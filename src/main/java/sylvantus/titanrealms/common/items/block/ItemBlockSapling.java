package sylvantus.titanrealms.common.items.block;

import sylvantus.titanrealms.common.blocks.basic.BlockSapling;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockSapling extends ItemBlockTitanRealms<BlockSapling> {

    public ItemBlockSapling(BlockSapling block) {
        super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64));
    }
}
