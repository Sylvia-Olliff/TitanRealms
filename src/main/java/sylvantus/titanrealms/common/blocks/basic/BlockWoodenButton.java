package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

public class BlockWoodenButton extends WoodButtonBlock {

    public BlockWoodenButton() {
        super(Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.5F).sound(SoundType.WOOD));
    }
}
