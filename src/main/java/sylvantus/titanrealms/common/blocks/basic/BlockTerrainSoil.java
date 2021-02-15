package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.blocktypes.BlockShapes;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockTerrainSoil extends BlockTerrain implements IGrowable {

    public BlockTerrainSoil(BlockTerrainInfo terrain) {
        super(terrain);
    }

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BlockShapes.BLOCK_TERRAIN_SOIL;
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
        TitanRealms.LOGGER.debug("Calling randomTick for soil block");
        BlockState above = worldIn.getBlockState(pos.up());
        Material aboveMaterial = above.getMaterial();

        int soilBonus = this.getTerrainInfo().getSoilBonus();
        if (above.getBlock() instanceof IGrowable && soilBonus > 0) {
            boolean shouldBoost = random.nextInt(5) < soilBonus;
            TitanRealms.LOGGER.debug("Choosing to " + (shouldBoost ? "" : "not ") + "boost");
            if (shouldBoost) {
                BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), worldIn, pos.up());
            }
        }

        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    @Deprecated
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        BlockState above = worldIn.getBlockState(pos.up());
        Material aboveMaterial = above.getMaterial();

        if (aboveMaterial.isSolid()) {
            worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
        }
    }

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
