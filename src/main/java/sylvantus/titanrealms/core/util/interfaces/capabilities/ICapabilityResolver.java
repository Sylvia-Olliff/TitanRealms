package sylvantus.titanrealms.core.util.interfaces.capabilities;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface ICapabilityResolver {

    List<Capability<?>> getSupportedCapabilities();

    <T> LazyOptional<T> resolve(Capability<T> capability, @Nullable Direction side);

    void invalidate(Capability<?> capability, @Nullable Direction side);

    void invalidateAll();
}
