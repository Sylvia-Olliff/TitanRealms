package sylvantus.titanrealms.core.util.interfaces.tiles;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITileWrapper {

    BlockPos getTilePos();

    World getTileWorld();
}
