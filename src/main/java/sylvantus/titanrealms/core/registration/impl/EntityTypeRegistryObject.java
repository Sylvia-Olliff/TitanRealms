package sylvantus.titanrealms.core.registration.impl;

import mekanism.api.providers.IEntityTypeProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.WrappedRegistryObject;

import javax.annotation.Nonnull;

public class EntityTypeRegistryObject<ENTITY extends Entity> extends WrappedRegistryObject<EntityType<ENTITY>> implements IEntityTypeProvider {

    public EntityTypeRegistryObject(RegistryObject<EntityType<ENTITY>> registryObject) {
        super(registryObject);
    }

    @Nonnull
    @Override
    public EntityType<ENTITY> getEntityType() { return get(); }
}
