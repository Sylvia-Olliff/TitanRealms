package sylvantus.titanrealms.datagen.client.state;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ModelTextures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.TitanRealmsBlock;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrain;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrainSoil;
import sylvantus.titanrealms.common.items.block.ItemBlockTerrain;
import sylvantus.titanrealms.common.items.block.ItemBlockTerrainSoil;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;
import sylvantus.titanrealms.common.resources.TerrainResource;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.registration.impl.BlockRegistryObject;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;
import sylvantus.titanrealms.datagen.client.model.TitanRealmsBlockModelProvider;

import java.util.Map;

public class TitanRealmsBlockStateProvider extends BaseBlockStateProvider<TitanRealmsBlockModelProvider> {

    private static final ResourceLocation basicCube = TitanRealms.rl("block/basic_cube");
    private static final ResourceLocation cubeAll2Layer = TitanRealms.rl("block/util/cube_all_2_layer");
    private static final ResourceLocation pane$side_north = TitanRealms.rl("block/util/pane/side_north");
    private static final ResourceLocation pane$side_south = TitanRealms.rl("block/util/pane/side_south");
    private static final ResourceLocation pane$side_east = TitanRealms.rl("block/util/pane/side_east");
    private static final ResourceLocation pane$side_west = TitanRealms.rl("block/util/pane/side_west");
    private static final ResourceLocation pane$post_down = TitanRealms.rl("block/util/pane/post_down");
    private static final ResourceLocation pane$post_up = TitanRealms.rl("block/util/pane/post_up");

    public TitanRealmsBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TitanRealms.MODID, existingFileHelper, TitanRealmsBlockModelProvider::new);
    }

    @Override
    protected void registerStatesAndModels() {

        // terrain
        registerTerrainSoilStates();
        registerTerrainStates();

        // trees
        registerTreeStates();

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

    @SuppressWarnings("unchecked")
    private void registerTerrainSoilStates() {
        ImmutableList<BlockRegistryObject<? extends BlockTerrainSoil, ? extends ItemBlockTerrainSoil>> terrainSoil = ImmutableList.of(
                (BlockRegistryObject<? extends BlockTerrainSoil,? extends ItemBlockTerrainSoil>) TitanRealmsBlocks.TERRAIN.get(TerrainType.CLOUD_SOIL),
                (BlockRegistryObject<? extends BlockTerrainSoil,? extends ItemBlockTerrainSoil>) TitanRealmsBlocks.TERRAIN.get(TerrainType.DENSE_CLOUD_SOIL),
                (BlockRegistryObject<? extends BlockTerrainSoil,? extends ItemBlockTerrainSoil>) TitanRealmsBlocks.TERRAIN.get(TerrainType.SPARSE_CLOUD_SOIL));

        for (BlockRegistryObject<? extends BlockTerrainSoil, ? extends ItemBlockTerrainSoil> entry : terrainSoil) {
            BlockTerrainInfo terrainInfo = entry.getBlock().getTerrainInfo();
            BlockTerrainSoil block = entry.getBlock();

            ModelFile post_down = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_post_down", pane$post_down)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile post_up = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_post_up", pane$post_up)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile side_north = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_side_north", pane$side_north)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile side_south = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_side_south", pane$side_south)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile side_east = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_side_east", pane$side_east)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");
            ModelFile side_west = models().withExistingParent(terrainInfo.getRegistrySuffix() + "_side_west", pane$side_west)
                    .texture("pane", TitanRealms.rl("block/block_cloud_soil"))
                    .texture("edge", "#pane");

            getMultipartBuilder(block)
                    .part().modelFile(side_north).addModel().condition(SixWayBlock.NORTH, false).end()
                    .part().modelFile(side_east).addModel().condition(SixWayBlock.EAST, false).end()
                    .part().modelFile(side_south).addModel().condition(SixWayBlock.SOUTH, false).end()
                    .part().modelFile(side_west).addModel().condition(SixWayBlock.WEST, false).end()
                    .part().modelFile(post_down).addModel().condition(SixWayBlock.DOWN, false).end()
                    .part().modelFile(post_up).addModel().condition(SixWayBlock.UP, false).end();

        }
    }

    @SuppressWarnings("unchecked")
    private void registerTerrainStates() {

        BlockTerrainInfo blastedMarbleInfo = ((BlockTerrain) TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_MARBLE).getBlock()).getTerrainInfo();
        BlockTerrainInfo titanforgedStoneInfo = ((BlockTerrain) TitanRealmsBlocks.TERRAIN.get(TerrainType.TITANFORGED_STONE).getBlock()).getTerrainInfo();
        BlockTerrainInfo aesirStoneInfo = ((BlockTerrain) TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE).getBlock()).getTerrainInfo();
        BlockTerrainInfo blastedGlassInfo = ((BlockTerrain) TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_GLASS).getBlock()).getTerrainInfo();
        BlockTerrainInfo blastedStoneInfo = ((BlockTerrain) TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_STONE).getBlock()).getTerrainInfo();

        models().withExistingParent("item/block_" + titanforgedStoneInfo.getRegistrySuffix(), modLoc("block/" + titanforgedStoneInfo.getRegistrySuffix()));
        models().withExistingParent("item/block_" + aesirStoneInfo.getRegistrySuffix(), modLoc("block/" + aesirStoneInfo.getRegistrySuffix()));
        models().withExistingParent("item/block_" + blastedGlassInfo.getRegistrySuffix(), modLoc("block/" + blastedGlassInfo.getRegistrySuffix()));

        ModelFile blastedStone = buildDerivativeBlock(blastedStoneInfo);
        simpleBlock(TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_STONE).getBlock(), blastedStone);
        models().withExistingParent("item/block_" + blastedStoneInfo.getRegistrySuffix(), modLoc("block/" + blastedStoneInfo.getRegistrySuffix()));

        ModelFile blastedMarble = buildDerivativeBlock(blastedMarbleInfo);
        simpleBlock(TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_MARBLE).getBlock(), blastedMarble);
        models().withExistingParent("item/block_" + blastedMarbleInfo.getRegistrySuffix(), modLoc("block/" + blastedMarbleInfo.getRegistrySuffix()));
    }

    private void registerTreeStates() {
        registerLogSapling(TitanRealmsBlocks.AIRWOOD_LOG.getBlock());
        registerLogSapling(TitanRealmsBlocks.STORMWOOD_LOG.getBlock());
    }

    // TODO: Add Support for saplings
    private void registerLogSapling(RotatedPillarBlock log /*, Block sapling */) {
        logBlock(log);
        models().withExistingParent("item/" + log.getRegistryName().getPath(), TitanRealms.rl("block/" + log.getRegistryName().getPath()));
//        ResourceLocation saplingTex = TitanRealms.rl("block/" + sapling.getRegistryName().getPath());
//        simpleBlock(sapling, models().cross(sapling.getRegistryName().getPath(), saplingTex));
    }

    private <R extends IResource> ModelFile buildDerivativeBlock(R blockInfo) {
        ResourceLocation texture = modLoc("block/block_" + blockInfo.getRegistrySuffix());
        ModelFile file;

        if (models().textureExists(texture)) {
            file = models().withExistingParent("block/" + blockInfo.getRegistrySuffix(), basicCube)
                    .texture("all", texture);
        } else {
            file = models().withExistingParent("block/" + blockInfo.getRegistrySuffix(), modLoc("block/colored_cube"))
                    .texture("all", modLoc("block/block_" +
                            ((BlockTerrain) TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE).getBlock()).getTerrainInfo().getRegistrySuffix()));
        }

        return file;
    }
}
