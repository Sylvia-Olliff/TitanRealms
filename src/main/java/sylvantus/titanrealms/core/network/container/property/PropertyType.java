package sylvantus.titanrealms.core.network.container.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import mekanism.api.math.FloatingLong;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import sylvantus.titanrealms.core.network.container.property.list.ListPropertyData;
import sylvantus.titanrealms.core.network.sync.*;

public enum PropertyType {
    BOOLEAN(Boolean.TYPE, false, (getter, setter) -> SyncableBoolean.create(() -> (boolean) getter.get(), setter::accept),
          (property, buffer) -> new BooleanPropertyData(property, buffer.readBoolean())),
    BYTE(Byte.TYPE, (byte) 0, (getter, setter) -> SyncableByte.create(() -> (byte) getter.get(), setter::accept),
          (property, buffer) -> new BytePropertyData(property, buffer.readByte())),
    DOUBLE(Double.TYPE, 0D, (getter, setter) -> SyncableDouble.create(() -> (double) getter.get(), setter::accept),
          (property, buffer) -> new DoublePropertyData(property, buffer.readDouble())),
    FLOAT(Float.TYPE, 0F, (getter, setter) -> SyncableFloat.create(() -> (float) getter.get(), setter::accept),
          (property, buffer) -> new FloatPropertyData(property, buffer.readFloat())),
    INT(Integer.TYPE, 0, (getter, setter) -> SyncableInt.create(() -> (int) getter.get(), setter::accept),
          (property, buffer) -> new IntPropertyData(property, buffer.readVarInt())),
    LONG(Long.TYPE, 0L, (getter, setter) -> SyncableLong.create(() -> (long) getter.get(), setter::accept),
          (property, buffer) -> new LongPropertyData(property, buffer.readVarLong())),
    SHORT(Short.TYPE, (short) 0, (getter, setter) -> SyncableShort.create(() -> (short) getter.get(), setter::accept),
          (property, buffer) -> new ShortPropertyData(property, buffer.readShort())),
    ITEM_STACK(ItemStack.class, ItemStack.EMPTY, (getter, setter) -> SyncableItemStack.create(() -> (ItemStack) getter.get(), setter::accept),
          (property, buffer) -> new ItemStackPropertyData(property, buffer.readItemStack())),
    LIST(ArrayList.class, Collections.emptyList(), (getter, setter) -> null /* not handled */, ListPropertyData::readList),
    BLOCK_POS(BlockPos.class, null, (getter, setter) -> SyncableBlockPos.create(() -> (BlockPos) getter.get(), setter::accept),
          (property, buffer) -> new BlockPosPropertyData(property, buffer.readBoolean() ? buffer.readBlockPos() : null)),
    FLOATING_LONG(FloatingLong.class, FloatingLong.ZERO, (getter, setter) -> SyncableFloatingLong.create(() -> (FloatingLong) getter.get(), setter::accept),
          (property, buffer) -> new FloatingLongPropertyData(property, FloatingLong.readFromBuffer(buffer)));

    private final Class<?> type;
    private final Object defaultValue;
    private final BiFunction<Supplier<Object>, Consumer<Object>, ISyncableData> creatorFunction;
    private final BiFunction<Short, PacketBuffer, PropertyData> dataCreatorFunction;

    private static final PropertyType[] VALUES = values();

    PropertyType(Class<?> type, Object defaultValue, BiFunction<Supplier<Object>, Consumer<Object>, ISyncableData> creatorFunction,
          BiFunction<Short, PacketBuffer, PropertyData> dataCreatorFunction) {
        this.type = type;
        this.defaultValue = defaultValue;
        this.creatorFunction = creatorFunction;
        this.dataCreatorFunction = dataCreatorFunction;
    }

    @SuppressWarnings("unchecked cast")
    public <T> T getDefault() {
        return (T) defaultValue;
    }

    public static PropertyType getFromType(Class<?> type) {
        for (PropertyType propertyType : VALUES) {
            if (type == propertyType.type) {
                return propertyType;
            }
        }

        return null;
    }

    public PropertyData createData(short property, PacketBuffer buffer) {
        return dataCreatorFunction.apply(property, buffer);
    }

    public ISyncableData create(Supplier<Object> supplier, Consumer<Object> consumer) {
        return creatorFunction.apply(supplier, consumer);
    }
}