package sylvantus.titanrealms.datagen.client.state;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.SixWayBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrain;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrainSoil;
import sylvantus.titanrealms.common.items.block.ItemBlockTerrain;
import sylvantus.titanrealms.common.items.block.ItemBlockTerrainSoil;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;
import sylvantus.titanrealms.common.resources.TerrainResource;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.registration.impl.BlockRegistryObject;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;
import sylvantus.titanrealms.datagen.client.model.TitanRealmsBlockModelProvider;

import java.util.Map;

public class TitanRealmsBlockStateProvider extends BaseBlockStateProvider<TitanRealmsBlockModelProvider> {

    private static final ResourceLocation basicCube = TitanRealms.rl("block/basic_cube");
    private static final ResourceLocation cubeAll2Layer = TitanRealms.rl("block/util/cube_all_2_layer");
    private static final ResourceLocation pane$side = TitanRealms.rl("block/util/pane/side");
    private static final ResourceLocation pane$noside = TitanRealms.rl("block/util/pane/noside");
    private static final ResourceLocation pane$noside_alt = TitanRealms.rl("block/util/pane/noside_alt");
    private static final ResourceLocation pane$post = TitanRealms.rl("block/util/pane/post");

    public TitanRealmsBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TitanRealms.MODID, existingFileHelper, TitanRealmsBlockModelProvider::new);
    }

    @Override
    protected void registerStatesAndModels() {

        // terrain soil
        registerTerrainSoilStates();

//        // resource blocks
//        for (Map.Entry<PrimaryResource, BlockRegistryObject<?, ?>> entry : TitanRealmsBlocks.PROCESSED_RESOURCE_BLOCKS.entrySet()) {
//            ResourceLocation texture = modLoc("block/block_" + entry.getKey().getName());
//            ModelFile file;
//            if (models().textureExists(texture)) {
//                //If we have an override we can just use a basic cube that has no color tints in it
//                file = models().withExistingParent("block/storage/" + entry.getKey().getName(), basicCube)
//                        .texture("all", texture);
//            } else {
//                //If the texture does not exist fallback to the default texture and use a colorable base model
//                file = models().withExistingParent("block/storage/" + entry.getKey().getName(), modLoc("block/colored_cube"))
//                        .texture("all", modLoc("block/resource_block"));
//            }
//            simpleBlock(entry.getValue().getBlock(), file);
//        }
//        for (Map.Entry<OreType, BlockRegistryObject<?, ?>> entry : TitanRealmsBlocks.ORES.entrySet()) {
//            ModelFile file = models().withExistingParent("block/ore/" + entry.getKey().getResource().getRegistrySuffix(), basicCube)
//                    .texture("all", modLoc("block/" + entry.getValue().getName()));
//            simpleBlock(entry.getValue().getBlock(), file);
//        }
//        // block items
//        for (Map.Entry<PrimaryResource, BlockRegistryObject<?, ?>> entry : TitanRealmsBlocks.PROCESSED_RESOURCE_BLOCKS.entrySet()) {
//            models().withExistingParent("item/block_" + entry.getKey().getName(), modLoc("block/storage/" + entry.getKey().getName()));
//        }
//        for (Map.Entry<OreType, BlockRegistryObject<?, ?>> entry : TitanRealmsBlocks.ORES.entrySet()) {
//            models().withExistingParent("item/" + entry.getKey().getResource().getRegistrySuffix() + "_ore", modLoc("block/ore/" + entry.getKey().getResource().getRegistrySuffix()));
//        }
    }

    @SuppressWarnings("unchecked cast")
    private void registerTerrainSoilStates() {
        ImmutableList<BlockRegistryObject<? extends BlockTerrainSoil, ? extends ItemBlockTerrainSoil>> terrain = ImmutableList.of(
                (BlockRegistryObject<? extends BlockTerrainSoil,? extends ItemBlockTerrainSoil>) TitanRealmsBlocks.TERRAIN.get(TerrainType.CLOUD_SOIL),
                (BlockRegistryObject<? extends BlockTerrainSoil,? extends ItemBlockTerrainSoil>) TitanRealmsBlocks.TERRAIN.get(TerrainType.DENSE_CLOUD_SOIL),
                (BlockRegistryObject<? extends BlockTerrainSoil,? extends ItemBlockTerrainSoil>) TitanRealmsBlocks.TERRAIN.get(TerrainType.SPARSE_CLOUD_SOIL));

        for (BlockRegistryObject<? extends BlockTerrainSoil, ? extends ItemBlockTerrainSoil> entry : terrain) {
            BlockTerrainInfo terrainInfo = entry.getBlock().getTerrainInfo();
            BlockTerrainSoil block = entry.getBlock();

            ModelFile post = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_post", pane$post)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile side = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_side", pane$side)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile noside = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_noside", pane$noside)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"));
            ModelFile noside_alt = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_noside_alt", pane$noside_alt)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"));

            getMultipartBuilder(block)
                    .part().modelFile(post).addModel().end()
                    .part().modelFile(side).addModel().condition(SixWayBlock.NORTH, true).end()
                    .part().modelFile(side).rotationY(90).addModel().condition(SixWayBlock.EAST, true).end()
                    .part().modelFile(post).addModel().end()
                    .part().modelFile(side).addModel().condition(SixWayBlock.NORTH, true).end()
                    .part().modelFile(side).rotationY(90).addModel().condition(SixWayBlock.EAST, true).end()
                    .part().modelFile(noside).addModel().condition(SixWayBlock.NORTH, false).end()
                    .part().modelFile(noside).rotationY(90).addModel().condition(SixWayBlock.EAST, false).end()
                    .part().modelFile(noside_alt).addModel().condition(SixWayBlock.SOUTH, false).end()
                    .part().modelFile(noside_alt).rotationY(90).addModel().condition(SixWayBlock.WEST, false).end();
        }
    }
}
