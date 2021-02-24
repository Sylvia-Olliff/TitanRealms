package sylvantus.titanrealms.common.items.block;

import net.minecraft.block.FenceGateBlock;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockGate extends ItemBlockTitanRealms<FenceGateBlock> {
    public ItemBlockGate(FenceGateBlock block) { super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64)); }
}
