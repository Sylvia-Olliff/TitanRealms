package sylvantus.titanrealms.core.registration.impl;

import mekanism.api.providers.IBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.DoubleWrappedRegistryObject;

import javax.annotation.Nonnull;

public class BlockRegistryObject<BLOCK extends Block, ITEM extends Item> extends DoubleWrappedRegistryObject<BLOCK, ITEM> implements IBlockProvider {

    public BlockRegistryObject(RegistryObject<BLOCK> blockRegistryObject, RegistryObject<ITEM> itemRegistryObject) {
        super(blockRegistryObject, itemRegistryObject);
    }

    @Nonnull
    @Override
    public BLOCK getBlock() { return getPrimary(); }

    @Nonnull
    @Override
    public ITEM getItem() { return getSecondary(); }

    @Nonnull
    @Override
    public ITEM asItem() { return getItem(); }
}
