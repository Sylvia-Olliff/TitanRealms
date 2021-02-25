package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class BlockSapling extends SaplingBlock {

    public BlockSapling(Tree tree) {
        super(tree, Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).sound(SoundType.PLANT).doesNotBlockMovement().tickRandomly());
    }
}
