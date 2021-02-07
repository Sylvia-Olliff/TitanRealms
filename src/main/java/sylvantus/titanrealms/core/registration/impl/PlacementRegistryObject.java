package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.WrappedRegistryObject;

import javax.annotation.Nonnull;

public class PlacementRegistryObject<CONFIG extends IPlacementConfig, PLACEMENT extends Placement<CONFIG>> extends WrappedRegistryObject<PLACEMENT> {

    public PlacementRegistryObject(RegistryObject<PLACEMENT> registryObject) {
        super(registryObject);
    }

    @Nonnull
    public PLACEMENT getPlacement() {
        return get();
    }

    @Nonnull
    public ConfiguredPlacement<CONFIG> getConfigured(CONFIG placementConfig) {
        return getPlacement().configure(placementConfig);
    }
}
