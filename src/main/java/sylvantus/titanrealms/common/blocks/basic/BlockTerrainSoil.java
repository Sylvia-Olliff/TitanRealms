package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockTerrainSoil extends BlockTerrain implements IGrowable {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    private static final BooleanProperty NORTH = SixWayBlock.NORTH;
    private static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    private static final BooleanProperty WEST = SixWayBlock.WEST;
    private static final BooleanProperty EAST = SixWayBlock.EAST;
    private static final BooleanProperty UP = SixWayBlock.UP;
    private static final BooleanProperty DOWN = SixWayBlock.DOWN;

    final double boundingBoxWidthLower;
    final double boundingBoxWidthUpper;

    public BlockTerrainSoil(BlockTerrainInfo terrain) {
        super(terrain);

        this.boundingBoxWidthLower = 0d;
        this.boundingBoxWidthUpper = 16d;

        this.setDefaultState(stateContainer.getBaseState().with(AXIS, Direction.Axis.Y)
                .with(NORTH, false).with(WEST, false)
                .with(SOUTH, false).with(EAST, false)
                .with(DOWN, false).with(UP, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(AXIS, NORTH, EAST, SOUTH, WEST, DOWN, UP);
    }

    @Override
    @Deprecated
    @NotNull
    public BlockState updatePostPlacement(BlockState state, Direction dirToNeighbor, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return state.with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(dirToNeighbor), canConnectTo(state, dirToNeighbor, neighborState, world, pos, neighborPos));
    }

    protected boolean canConnectTo(BlockState state, Direction dirToNeighbor, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (!neighborState.getBlock().isAir(neighborState, world, neighborPos)) {
            if (neighborState.getBlock() instanceof IGrowable) {
                return neighborState.getBlock() instanceof BlockTerrainSoil;
            } else {
                return true;
            }
        } else {
            return false;
        }
//        return (neighborState.getBlock() == TitanRealmsBlocks.TERRAIN.get(TerrainType.CLOUD_SOIL).getBlock() ||
//                neighborState.getBlock() == TitanRealmsBlocks.TERRAIN.get(TerrainType.SPARSE_CLOUD_SOIL).getBlock() ||
//                neighborState.getBlock() == TitanRealmsBlocks.TERRAIN.get(TerrainType.DENSE_CLOUD_SOIL).getBlock())
//                && state.get(AXIS) != dirToNeighbor.getAxis();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        BlockState ret = getDefaultState().with(AXIS, ctx.getFace().getAxis());

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = ctx.getPos().offset(dir);
            boolean connect = canConnectTo(ret, dir, ctx.getWorld().getBlockState(neighborPos), ctx.getWorld(), ctx.getPos(), neighborPos);
            ret = ret.with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(dir), connect);
        }
        return ret;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rotation) {
        switch(rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch(state.get(AXIS)) {
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    @Deprecated
    @NotNull
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case X:
                return makeCuboidShape(
                        0d,
                        state.get(NORTH) ?  0d : this.boundingBoxWidthLower,
                        state.get(WEST ) ?  0d : this.boundingBoxWidthLower,
                        16d,
                        state.get(SOUTH) ? 16d : this.boundingBoxWidthUpper,
                        state.get(EAST ) ? 16d : this.boundingBoxWidthUpper
                );
            case Z:
                return makeCuboidShape(
                        state.get(EAST ) ?  0d : this.boundingBoxWidthLower,
                        state.get(SOUTH) ?  0d : this.boundingBoxWidthLower,
                        0d,
                        state.get(WEST ) ? 16d : this.boundingBoxWidthUpper,
                        state.get(NORTH) ? 16d : this.boundingBoxWidthUpper,
                        16d
                );
            default:
                return makeCuboidShape(
                        state.get(WEST)  ?  0d : this.boundingBoxWidthLower,
                        0d,
                        state.get(NORTH) ?  0d : this.boundingBoxWidthLower,
                        state.get(EAST)  ? 16d : this.boundingBoxWidthUpper,
                        16d,
                        state.get(SOUTH) ? 16d : this.boundingBoxWidthUpper
                );
        }
    }

    @Override
    public boolean isVariableOpacity() {
        return this.getTerrainInfo().isTransparent();
    }

    @Override
    @Deprecated
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return this.getTerrainInfo().isTransparent() ? 0 : 1;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction, IPlantable plantable) {
        if (direction != Direction.UP)
            return false;
        PlantType plantType = plantable.getPlantType(world, pos.offset(direction));
        return plantType == PlantType.CROP || plantType == PlantType.PLAINS || plantType == PlantType.CAVE;
    }

    @Override
    public boolean isFertile(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    @Deprecated
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockState above = worldIn.getBlockState(pos.up());

        int soilBonus = this.getTerrainInfo().getSoilBonus();
        if (above.getBlock() instanceof IGrowable && soilBonus > 0) {
            boolean shouldBoost = random.nextInt(5) < soilBonus;
            if (shouldBoost) {
                BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), worldIn, pos.up());
            }
        }

        super.randomTick(state, worldIn, pos, random);
    }

//    @Override
//    @Deprecated
//    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
//        Material aboveMaterial = worldIn.getBlockState(pos.up()).getMaterial();
//
//        if (aboveMaterial.isSolid()) {
//            worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
//        }
//    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        pos = pos.offset(Direction.Plane.HORIZONTAL.random(rand));

        Block blockAt = worldIn.getBlockState(pos).getBlock();
        if (worldIn.isAirBlock(pos.up()) && (blockAt == Blocks.DIRT || blockAt == Blocks.GRASS_BLOCK || blockAt == Blocks.FARMLAND)) {
            worldIn.setBlockState(pos, this.getDefaultState());
        }
    }
}
