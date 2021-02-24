package sylvantus.titanrealms.common.items.block;

import net.minecraft.block.SlabBlock;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockSlab extends ItemBlockTitanRealms<SlabBlock> {
    public ItemBlockSlab(SlabBlock block) { super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64)); }
}
