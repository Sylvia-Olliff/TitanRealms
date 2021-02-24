package sylvantus.titanrealms.common.items.block;

import net.minecraft.block.FenceBlock;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockFence extends ItemBlockTitanRealms<FenceBlock> {
    public ItemBlockFence(FenceBlock block) { super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64)); }
}
