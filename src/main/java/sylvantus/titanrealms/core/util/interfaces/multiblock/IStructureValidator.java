package sylvantus.titanrealms.core.util.interfaces.multiblock;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import sylvantus.titanrealms.core.lib.math.voxel.IShape;
import sylvantus.titanrealms.core.lib.multiblock.FormationProtocol;
import sylvantus.titanrealms.core.lib.multiblock.FormationProtocol.FormationResult;
import sylvantus.titanrealms.core.lib.multiblock.MultiblockData;
import sylvantus.titanrealms.core.lib.multiblock.MultiblockManager;
import sylvantus.titanrealms.core.lib.multiblock.Structure;

public interface IStructureValidator<T extends MultiblockData> {

    void init(World world, MultiblockManager<T> manager, Structure structure);

    boolean precheck();

    FormationResult validate(FormationProtocol<T> ctx, Long2ObjectMap<IChunk> chunkMap);

    FormationResult postcheck(T structure, Set<BlockPos> innerNodes, Long2ObjectMap<IChunk> chunkMap);

    IShape getShape();
}
