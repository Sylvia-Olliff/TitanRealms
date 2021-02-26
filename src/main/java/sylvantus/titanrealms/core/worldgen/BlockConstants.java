package sylvantus.titanrealms.core.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;

public final class BlockConstants {
    public static final BlockState WATER = Blocks.WATER.getDefaultState();
    public static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    public static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
    public static final BlockState SPRUCE_WOOD = Blocks.SPRUCE_WOOD.getDefaultState();
    public static final BlockState SPRUCE_LEAVES = Blocks.SPRUCE_LEAVES.getDefaultState();
    public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
    public static final BlockState OAK_WOOD = Blocks.OAK_WOOD.getDefaultState();
    public static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
    public static final BlockState AIRWOOD_LOG = TitanRealmsBlocks.AIRWOOD_LOG.getBlock().getDefaultState();
    public static final BlockState STORMWOOD_LOG = TitanRealmsBlocks.STORMWOOD_LOG.getBlock().getDefaultState();
}
