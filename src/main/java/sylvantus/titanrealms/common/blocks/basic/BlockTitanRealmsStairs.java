package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class BlockTitanRealmsStairs extends StairsBlock {

    public BlockTitanRealmsStairs(Block rootBlock) {
        super(rootBlock::getDefaultState, Properties.from(rootBlock));
    }
}
