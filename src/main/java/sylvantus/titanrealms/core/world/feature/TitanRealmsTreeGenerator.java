package sylvantus.titanrealms.core.world.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.template.Template;
import sylvantus.titanrealms.core.util.FeatureUtils;
import sylvantus.titanrealms.core.world.feature.config.TreeFeatureConfig;

import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class TitanRealmsTreeGenerator<T extends TreeFeatureConfig> extends Feature<T> {

    public TitanRealmsTreeGenerator(Codec<T> configIn) {
        super(configIn);
    }

    protected boolean generate(IWorld world, Random random, BlockPos pos, Set<BlockPos> logpos, Set<BlockPos> leavespos, MutableBoundingBox mbb, T config) {
        Set<BlockPos> branchSet = Sets.newHashSet();
        Set<BlockPos> rootSet = Sets.newHashSet();
        return generate(world, random, pos, logpos, leavespos, branchSet, rootSet, mbb, config);
    }

    //AbstractTreeFeature.place from 1.15, modified for us
    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, T config) {
        Set<BlockPos> logs = Sets.newHashSet();
        Set<BlockPos> leaves = Sets.newHashSet();
        MutableBoundingBox mutableboundingbox = MutableBoundingBox.getNewBoundingBox();
        boolean flag = this.generate(world, random, pos, logs, leaves, mutableboundingbox, config);
        if (mutableboundingbox.minX <= mutableboundingbox.maxX && flag && !logs.isEmpty()) {
            VoxelShapePart voxelshapepart = this.getVoxelShapePart(world, mutableboundingbox, logs);
            Template.func_222857_a(world, 3, voxelshapepart, mutableboundingbox.minX, mutableboundingbox.minY, mutableboundingbox.minZ);
            return true;
        } else {
            return false;
        }
    }

    //TreeFeature.func_227214_a_ copy, modified to remove decorations
    private VoxelShapePart getVoxelShapePart(IWorld world, MutableBoundingBox mbb, Set<BlockPos> logPosSet) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(mbb.getXSize(), mbb.getYSize(), mbb.getZSize());

        for(int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(BlockPos logPos : Lists.newArrayList(logPosSet)) {
            if (mbb.isVecInside(logPos)) {
                voxelshapepart.setFilled(logPos.getX() - mbb.minX, logPos.getY() - mbb.minY, logPos.getZ() - mbb.minZ, true, true);
            }

            for(Direction direction : Direction.values()) {
                mutable.setAndMove(logPos, direction);
                if (!logPosSet.contains(mutable)) {
                    BlockState blockstate = world.getBlockState(mutable);
                    if (blockstate.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
                        list.get(0).add(mutable.toImmutable());
                        TreeFeature.func_236408_b_(world, mutable, blockstate.with(BlockStateProperties.DISTANCE_1_7, 1));
                        if (mbb.isVecInside(mutable)) {
                            voxelshapepart.setFilled(mutable.getX() - mbb.minX, mutable.getY() - mbb.minY, mutable.getZ() - mbb.minZ, true, true);
                        }
                    }
                }
            }
        }

        for(int l = 1; l < 6; ++l) {
            Set<BlockPos> set = list.get(l - 1);
            Set<BlockPos> set1 = list.get(l);

            for(BlockPos blockpos2 : set) {
                if (mbb.isVecInside(blockpos2)) {
                    voxelshapepart.setFilled(blockpos2.getX() - mbb.minX, blockpos2.getY() - mbb.minY, blockpos2.getZ() - mbb.minZ, true, true);
                }

                for(Direction direction1 : Direction.values()) {
                    mutable.setAndMove(blockpos2, direction1);
                    if (!set.contains(mutable) && !set1.contains(mutable)) {
                        BlockState blockstate1 = world.getBlockState(mutable);
                        if (blockstate1.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
                            int k = blockstate1.get(BlockStateProperties.DISTANCE_1_7);
                            if (k > l + 1) {
                                BlockState blockstate2 = blockstate1.with(BlockStateProperties.DISTANCE_1_7, l + 1);
                                TreeFeature.func_236408_b_(world, mutable, blockstate2);
                                if (mbb.isVecInside(mutable)) {
                                    voxelshapepart.setFilled(mutable.getX() - mbb.minX, mutable.getY() - mbb.minY, mutable.getZ() - mbb.minZ, true, true);
                                }

                                set1.add(mutable.toImmutable());
                            }
                        }
                    }
                }
            }
        }

        return voxelshapepart;
    }

    /**
     * This works akin to the AbstractTreeFeature.generate, but put our branches and roots here
     */
    protected abstract boolean generate(IWorld world, Random random, BlockPos pos, Set<BlockPos> logpos, Set<BlockPos> leavespos, Set<BlockPos> branchpos, Set<BlockPos> rootpos, MutableBoundingBox mbb, T config);

    //AbstractTrunkPlacer.func_236911_a_ copy - Use that one instead when extending that Abstract
    protected boolean setLogBlockState(IWorld world, Random random, BlockPos pos, Set<BlockPos> logPos, MutableBoundingBox mbb, TreeFeatureConfig config) {
        if (TreeFeature.isReplaceableAt(world, pos)) {
            this.setBlockState(world, pos, config.trunkProvider.getBlockState(random, pos), mbb);
            logPos.add(pos.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    //We aren't actually using this, but it is here just in case
    protected boolean setLeavesBlockState(IWorld world, Random random, BlockPos pos, Set<BlockPos> leavesPos, MutableBoundingBox mbb, TreeFeatureConfig config) {
        if (TreeFeature.isReplaceableAt(world, pos)) {
            this.setBlockState(world, pos, config.leavesProvider.getBlockState(random, pos), mbb);
            leavesPos.add(pos.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    public boolean setBranchBlockState(IWorld world, Random random, BlockPos pos, Set<BlockPos> branchpos, MutableBoundingBox mbb, TreeFeatureConfig config) {
        if (TreeFeature.isReplaceableAt(world, pos)) {
            this.setBlockState(world, pos, config.branchProvider.getBlockState(random, pos), mbb);
            branchpos.add(pos.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    protected boolean setRootsBlockState(IWorld world, Random random, BlockPos pos, Set<BlockPos> branchpos, MutableBoundingBox mbb, TreeFeatureConfig config) {
        // XXX: This was originally an IWorld in AbstractTreeFeature.place, so it should be ok to cast it back.
        // If you're here investigating after it blew up, then the above assumption is no longer true.
        if (FeatureUtils.canRootGrowIn(world, pos)) {
            this.setBlockState(world, pos, config.rootsProvider.getBlockState(random, pos), mbb);
            branchpos.add(pos.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    protected final void setBlockState(IWorldWriter world, BlockPos pos, BlockState state, MutableBoundingBox mbb) {
        world.setBlockState(pos, state, 19);
        mbb.expandTo(new MutableBoundingBox(pos, pos));
    }

    /**
     * Build a root, but don't let it stick out too far into thin air because that's weird
     */
    protected void buildRoot(IWorld world, Random rand, BlockPos pos, Set<BlockPos> setpos, double offset, int b, MutableBoundingBox mbb, T config) {
        BlockPos dest = FeatureUtils.translate(pos.down(b + 2), 5, 0.3 * b + offset, 0.8);

        // go through block by block and stop drawing when we head too far into open air
        BlockPos[] lineArray = FeatureUtils.getBresenhamArrays(pos.down(), dest);
        for (BlockPos coord : lineArray) {
            this.setRootsBlockState(world, rand, coord, setpos, mbb, config);
        }
    }

    private void setIfEmpty(IWorld world, BlockPos pos, BlockState state) {
        if (world.isAirBlock(pos)) {
            world.setBlockState(pos, state,3);
        }
    }
}
