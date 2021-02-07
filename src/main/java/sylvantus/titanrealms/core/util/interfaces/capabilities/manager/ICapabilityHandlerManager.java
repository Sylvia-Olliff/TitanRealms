package sylvantus.titanrealms.core.util.interfaces.capabilities.manager;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.Direction;
import sylvantus.titanrealms.core.util.interfaces.capabilities.ICapabilityResolver;

import javax.annotation.Nullable;
import java.util.List;

@MethodsReturnNonnullByDefault
public interface ICapabilityHandlerManager<CONTAINER> extends ICapabilityResolver {

    /**
     * Checks if the capability handler manager can handle this substance type.
     *
     * @return {@code true} if it can handle the substance type, {@code false} otherwise.
     */
    boolean canHandle();

    /**
     * Gets the containers for a given side.
     *
     * @param side The side
     *
     * @return Containers on the given side
     */
    List<CONTAINER> getContainers(@Nullable Direction side);
}
