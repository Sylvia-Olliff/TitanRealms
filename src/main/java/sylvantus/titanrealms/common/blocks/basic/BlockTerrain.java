package sylvantus.titanrealms.common.blocks.basic;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sylvantus.titanrealms.common.blocks.TitanRealmsBlock;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockTerrain extends TitanRealmsBlock {

    @Nonnull
    private final BlockTerrainInfo terrainInfo;

    public BlockTerrain(BlockTerrainInfo terrain) {
        super(terrain.buildProperties());
        this.terrainInfo = terrain;
    }

    @Nonnull
    public BlockTerrainInfo getTerrainInfo() { return terrainInfo; }

    @Nonnull
    @Override
    @Deprecated
    public PushReaction getPushReaction(BlockState state) {
        return terrainInfo.getPushReaction();
    }

    @Override
    public boolean isPortalFrame(BlockState state, IBlockReader world, BlockPos pos) {
        return terrainInfo.isPortalFrame();
    }

    @Override
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (this.terrainInfo.isTransparent()) {
            return adjacentBlockState.isIn(this) || super.isSideInvisible(state, adjacentBlockState, side);
        } else {
            return super.isSideInvisible(state, adjacentBlockState, side);
        }
    }

    @Override
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return this.terrainInfo.isTransparent() ? 1.0F : super.getAmbientOcclusionLightValue(state, worldIn, pos);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return this.terrainInfo.isTransparent() || super.propagatesSkylightDown(state, reader, pos);
    }

    @Override
    @Deprecated
    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return this.terrainInfo.isTransparent() ? VoxelShapes.empty() : super.getRayTraceShape(state, reader, pos, context);
    }
}
