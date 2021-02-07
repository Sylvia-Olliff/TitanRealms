package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import sylvantus.titanrealms.core.registration.WrappedDeferredRegister;

public class EntityTypeDeferredRegister extends WrappedDeferredRegister<EntityType<?>> {

    public EntityTypeDeferredRegister(String modid) {
        super(modid, ForgeRegistries.ENTITIES);
    }

    public <ENTITY extends Entity> EntityTypeRegistryObject<ENTITY> register(String name, EntityType.Builder<ENTITY> builder) {
        return register(name, () -> builder.build(name), EntityTypeRegistryObject::new);
    }
}
