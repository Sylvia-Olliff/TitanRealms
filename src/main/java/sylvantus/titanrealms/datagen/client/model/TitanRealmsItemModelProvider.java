package sylvantus.titanrealms.datagen.client.model;

import com.google.common.collect.Table.Cell;
import java.util.Map;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.registration.impl.ItemRegistryObject;
import sylvantus.titanrealms.core.registries.TitanRealmsItems;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;

public class TitanRealmsItemModelProvider extends BaseItemModelProvider {

    public TitanRealmsItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TitanRealms.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Buckets
//        TitanRealmsFluids.FLUIDS.getAllFluids().forEach(this::registerBucket);

//        for (Cell<ResourceType, PrimaryResource, ItemRegistryObject<Item>> item : TitanRealmsItems.PROCESSED_RESOURCES.cellSet()) {
//            ResourceLocation texture = itemTexture(item.getValue());
//            if (textureExists(texture)) {
//                generated(item.getValue(), texture);
//            } else {
//                //If the texture does not exist fallback to the default texture
//                resource(item.getValue(), item.getRowKey().getRegistryPrefix());
//            }
//        }

//        for (Map.Entry<ModuleData<?>, ItemRegistryObject<? extends ItemModule>> entry : MekanismItems.MODULES.entrySet()) {
//            generated(entry.getValue());
//        }
    }
}