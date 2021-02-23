package sylvantus.titanrealms.common.items.block;

import net.minecraft.item.Item;
import sylvantus.titanrealms.common.blocks.basic.BlockFlammable;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockPlanks extends ItemBlockTitanRealms<BlockFlammable> {

    public ItemBlockPlanks(BlockFlammable block) { super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64)); }
}
