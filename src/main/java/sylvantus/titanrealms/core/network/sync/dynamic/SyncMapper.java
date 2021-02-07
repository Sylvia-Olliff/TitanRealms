package sylvantus.titanrealms.core.network.sync.dynamic;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import mekanism.api.fluid.IExtendedFluidTank;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import org.objectweb.asm.Type;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.core.lib.math.voxel.VoxelCuboid;
import sylvantus.titanrealms.core.network.container.property.PropertyType;
import sylvantus.titanrealms.core.network.sync.ISyncableData;
import sylvantus.titanrealms.core.network.sync.SyncableEnum;
import sylvantus.titanrealms.core.util.LambdaMetaFactoryUtil;

public class SyncMapper {

    private SyncMapper() {
    }

    public static final String DEFAULT_TAG = "default";
    private static final List<SpecialPropertyHandler<?>> specialProperties = new ArrayList<>();
    private static final Map<Class<?>, PropertyDataClassCache> syncablePropertyMap = new Object2ObjectOpenHashMap<>();

    static {
        specialProperties.add(new SpecialPropertyHandler<>(IExtendedFluidTank.class,
              SpecialPropertyData.create(FluidStack.class, IFluidTank::getFluid, IExtendedFluidTank::setStack)
        ));
        specialProperties.add(new SpecialPropertyHandler<>(VoxelCuboid.class,
              SpecialPropertyData.create(BlockPos.class, VoxelCuboid::getMinPos, VoxelCuboid::setMinPos),
              SpecialPropertyData.create(BlockPos.class, VoxelCuboid::getMaxPos, VoxelCuboid::setMaxPos)
        ));
    }

    public static void collectScanData() {
        try {
            collectScanDataUnsafe();
        } catch (Throwable e) {
            TitanRealms.LOGGER.error("Failed to collect scan data and create sync maps", e);
        }
    }

    private static void collectScanDataUnsafe() throws Throwable {
        ModList modList = ModList.get();
        Map<Class<?>, List<AnnotationData>> knownClasses = new Object2ObjectOpenHashMap<>();
        Type containerSyncType = Type.getType(ContainerSync.class);
        for (ModFileScanData scanData : modList.getAllScanData()) {
            for (AnnotationData data : scanData.getAnnotations()) {
                //If the annotation is on a field, and is the sync type
                if (data.getTargetType() == ElementType.FIELD && containerSyncType.equals(data.getAnnotationType())) {
                    String className = data.getClassType().getClassName();
                    try {
                        Class<?> annotatedClass = Class.forName(className);
                        knownClasses.computeIfAbsent(annotatedClass, clazz -> new ArrayList<>()).add(data);
                    } catch (ClassNotFoundException e) {
                        TitanRealms.LOGGER.error("Failed to find class '{}'", className);
                    }
                }
            }
        }
        Map<Class<?>, List<PropertyFieldInfo>> flatPropertyMap = new Object2ObjectOpenHashMap<>();
        for (Entry<Class<?>, List<AnnotationData>> entry : knownClasses.entrySet()) {
            Class<?> annotatedClass = entry.getKey();
            List<PropertyFieldInfo> propertyInfo = new ArrayList<>();
            flatPropertyMap.put(annotatedClass, propertyInfo);
            for (AnnotationData data : entry.getValue()) {
                String fieldName = data.getMemberName();
                try {
                    Field field = annotatedClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Map<String, Object> annotationData = data.getAnnotationData();
                    String getterName = (String) annotationData.getOrDefault("getter", "");
                    PropertyField newField;
                    SpecialPropertyHandler<?> handler = specialProperties.stream().filter(h -> h.fieldType.isAssignableFrom(field.getType())).findFirst().orElse(null);
                    if (handler == null) {
                        PropertyType type = PropertyType.getFromType(field.getType());
                        String setterName = (String) annotationData.getOrDefault("setter", "");
                        if (type != null) {
                            newField = new PropertyField(new TrackedFieldData(LambdaMetaFactoryUtil.createGetter(field, annotatedClass, getterName),
                                  LambdaMetaFactoryUtil.createSetter(field, annotatedClass, setterName), type));
                        } else if (field.getType().isEnum()) {
                            newField = new PropertyField(new EnumFieldData(LambdaMetaFactoryUtil.createGetter(field, annotatedClass, getterName),
                                  LambdaMetaFactoryUtil.createSetter(field, annotatedClass, setterName), field.getType()));
                        } else {
                            TitanRealms.LOGGER.error("Attempted to sync an invalid field '{}'", fieldName);
                            return;
                        }
                    } else {
                        newField = createSpecialProperty(handler, field, annotatedClass, getterName);
                    }
                    String fullPath = annotatedClass.getName() + "#" + fieldName;
                    if (annotationData.containsKey("tags")) {
                        //If the annotation data has tags add them
                        List<String> tags = (List<String>) annotationData.get("tags");
                        for (String tag : tags) {
                            propertyInfo.add(new PropertyFieldInfo(fullPath, tag, newField));
                        }
                    } else {
                        //Otherwise fallback to the default
                        propertyInfo.add(new PropertyFieldInfo(fullPath, DEFAULT_TAG, newField));
                    }
                } catch (NoSuchFieldException e) {
                    TitanRealms.LOGGER.error("Failed to find field '{}' for class '{}'", fieldName, data.getClassType());
                }
            }
        }
        Map<Class<?>, List<PropertyFieldInfo>> propertyMap = new Object2ObjectOpenHashMap<>();
        for (Entry<Class<?>, List<PropertyFieldInfo>> entry : flatPropertyMap.entrySet()) {
            Class<?> clazz = entry.getKey();
            List<PropertyFieldInfo> propertyInfo = entry.getValue();
            Class<?> current = clazz;
            while (current.getSuperclass() != null) {
                current = current.getSuperclass();
                List<PropertyFieldInfo> superInfo = propertyMap.get(current);
                if (superInfo != null) {
                    //If we already have an overall cache for the super class, add from it and break out of checking super classes
                    propertyInfo.addAll(superInfo);
                    break;
                }
                //Otherwise continue building up the cache, collecting all the class names up to the root superclass
                superInfo = flatPropertyMap.get(current);
                if (superInfo != null) {
                    //If the property map has the super class, grab the fields that correspond to it
                    //Note: We keep going here as it may have super classes higher up
                    propertyInfo.addAll(superInfo);
                }
            }
            propertyMap.put(clazz, propertyInfo);
        }
        List<ClassPropertyFieldInfo> flatPropertyMapInfo = propertyMap.entrySet().stream().map(entry -> new ClassPropertyFieldInfo(entry.getKey(), entry.getValue()))
              .sorted(Comparator.comparing(info -> info.className)).collect(Collectors.toList());
        for (ClassPropertyFieldInfo classPropertyInfo : flatPropertyMapInfo) {
            PropertyDataClassCache cache = new PropertyDataClassCache();
            classPropertyInfo.propertyFields.sort(Comparator.comparing(info -> info.fieldPath + "|" + info.tag));
            for (PropertyFieldInfo field : classPropertyInfo.propertyFields) {
                cache.propertyFieldMap.put(field.tag, field.field);
            }
            syncablePropertyMap.put(classPropertyInfo.clazz, cache);
        }
    }

    public static void setup(TitanRealmsContainer container, Class<?> holderClass, Supplier<Object> holderSupplier) {
        setup(container, holderClass, holderSupplier, DEFAULT_TAG);
    }

    public static void setup(TitanRealmsContainer container, Class<?> holderClass, Supplier<Object> holderSupplier, String tag) {
        PropertyDataClassCache cache = syncablePropertyMap.computeIfAbsent(holderClass, SyncMapper::buildSyncMap);
        for (PropertyField field : cache.propertyFieldMap.get(tag)) {
            for (TrackedFieldData data : field.trackedData) {
                container.track(data.createSyncableData(holderSupplier));
            }
        }
    }

    private static PropertyDataClassCache buildSyncMap(Class<?> clazz) {
        PropertyDataClassCache cache = new PropertyDataClassCache();
        Class<?> current = clazz;
        while (current.getSuperclass() != null) {
            current = current.getSuperclass();
            PropertyDataClassCache superCache = syncablePropertyMap.get(current);
            if (superCache != null) {
                //If we already have an overall cache for the super class, add from it and break out of checking super classes
                cache.propertyFieldMap.putAll(superCache.propertyFieldMap);
                break;
            }
            //Otherwise continue going up to the root superclass
        }
        return cache;
    }

    private static <O> PropertyField createSpecialProperty(SpecialPropertyHandler<O> handler, Field field, Class<?> objType, String getterName) throws Throwable {
        PropertyField ret = new PropertyField();
        for (SpecialPropertyData<O> data : handler.specialData) {
            // create a getter for the actual property field itself
            Function<Object, O> fieldGetter = LambdaMetaFactoryUtil.createGetter(field, objType, getterName);
            // create a new tracked field
            TrackedFieldData trackedField = TrackedFieldData.create(data.propertyType, obj -> data.get(fieldGetter.apply(obj)), (obj, val) -> data.set(fieldGetter.apply(obj), val));
            if (trackedField != null) {
                ret.addTrackedData(trackedField);
            }
        }
        return ret;
    }

    private static class PropertyDataClassCache {

        //Note: This needs to be a linked map to ensure that the order is preserved
        private final Multimap<String, PropertyField> propertyFieldMap = LinkedHashMultimap.create();
    }

    private static class PropertyField {

        private final List<TrackedFieldData> trackedData = new ArrayList<>();

        private PropertyField(TrackedFieldData... data) {
            trackedData.addAll(Arrays.asList(data));
        }

        private void addTrackedData(TrackedFieldData data) {
            trackedData.add(data);
        }
    }

    protected static class TrackedFieldData {

        private PropertyType propertyType;
        private final Function<Object, Object> getter;
        private final BiConsumer<Object, Object> setter;

        protected TrackedFieldData(Function<Object, Object> getter, BiConsumer<Object, Object> setter) {
            this.getter = getter;
            this.setter = setter;
        }

        private TrackedFieldData(Function<Object, Object> getter, BiConsumer<Object, Object> setter, PropertyType propertyType) {
            this(getter, setter);
            this.propertyType = propertyType;
        }

        protected Object get(Object dataObj) {
            return getter.apply(dataObj);
        }

        protected void set(Object dataObj, Object value) {
            setter.accept(dataObj, value);
        }

        protected ISyncableData createSyncableData(Supplier<Object> obj) {
            return create(() -> {
                Object dataObj = obj.get();
                return dataObj == null ? getDefault() : get(dataObj);
            }, val -> {
                Object dataObj = obj.get();
                if (dataObj != null) {
                    set(dataObj, val);
                }
            });
        }

        protected ISyncableData create(Supplier<Object> getter, Consumer<Object> setter) {
            return propertyType.create(getter, setter);
        }

        protected Object getDefault() {
            return propertyType.getDefault();
        }

        protected static TrackedFieldData create(Class<?> propertyType, Function<Object, Object> getter, BiConsumer<Object, Object> setter) {
            if (propertyType.isEnum()) {
                return new EnumFieldData(getter, setter, propertyType);
            }
            PropertyType type = PropertyType.getFromType(propertyType);
            if (type == null) {
                TitanRealms.LOGGER.error("Tried to create property data for invalid type '{}'.", propertyType.getName());
                return null;
            }
            return new TrackedFieldData(getter, setter, type);
        }
    }

    protected static class EnumFieldData extends TrackedFieldData {

        private final Object[] constants;

        private EnumFieldData(Function<Object, Object> getter, BiConsumer<Object, Object> setter, Class<?> enumClass) {
            super(getter, setter);
            constants = enumClass.getEnumConstants();
        }

        @Override
        protected ISyncableData create(Supplier<Object> getter, Consumer<Object> setter) {
            return createData((Enum[]) constants, getter, setter);
        }

        protected <ENUM extends Enum<ENUM>> ISyncableData createData(ENUM[] constants, Supplier<Object> getter, Consumer<Object> setter) {
            return SyncableEnum.create(val -> constants[val], constants[0], () -> (ENUM) getter.get(), setter::accept);
        }

        @Override
        protected Object getDefault() {
            return constants[0];
        }
    }

    private static class SpecialPropertyHandler<O> {

        private final Class<O> fieldType;
        private final List<SpecialPropertyData<O>> specialData = new ArrayList<>();

        @SafeVarargs
        private SpecialPropertyHandler(Class<O> fieldType, SpecialPropertyData<O>... data) {
            this.fieldType = fieldType;
            specialData.addAll(Arrays.asList(data));
        }
    }

    protected static class SpecialPropertyData<O> {

        private final Class<?> propertyType;
        private final Function<O, ?> getter;
        private final BiConsumer<O, Object> setter;

        private SpecialPropertyData(Class<?> propertyType, Function<O, ?> getter, BiConsumer<O, Object> setter) {
            this.propertyType = propertyType;
            this.getter = getter;
            this.setter = setter;
        }

        protected Object get(O obj) {
            return getter.apply(obj);
        }

        protected void set(O obj, Object val) {
            setter.accept(obj, val);
        }

        @SuppressWarnings("unchecked")
        protected static <O, V> SpecialPropertyData<O> create(Class<V> propertyType, Function<O, V> getter, BiConsumer<O, V> setter) {
            return new SpecialPropertyData<>(propertyType, getter, (BiConsumer<O, Object>) setter);
        }
    }

    private static class ClassPropertyFieldInfo {

        private final Class<?> clazz;
        private final String className;
        private final List<PropertyFieldInfo> propertyFields;

        private ClassPropertyFieldInfo(Class<?> clazz, List<PropertyFieldInfo> propertyFields) {
            this.clazz = clazz;
            this.className = clazz.getName();
            this.propertyFields = propertyFields;
        }
    }

    private static class PropertyFieldInfo {

        private final PropertyField field;
        private final String fieldPath;
        private final String tag;

        private PropertyFieldInfo(String fieldPath, String tag, PropertyField field) {
            this.fieldPath = fieldPath;
            this.field = field;
            this.tag = tag;
        }
    }
}