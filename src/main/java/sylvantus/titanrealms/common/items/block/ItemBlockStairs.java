package sylvantus.titanrealms.common.items.block;

import net.minecraft.item.Item;
import sylvantus.titanrealms.common.blocks.basic.BlockTitanRealmsStairs;
import sylvantus.titanrealms.core.registration.impl.ItemDeferredRegister;

public class ItemBlockStairs extends ItemBlockTitanRealms<BlockTitanRealmsStairs> {

    public ItemBlockStairs(BlockTitanRealmsStairs block) { super(block, ItemDeferredRegister.getTRBaseProperties().maxStackSize(64)); }
}
