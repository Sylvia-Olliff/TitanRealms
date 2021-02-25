package sylvantus.titanrealms.common.items.block;

import sylvantus.titanrealms.common.blocks.basic.BlockWoodenPressurePlate;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockWoodenPressurePlate extends ItemBlockTitanRealms<BlockWoodenPressurePlate> {

    public ItemBlockWoodenPressurePlate(BlockWoodenPressurePlate block) {
        super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64));
    }
}
