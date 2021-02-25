package sylvantus.titanrealms.common.items.block;

import sylvantus.titanrealms.common.blocks.basic.BlockWoodenDoor;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockWoodenDoor extends ItemBlockTitanRealms<BlockWoodenDoor> {

    public ItemBlockWoodenDoor(BlockWoodenDoor block) {
        super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(16));
    }
}
