package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import sylvantus.titanrealms.core.registration.WrappedDeferredRegister;

import java.util.function.Supplier;

public class PlacementDeferredRegister extends WrappedDeferredRegister<Placement<?>> {

    public PlacementDeferredRegister(String modid) {
        super(modid, ForgeRegistries.DECORATORS);
    }

    public <CONFIG extends IPlacementConfig, PLACEMENT extends Placement<CONFIG>> PlacementRegistryObject<CONFIG, PLACEMENT> register(String name, Supplier<PLACEMENT> sup) {
        return register(name, sup, PlacementRegistryObject::new);
    }
}
