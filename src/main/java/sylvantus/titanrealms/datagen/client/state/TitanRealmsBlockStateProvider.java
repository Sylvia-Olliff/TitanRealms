package sylvantus.titanrealms.datagen.client.state;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrain;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrainSoil;
import sylvantus.titanrealms.common.items.block.ItemBlockTerrainSoil;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.registration.impl.BlockRegistryObject;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;
import sylvantus.titanrealms.datagen.client.model.TitanRealmsBlockModelProvider;

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
        registerPlankBasedBlocks("airwood",
                TitanRealmsBlocks.AIRWOOD_PLANKS.getBlock(),
                TitanRealmsBlocks.AIRWOOD_STAIRS.getBlock(),
                TitanRealmsBlocks.AIRWOOD_SLAB.getBlock(),
                TitanRealmsBlocks.AIRWOOD_FENCE.getBlock(),
                TitanRealmsBlocks.AIRWOOD_GATE.getBlock(),
                TitanRealmsBlocks.AIRWOOD_PLATE.getBlock(),
                TitanRealmsBlocks.AIRWOOD_BUTTON.getBlock());
        registerLogSapling(TitanRealmsBlocks.STORMWOOD_LOG.getBlock());
    }

    // TODO: Add Support for saplings
    private void registerLogSapling(RotatedPillarBlock log /*, Block sapling */) {
        logBlock(log);
        models().withExistingParent("item/" + log.getRegistryName().getPath(), TitanRealms.rl("block/" + log.getRegistryName().getPath()));
//        ResourceLocation saplingTex = TitanRealms.rl("block/" + sapling.getRegistryName().getPath());
//        simpleBlock(sapling, models().cross(sapling.getRegistryName().getPath(), saplingTex));
    }

    private void registerPlankBasedBlocks(String variant, Block plank, StairsBlock stairs, Block slab, Block fence, Block gate, Block plate, Block button) {
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

        ConfiguredModel[] bottomSlabModels = ConfiguredModel.builder()
                .weight(10).modelFile(models().slab(slab.getRegistryName().getPath(), plankText_0, plankText_0, plankText_0)).nextModel()
                .weight(10).modelFile(models().slab(slab.getRegistryName().getPath() + "_1", plankText_1, plankText_1, plankText_1)).nextModel()
                .weight(1).modelFile(models().slab(slab.getRegistryName().getPath() + "_2", plankText_2, plankText_2, plankText_2)).nextModel()
                .weight(1).modelFile(models().slab(slab.getRegistryName().getPath() + "_3", plankText_3, plankText_3, plankText_3)).build();
        ConfiguredModel[] topSlabModels = ConfiguredModel.builder()
                .weight(10).uvLock(true).rotationX(180).modelFile(bottomSlabModels[0].model).nextModel()
                .weight(10).uvLock(true).rotationX(180).modelFile(bottomSlabModels[1].model).nextModel()
                .weight(1).uvLock(true).rotationX(180).modelFile(bottomSlabModels[2].model).nextModel()
                .weight(1).uvLock(true).rotationX(180).modelFile(bottomSlabModels[3].model).build();
        getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).setModels(bottomSlabModels);
        getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.TOP).setModels(topSlabModels);
        getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).setModels(plankModels);

        models().withExistingParent("item/" + plank.getRegistryName().getPath(), TitanRealms.rl("block/" + plank.getRegistryName().getPath()));
        models().withExistingParent("item/" + slab.getRegistryName().getPath(), TitanRealms.rl("block/" + slab.getRegistryName().getPath()));

        generateWoodStairs(stairs, plankText_0, plankText_1, plankText_2, plankText_3);
        generateWoodFence(fence, plankText_0, plankText_1, plankText_2, plankText_3);
        generateWoodGate(gate, plankText_0, plankText_1, plankText_2, plankText_3);
        generateWoodPlate(plate, plankText_0, plankText_1, plankText_2, plankText_3);
        generateWoodButton(button, plankText_0, plankText_1, plankText_2, plankText_3);

    }

    private void generateWoodStairs(StairsBlock stairs, ResourceLocation plankText_0, ResourceLocation plankText_1,
                                    ResourceLocation plankText_2, ResourceLocation plankText_3) {
        ModelFile main_0 = models().stairs(stairs.getRegistryName().getPath(), plankText_0, plankText_0, plankText_0);
        ModelFile main_1 = models().stairs(stairs.getRegistryName().getPath() + "_1", plankText_1, plankText_1, plankText_1);
        ModelFile main_2 = models().stairs(stairs.getRegistryName().getPath() + "_2", plankText_2, plankText_2, plankText_2);
        ModelFile main_3 = models().stairs(stairs.getRegistryName().getPath() + "_3", plankText_3, plankText_3, plankText_3);
        ModelFile inner_0 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner", plankText_0, plankText_0, plankText_0);
        ModelFile inner_1 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner_1", plankText_1, plankText_1, plankText_1);
        ModelFile inner_2 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner_2", plankText_2, plankText_2, plankText_2);
        ModelFile inner_3 = models().stairsInner(stairs.getRegistryName().getPath() + "_inner_3", plankText_3, plankText_3, plankText_3);
        ModelFile outer_0 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer", plankText_0, plankText_0, plankText_0);
        ModelFile outer_1 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer_1", plankText_1, plankText_1, plankText_1);
        ModelFile outer_2 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer_2", plankText_2, plankText_2, plankText_2);
        ModelFile outer_3 = models().stairsOuter(stairs.getRegistryName().getPath() + "_outer_3", plankText_3, plankText_3, plankText_3);

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
                            .modelFile(shape == StairsShape.STRAIGHT ? main_0 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner_0 : outer_0)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .nextModel()

                            .weight(10)
                            .modelFile(shape == StairsShape.STRAIGHT ? main_1 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner_1 : outer_1)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .nextModel()

                            .weight(1)
                            .modelFile(shape == StairsShape.STRAIGHT ? main_2 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner_2 : outer_2)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .nextModel()

                            .weight(1)
                            .modelFile(shape == StairsShape.STRAIGHT ? main_3 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner_3 : outer_3)
                            .rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
                            .build();
                }, StairsBlock.WATERLOGGED);

        models().withExistingParent("item/" + stairs.getRegistryName().getPath(), TitanRealms.rl("block/" + stairs.getRegistryName().getPath()));
    }

    private void generateWoodFence(Block fence, ResourceLocation plankText_0, ResourceLocation plankText_1,
                                   ResourceLocation plankText_2, ResourceLocation plankText_3) {
        ModelFile post_0 = models().fencePost(fence.getRegistryName().getPath() + "_post", plankText_0);
        ModelFile post_1 = models().fencePost(fence.getRegistryName().getPath() + "_post_1", plankText_1);
        ModelFile post_2 = models().fencePost(fence.getRegistryName().getPath() + "_post_2", plankText_2);
        ModelFile post_3 = models().fencePost(fence.getRegistryName().getPath() + "_post_3", plankText_3);
        ModelFile side_0 = models().fenceSide(fence.getRegistryName().getPath() + "_side", plankText_0);
        ModelFile side_1 = models().fenceSide(fence.getRegistryName().getPath() + "_side_1", plankText_1);
        ModelFile side_2 = models().fenceSide(fence.getRegistryName().getPath() + "_side_2", plankText_2);
        ModelFile side_3 = models().fenceSide(fence.getRegistryName().getPath() + "_side_3", plankText_3);

        // [VanillaCopy] super.fourWayBlock, but with more models
        MultiPartBlockStateBuilder builder = getMultipartBuilder(fence).part()
                .weight(10).modelFile(post_0).nextModel()
                .weight(10).modelFile(post_1).nextModel()
                .weight(1).modelFile(post_2).nextModel()
                .weight(1).modelFile(post_3).addModel().end();
        SixWayBlock.FACING_TO_PROPERTY_MAP.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                builder.part()
                        .weight(10).modelFile(side_0).rotationY((((int) dir.getHorizontalAngle()) + 180) % 360).uvLock(true).nextModel()
                        .weight(10).modelFile(side_1).rotationY((((int) dir.getHorizontalAngle()) + 180) % 360).uvLock(true).nextModel()
                        .weight(1).modelFile(side_2).rotationY((((int) dir.getHorizontalAngle()) + 180) % 360).uvLock(true).nextModel()
                        .weight(1).modelFile(side_3).rotationY((((int) dir.getHorizontalAngle()) + 180) % 360).uvLock(true)
                        .addModel()
                        .condition(value, true);
            }
        });

        itemModels().getBuilder(fence.getRegistryName().getPath())
                .parent(itemModels().getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", plankText_0);
    }

    private void generateWoodGate(Block gate, ResourceLocation plankText_0, ResourceLocation plankText_1,
                                  ResourceLocation plankText_2, ResourceLocation plankText_3) {
        ModelFile gate_0 = models().fenceGate(gate.getRegistryName().getPath(), plankText_0);
        ModelFile gate_1 = models().fenceGate(gate.getRegistryName().getPath() + "_1", plankText_1);
        ModelFile gate_2 = models().fenceGate(gate.getRegistryName().getPath() + "_2", plankText_2);
        ModelFile gate_3 = models().fenceGate(gate.getRegistryName().getPath() + "_3", plankText_3);
        ModelFile open_0 = models().fenceGateOpen(gate.getRegistryName().getPath() + "_open", plankText_0);
        ModelFile open_1 = models().fenceGateOpen(gate.getRegistryName().getPath() + "_open_1", plankText_1);
        ModelFile open_2 = models().fenceGateOpen(gate.getRegistryName().getPath() + "_open_2", plankText_2);
        ModelFile open_3 = models().fenceGateOpen(gate.getRegistryName().getPath() + "_open_3", plankText_3);
        ModelFile wall_0 = models().fenceGateWall(gate.getRegistryName().getPath() + "_wall", plankText_0);
        ModelFile wall_1 = models().fenceGateWall(gate.getRegistryName().getPath() + "_wall_1", plankText_1);
        ModelFile wall_2 = models().fenceGateWall(gate.getRegistryName().getPath() + "_wall_2", plankText_2);
        ModelFile wall_3 = models().fenceGateWall(gate.getRegistryName().getPath() + "_wall_3", plankText_3);
        ModelFile wallOpen_0 = models().fenceGateWallOpen(gate.getRegistryName().getPath() + "_wall_open", plankText_0);
        ModelFile wallOpen_1 = models().fenceGateWallOpen(gate.getRegistryName().getPath() + "_wall_open_1", plankText_1);
        ModelFile wallOpen_2 = models().fenceGateWallOpen(gate.getRegistryName().getPath() + "_wall_open_2", plankText_2);
        ModelFile wallOpen_3 = models().fenceGateWallOpen(gate.getRegistryName().getPath() + "_wall_open_3", plankText_3);

        // [VanillaCopy] super.fenceGateBlock except with more models
        getVariantBuilder(gate).forAllStatesExcept(state -> {
            ModelFile model_0 = gate_0;
            ModelFile model_1 = gate_1;
            ModelFile model_2 = gate_2;
            ModelFile model_3 = gate_3;
            if (state.get(FenceGateBlock.IN_WALL)) {
                model_0 = wall_0;
                model_1 = wall_1;
                model_2 = wall_2;
                model_3 = wall_3;
            }
            if (state.get(FenceGateBlock.OPEN)) {
                model_0 = model_0 == wall_0 ? wallOpen_0 : open_0;
                model_1 = model_1 == wall_1 ? wallOpen_1 : open_1;
                model_2 = model_2 == wall_2 ? wallOpen_2 : open_2;
                model_3 = model_3 == wall_3 ? wallOpen_3 : open_3;
            }
            return ConfiguredModel.builder()
                    .weight(10).modelFile(model_0)
                    .rotationY((int) state.get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalAngle())
                    .uvLock(true).nextModel()

                    .weight(10).modelFile(model_1)
                    .rotationY((int) state.get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalAngle())
                    .uvLock(true).nextModel()

                    .weight(1).modelFile(model_2)
                    .rotationY((int) state.get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalAngle())
                    .uvLock(true).nextModel()

                    .weight(1).modelFile(model_3)
                    .rotationY((int) state.get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalAngle())
                    .uvLock(true)
                    .build();
        }, FenceGateBlock.POWERED);

        models().withExistingParent("item/" + gate.getRegistryName().getPath(), TitanRealms.rl("block/" + gate.getRegistryName().getPath()));
    }

    private void generateWoodPlate(Block plate, ResourceLocation plankText_0, ResourceLocation plankText_1,
                                   ResourceLocation plankText_2, ResourceLocation plankText_3) {
        ConfiguredModel[] unpressed = ConfiguredModel.builder()
                .weight(10).modelFile(models().withExistingParent(plate.getRegistryName().getPath(), "pressure_plate_up").texture("texture", plankText_0)).nextModel()
                .weight(10).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_1", "pressure_plate_up").texture("texture", plankText_1)).nextModel()
                .weight(1).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_2", "pressure_plate_up").texture("texture", plankText_2)).nextModel()
                .weight(1).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_3", "pressure_plate_up").texture("texture", plankText_3)).build();
        ConfiguredModel[] pressed = ConfiguredModel.builder()
                .weight(10).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_down", "pressure_plate_down").texture("texture", plankText_0)).nextModel()
                .weight(10).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_down_1", "pressure_plate_down").texture("texture", plankText_1)).nextModel()
                .weight(1).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_down_2", "pressure_plate_down").texture("texture", plankText_2)).nextModel()
                .weight(1).modelFile(models().withExistingParent(plate.getRegistryName().getPath() + "_down_3", "pressure_plate_down").texture("texture", plankText_3)).build();

        getVariantBuilder(plate).partialState().with(PressurePlateBlock.POWERED, false).setModels(unpressed);
        getVariantBuilder(plate).partialState().with(PressurePlateBlock.POWERED, true).setModels(pressed);

        models().withExistingParent("item/" + plate.getRegistryName().getPath(), TitanRealms.rl("block/" + plate.getRegistryName().getPath()));
    }

    private void generateWoodButton(Block button, ResourceLocation plankText_0, ResourceLocation plankText_1,
                                    ResourceLocation plankText_2, ResourceLocation plankText_3) {
        ModelFile unpressed_0 = models().withExistingParent(button.getRegistryName().getPath(), "button").texture("texture", plankText_0);
        ModelFile pressed_0 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed", "button_pressed").texture("texture", plankText_0);
        ModelFile unpressed_1 = models().withExistingParent(button.getRegistryName().getPath() + "_1", "button").texture("texture", plankText_1);
        ModelFile pressed_1 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_1", "button_pressed").texture("texture", plankText_1);
        ModelFile unpressed_2 = models().withExistingParent(button.getRegistryName().getPath() + "_2", "button").texture("texture", plankText_2);
        ModelFile pressed_2 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_2", "button_pressed").texture("texture", plankText_2);
        ModelFile unpressed_3 = models().withExistingParent(button.getRegistryName().getPath() + "_3", "button").texture("texture", plankText_3);
        ModelFile pressed_3 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_3", "button_pressed").texture("texture", plankText_3);

        getVariantBuilder(button).forAllStates(state -> {
            ModelFile model_0 = state.get(AbstractButtonBlock.POWERED) ? pressed_0 : unpressed_0;
            ModelFile model_1 = state.get(AbstractButtonBlock.POWERED) ? pressed_1 : unpressed_1;
            ModelFile model_2 = state.get(AbstractButtonBlock.POWERED) ? pressed_2 : unpressed_2;
            ModelFile model_3 = state.get(AbstractButtonBlock.POWERED) ? pressed_3 : unpressed_3;
            int rotX = 0;
            switch (state.get(HorizontalFaceBlock.FACE)) {
                case WALL:
                    rotX = 90;
                    break;
                case FLOOR:
                    rotX = 0;
                    break;
                case CEILING:
                    rotX = 180;
                    break;
            }
            int rotY = 0;
            if (state.get(HorizontalFaceBlock.FACE) == AttachFace.CEILING)  {
                switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
                    case NORTH: rotY = 180; break;
                    case SOUTH: rotY = 0; break;
                    case WEST: rotY = 90; break;
                    case EAST: rotY = 270; break;
                }
            } else {
                switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
                    case NORTH: rotY = 0; break;
                    case SOUTH: rotY = 180; break;
                    case WEST: rotY = 270; break;
                    case EAST: rotY = 90; break;
                }
            }
            boolean uvlock = state.get(HorizontalFaceBlock.FACE) == AttachFace.WALL;

            return ConfiguredModel.builder()
                    .weight(10).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model_0).nextModel()
                    .weight(10).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model_1).nextModel()
                    .weight(1).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model_2).nextModel()
                    .weight(1).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model_3)
                    .build();
        });

        models().withExistingParent("item/" + button.getRegistryName().getPath(), TitanRealms.rl("block/" + button.getRegistryName().getPath()));
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
