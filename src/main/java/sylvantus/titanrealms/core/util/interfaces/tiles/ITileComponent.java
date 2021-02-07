package sylvantus.titanrealms.core.util.interfaces.tiles;

import net.minecraft.nbt.CompoundNBT;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;

public interface ITileComponent {

    void tick();

    void read(CompoundNBT nbtTags);

    void write(CompoundNBT nbtTags);

    default void invalidate() {}

    default void onChunkLoaded() {}

    void trackForMainContainer(TitanRealmsContainer container);

    void addToUpdateTage(CompoundNBT updateTag);

    void readFromUpdateTag(CompoundNBT updateTag);
}
