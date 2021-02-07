package sylvantus.titanrealms.core.registration.impl;

import mekanism.api.providers.IItemProvider;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.WrappedRegistryObject;

import javax.annotation.Nonnull;

public class ItemRegistryObject<ITEM extends Item> extends WrappedRegistryObject<ITEM> implements IItemProvider {
    public ItemRegistryObject(RegistryObject<ITEM> registryObject) {
        super(registryObject);
    }

    @Nonnull
    @Override
    public ITEM getItem() {
        return get();
    }

    @Override
    public Item asItem() {
        return getItem();
    }
}
