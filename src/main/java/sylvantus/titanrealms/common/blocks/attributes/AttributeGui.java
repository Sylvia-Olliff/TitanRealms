package sylvantus.titanrealms.common.blocks.attributes;

import mekanism.api.text.TextComponentUtil;
import net.minecraft.inventory.container.INamedContainerProvider;
import sylvantus.titanrealms.common.containers.providers.ContainerProvider;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsTileContainer;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.registration.impl.ContainerTypeRegistryObject;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;

import java.util.function.Function;
import java.util.function.Supplier;

public class AttributeGui implements IAttribute {

    private final Supplier<ContainerTypeRegistryObject<? extends TitanRealmsContainer>> containerRegistrar;
    private Function<TileEntityTitanRealms, INamedContainerProvider> containerSupplier = tile -> new ContainerProvider(TextComponentUtil.build(tile.getBlockType()),
            (i, inv, player) -> new TitanRealmsTileContainer<>(getContainerType(), i, inv, tile));

    public AttributeGui(Supplier<ContainerTypeRegistryObject<? extends TitanRealmsContainer>> containerRegistrar) {
        this.containerRegistrar = containerRegistrar;
    }

    public void setCustomContainer(Function<TileEntityTitanRealms, INamedContainerProvider> containerSupplier) {
        this.containerSupplier = containerSupplier;
    }

    public ContainerTypeRegistryObject<? extends TitanRealmsContainer> getContainerType() {
        return containerRegistrar.get();
    }

    public INamedContainerProvider getProvider(TileEntityTitanRealms tile) {
        return containerSupplier.apply(tile);
    }
}
