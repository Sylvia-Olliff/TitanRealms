package sylvantus.titanrealms.datagen.client.state;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ModelTextures;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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
        registerPlankBasedBlocks("airwood", TitanRealmsBlocks.AIRWOOD_PLANKS.getBlock(), TitanRealmsBlocks.AIRWOOD_STAIRS.getBlock());
        registerLogSapling(TitanRealmsBlocks.STORMWOOD_LOG.getBlock());
    }

    // TODO: Add Support for saplings
    private void registerLogSapling(RotatedPillarBlock log /*, Block sapling */) {
        logBlock(log);
        models().withExistingParent("item/" + log.getRegistryName().getPath(), TitanRealms.rl("block/" + log.getRegistryName().getPath()));
//        ResourceLocation saplingTex = TitanRealms.rl("block/" + sapling.getRegistryName().getPath());
//        simpleBlock(sapling, models().cross(sapling.getRegistryName().getPath(), saplingTex));
    }

    private void registerPlankBasedBlocks(String variant, Block plank, StairsBlock stairs) {
        String plankTexName = "planks_" + variant;
        ResourceLocation plankText_0 = TitanRealms.rl("block/planks/" + plankTexName + "_0");
        ResourceLocation plankText_1 = TitanRealms.rl("block/planks/" + plankTexName + "_1");
        ResourceLocation plankText_2 = TitanRealms.rl("block/planks/" + plankTexName + "_2");
        ResourceLocation plankText_3 = TitanRealms.rl("block/planks/" + plankTexName + "_3");

        ConfiguredModel[] plankModels = ConfiguredModel.builder()
                .weight(10).modelFile(models().cubeAll(plank.getRegistryName().getPath(), plankText_0)).nextModel()
                .weight(10).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_1", plankText_1)).nextModel()
                .weight(1).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_2", plankText_2)).nextModel()
                .weight(1).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_3", plankText_3)).build();
        simpleBlock(plank, plankModels);

        models().withExistingParent("item/" + plank.getRegistryName().getPath(), TitanRealms.rl("block/" + plank.getRegistryName().getPath()));
        models().withExistingParent("item/" + plank.getRegistryName().getPath(), TitanRealms.rl("block/" + plank.getRegistryName().getPath() + "_1"));
        models().withExistingParent("item/" + plank.getRegistryName().getPath(), TitanRealms.rl("block/" + plank.getRegistryName().getPath() + "_2"));
        models().withExistingParent("item/" + plank.getRegistryName().getPath(), TitanRealms.rl("block/" + plank.getRegistryName().getPath() + "_3"));

        generateWoodStairs(stairs, plankText_0, plankText_1, plankText_2, plankText_3);
    }

    private void generateWoodStairs(StairsBlock stairs, ResourceLocation texture_0, ResourceLocation texture_1,
                                    ResourceLocation texture_2, ResourceLocation texture_3) {
        ModelFile main0 = models().stairs(stairs.getRegistryName().getPath(), texture_0, texture_0, texture_0);
        ModelFile main1 = models().stairs(stairs.getRegistryName().getPath() + "_1", texture_1, texture_1, texture_1);
        ModelFile main2 = models().stairs(stairs.getRegistryName().getPath() + "_2", texture_2, texture_2, texture_2);
        ModelFile main3 = models().stairs(stairs.getRegistryName().getPath() + "_3", texture_3, texture_3, texture_3);
        ModelFile inner0 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner", texture_0, texture_0, texture_0);
        ModelFile inner1 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner_1", texture_1, texture_1, texture_1);
        ModelFile inner2 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner_2", texture_2, texture_2, texture_2);
        ModelFile inner3 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner_3", texture_3, texture_3, texture_3);
        ModelFile outer0 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer", texture_0, texture_0, texture_0);
        ModelFile outer1 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer_1", texture_1, texture_1, texture_1);
        ModelFile outer2 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer_2", texture_2, texture_2, texture_2);
        ModelFile outer3 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer_3", texture_3, texture_3, texture_3);

        getVariantBuilder(stairs)
                .forAllStatesExcept(state -> {
                    Direction facing = state.get(StairsBlock.FACING);
                    Half half = state.get(StairsBlock.HALF);
                    StairsShape shape = state.get(StairsBlock.SHAPE);
                    int yRot = (int) facing.rotateY().getHorizontalAngle(); // Stairs model is rotated 90 degrees clockwise for some reason
                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                        yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
                    }
                    if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                        yRot += 90; // Top stairs are rotated 90 degrees clockwise
                    }
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
                    return ConfiguredModel.builder()
                            .weight(10)
                            .modelFile(shape == StairsShape.STRAIGHT ? main0 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner0 : outer0)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .nextModel()

                            .weight(10)
                            .modelFile(shape == StairsShape.STRAIGHT ? main1 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner1 : outer1)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .nextModel()

                            .weight(1)
                            .modelFile(shape == StairsShape.STRAIGHT ? main2 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner2 : outer2)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .nextModel()

                            .weight(1)
                            .modelFile(shape == StairsShape.STRAIGHT ? main3 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner3 : outer3)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .build();
                }, StairsBlock.WATERLOGGED);

        models().withExistingParent(stairs.getRegistryName().getPath(), TitanRealms.rl("block/" + stairs.getRegistryName().getPath()));
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
