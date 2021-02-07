package sylvantus.titanrealms.common.capabilities;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.config.tile.components.TileComponentConfig;
import sylvantus.titanrealms.core.util.interfaces.capabilities.ICapabilityResolver;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BooleanSupplier;

public class CapabilityCache {

    private final Map<Capability<?>, ICapabilityResolver> capabilityResolvers = new HashMap<>();
    private final List<ICapabilityResolver> uniqueResolvers = new ArrayList<>();
    private final Set<Capability<?>> alwaysDisabled = new HashSet<>();
    private final Map<Capability<?>, List<BooleanSupplier>> semiDisabled = new HashMap<>();
    private TileComponentConfig config;

    public void addCapabilityResolver(ICapabilityResolver resolver) {
        uniqueResolvers.add(resolver);
        List<Capability<?>> supportedCapabilities = resolver.getSupportedCapabilities();
        for (Capability<?> supportedCapability : supportedCapabilities) {
            if (supportedCapability != null && capabilityResolvers.put(supportedCapability, resolver) != null) {
                TitanRealms.LOGGER.warn("Multiple capability resolvers registered for {}. Overriding", supportedCapability.getName(), new Exception());
            }
        }
    }

    public void addDisabledCapabilities(Capability<?>... capabilities) {
        for (Capability<?> capability : capabilities) {
            //Don't add null capabilities. (Either ones that are not loaded mod wise or get fired during startup)
            if (capability != null) {
                alwaysDisabled.add(capability);
            }
        }
    }

    public void addDisabledCapabilities(Collection<Capability<?>> capabilities) {
        for (Capability<?> capability : capabilities) {
            //Don't add null capabilities. (Either ones that are not loaded mod wise or get fired during startup)
            if (capability != null) {
                alwaysDisabled.add(capability);
            }
        }
    }

    public void addSemiDisabledCapability(Capability<?> capability, BooleanSupplier checker) {
        //Don't add null capabilities. (Either ones that are not loaded mod wise or get fired during startup)
        if (capability != null) {
            semiDisabled.computeIfAbsent(capability, cap -> new ArrayList<>()).add(checker);
        }
    }

    /**
     * Adds the given config component for use in checking if capabilities are disabled on a specific side.
     */
    public void addConfigComponent(TileComponentConfig config) {
        if (this.config != null) {
            TitanRealms.LOGGER.warn("Config component already registered. Overriding", new Exception());
        }
        this.config = config;
    }

    public boolean isCapabilityDisabled(Capability<?> capability, @Nullable Direction side) {
        if (alwaysDisabled.contains(capability)) {
            return true;
        }
        if (semiDisabled.containsKey(capability)) {
            List<BooleanSupplier> predicates = semiDisabled.get(capability);
            for (BooleanSupplier predicate : predicates) {
                if (predicate.getAsBoolean()) {
                    return true;
                }
            }
        }
        return false;
//        if (config == null) {
//            return false;
//        }
//        return config.isCapabilityDisabled(capability, side);
    }

    public boolean canResolve(Capability<?> capability) {
        return capabilityResolvers.containsKey(capability);
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        if (!isCapabilityDisabled(capability, side) && canResolve(capability)) {
            return getCapabilityUnchecked(capability, side);
        }
        return LazyOptional.empty();
    }

    public <T> LazyOptional<T> getCapabilityUnchecked(Capability<T> capability, @Nullable Direction side) {
        ICapabilityResolver capabilityResolver = capabilityResolvers.get(capability);
        if (capabilityResolver == null) {
            return LazyOptional.empty();
        }
        return capabilityResolver.resolve(capability, side);
    }

    public void invalidate(Capability<?> capability, @Nullable Direction side) {
        ICapabilityResolver capabilityResolver = capabilityResolvers.get(capability);
        if (capabilityResolver != null) {
            capabilityResolver.invalidate(capability, side);
        }
    }

    public void invalidateAll() {
        uniqueResolvers.forEach(ICapabilityResolver::invalidateAll);
    }
}
