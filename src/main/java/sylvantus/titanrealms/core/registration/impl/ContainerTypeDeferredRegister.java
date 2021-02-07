package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.ForgeRegistries;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsTileContainer;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.registration.INamedEntry;
import sylvantus.titanrealms.core.registration.WrappedDeferredRegister;

public class ContainerTypeDeferredRegister extends WrappedDeferredRegister<ContainerType<?>> {

    public ContainerTypeDeferredRegister(String modid) { super(modid, ForgeRegistries.CONTAINERS); }

    public <TILE extends TileEntityTitanRealms> ContainerTypeRegistryObject<TitanRealmsTileContainer<TILE>> register(INamedEntry nameProvider, Class<TILE> tileClass) {
        return register(nameProvider.getInternalRegistryName(), tileClass);
    }

    public <TILE extends TileEntityTitanRealms> ContainerTypeRegistryObject<TitanRealmsTileContainer<TILE>> register(String name, Class<TILE> tileClass) {
        ContainerTypeRegistryObject<TitanRealmsTileContainer<TILE>> registryObject = new ContainerTypeRegistryObject<>(null);
        IContainerFactory<TitanRealmsTileContainer<TILE>> factory = (id, inv, buf) ->
                new TitanRealmsTileContainer<>(registryObject, id, inv, TitanRealmsTileContainer.getTileFromBuf(buf, tileClass));
        return register(name, () -> IForgeContainerType.create(factory), registryObject::setRegistryObject);
    }
}
