package sylvantus.titanrealms.datagen.client.model;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.resources.TerrainType;

public class TitanRealmsItemModelProvider extends BaseItemModelProvider {

    public TitanRealmsItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TitanRealms.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerTerrain();
        registerTerrainSoil();
    }

    private void registerTerrain() {
        generated("item/block_" + TerrainType.AESIR_STONE.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.AESIR_STONE.getTerrain().getRegistrySuffix()));
        generated("item/block_" + TerrainType.BLASTED_GLASS.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.BLASTED_GLASS.getTerrain().getRegistrySuffix()));
        generated("item/block_" + TerrainType.BLASTED_STONE.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.BLASTED_STONE.getTerrain().getRegistrySuffix()));
        generated("item/block_" + TerrainType.BLASTED_MARBLE.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.BLASTED_MARBLE.getTerrain().getRegistrySuffix()));
        generated("item/block_" + TerrainType.TITANFORGED_STONE.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.TITANFORGED_STONE.getTerrain().getRegistrySuffix()));
    }

    private void registerTerrainSoil() {
        generated("item/block_" + TerrainType.CLOUD_SOIL.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.CLOUD_SOIL.getTerrain().getRegistrySuffix()));
        generated("item/block_" + TerrainType.SPARSE_CLOUD_SOIL.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.CLOUD_SOIL.getTerrain().getRegistrySuffix()));
        generated("item/block_" + TerrainType.CLOUD_SOIL.getTerrain().getRegistrySuffix(), TitanRealms.rl("block/block_" + TerrainType.CLOUD_SOIL.getTerrain().getRegistrySuffix()));
    }

    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
}