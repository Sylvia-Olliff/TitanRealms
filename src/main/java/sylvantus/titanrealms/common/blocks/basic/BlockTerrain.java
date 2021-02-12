package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import sylvantus.titanrealms.common.blocks.TitanRealmsBlock;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;

import javax.annotation.Nonnull;

public class BlockTerrain extends TitanRealmsBlock {

    @Nonnull
    private final BlockTerrainInfo terrainInfo;

    public BlockTerrain(@Nonnull BlockTerrainInfo terrain) {
        super(terrain.buildProperties());
        this.terrainInfo = terrain;
    }

    @Nonnull
    public BlockTerrainInfo getTerrainInfo() { return terrainInfo; }

    @Nonnull
    @Override
    @Deprecated
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        return terrainInfo.getPushReaction();
    }

    @Override
    public boolean isPortalFrame(BlockState state, IBlockReader world, BlockPos pos) {
        return terrainInfo.isPortalFrame();
    }
}
