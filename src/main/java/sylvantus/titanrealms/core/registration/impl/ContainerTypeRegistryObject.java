package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.WrappedRegistryObject;

import javax.annotation.Nonnull;

public class ContainerTypeRegistryObject<CONTAINER extends Container> extends WrappedRegistryObject<ContainerType<CONTAINER>> {

    public ContainerTypeRegistryObject(RegistryObject<ContainerType<CONTAINER>> registryObject) {
        super(registryObject);
    }

    @Nonnull
    public ContainerType<CONTAINER> getContainerType() { return get(); }

    ContainerTypeRegistryObject<CONTAINER> setRegistryObject(RegistryObject<ContainerType<CONTAINER>> registryObject) {
        this.registryObject = registryObject;
        return this;
    }
}
