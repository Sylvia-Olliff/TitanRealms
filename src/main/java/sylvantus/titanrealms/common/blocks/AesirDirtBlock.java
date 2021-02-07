package sylvantus.titanrealms.common.blocks;

import net.minecraft.block.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;

import java.util.Random;

public class AesirDirtBlock extends SpreadableSnowyDirtBlock {
    public AesirDirtBlock(Block.Properties props) {
        super(props);
    }

    private static boolean canSnow(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        if (world.getFluidState(blockPos).isTagged(FluidTags.WATER))
            return false;

        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == Blocks.SNOW && blockState.get(SnowBlock.LAYERS) == 1)
            return true;
        else {
            int lightLevel = LightEngine.func_215613_a(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return lightLevel < world.getMaxLightLevel();
        }

    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!canSnow(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return;
             worldIn.setBlockState(pos, TitanRealmsBlocks.AESIR_DIRT_GRASS.getBlock().getDefaultState());
        } else {
          BlockState blockState = this.getDefaultState();

          for (int i = 0; i < 4; i++) {
              BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

              if (worldIn.getBlockState(blockPos).isIn(TitanRealmsBlocks.AESIR_DIRT.getBlock()) && canSnow(blockState, worldIn, blockPos)) {
                  worldIn.setBlockState(blockPos, blockState.with(SNOWY, worldIn.getBlockState(blockPos.up()).isIn(Blocks.SNOW)));
              }
          }
        }
    }
}
