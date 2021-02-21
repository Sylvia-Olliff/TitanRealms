package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class BlockTitanRealmsStairs extends StairsBlock {

    protected BlockTitanRealmsStairs(BlockState modelState) {
        super(() -> modelState, Properties.from(modelState.getBlock()));
    }
}
