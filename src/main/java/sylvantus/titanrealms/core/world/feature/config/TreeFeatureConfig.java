package sylvantus.titanrealms.core.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class TreeFeatureConfig implements IFeatureConfig {
    public static final Codec<TreeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((obj) -> obj.trunkProvider),
                    BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter((obj) -> obj.leavesProvider),
                    BlockStateProvider.CODEC.fieldOf("branch_provider").forGetter((obj) -> obj.branchProvider),
                    BlockStateProvider.CODEC.fieldOf("roots_provider").forGetter((obj) -> obj.rootsProvider),
                    Codec.INT.fieldOf("minimum_size").orElse(20).forGetter((obj) -> obj.minHeight),
                    Codec.INT.fieldOf("add_first_five_chance").orElse(1).forGetter((obj) -> obj.chanceAddFiveFirst),
                    Codec.INT.fieldOf("add_second_five_chance").orElse(1).forGetter((obj) -> obj.chanceAddFiveSecond),
                    Codec.BOOL.fieldOf("has_leaves").orElse(true).forGetter((obj) -> obj.hasLeaves),
                    Codec.BOOL.fieldOf("check_water").orElse(false).forGetter((obj) -> obj.checkWater),
                    BlockStateProvider.CODEC.fieldOf("sapling").orElse(new SimpleBlockStateProvider(Blocks.OAK_SAPLING.getDefaultState())).forGetter((obj) -> obj.sapling))
                    .apply(instance, TreeFeatureConfig::new));

    public final BlockStateProvider trunkProvider;
    public final BlockStateProvider leavesProvider;
    public final BlockStateProvider branchProvider;
    public final BlockStateProvider rootsProvider;
    public final int minHeight;
    public final int chanceAddFiveFirst;
    public final int chanceAddFiveSecond;
    public final boolean hasLeaves;
    public final boolean checkWater;
    public final BlockStateProvider sapling;
    public transient boolean forcePlacement;

    public TreeFeatureConfig(BlockStateProvider trunk, BlockStateProvider leaves, BlockStateProvider branch, BlockStateProvider roots, int height, int chanceFiveFirst, int chanceFiveSecond, boolean hasLeaves, boolean checkWater, BlockStateProvider sapling) {
        this.trunkProvider = trunk;
        this.leavesProvider = leaves;
        this.branchProvider = branch;
        this.rootsProvider = roots;
        this.minHeight = height;
        // For some dumb reason this keeps getting -1 so you get `Math.max(x, 0)` for punishment
        this.chanceAddFiveFirst = Math.max(chanceFiveFirst, 1);
        this.chanceAddFiveSecond = Math.max(chanceFiveSecond, 1);
        this.hasLeaves = hasLeaves;
        this.checkWater = checkWater;
        this.sapling = sapling;
    }

    public void forcePlacement() {
        this.forcePlacement = true;
    }

    public IPlantable getSapling(Random rand, BlockPos pos) {
        return (IPlantable) sapling.getBlockState(rand, pos).getBlock();
    }

    public static class Builder {
        private BlockStateProvider trunkProvider;
        private BlockStateProvider leavesProvider;
        private BlockStateProvider branchProvider;
        private BlockStateProvider rootsProvider;
        private int baseHeight;
        private int chanceFirstFive;
        private int chanceSecondFive;
        private boolean hasLeaves;
        private boolean checkWater;
        private BlockStateProvider sapling;

        public Builder(BlockStateProvider trunk, BlockStateProvider leaves, BlockStateProvider branch, BlockStateProvider roots) {
            this.trunkProvider = trunk;
            this.leavesProvider = leaves;
            this.branchProvider = branch;
            this.rootsProvider = roots;
        }

        public TreeFeatureConfig.Builder minHeight(int height) {
            this.baseHeight = height;
            return this;
        }

        public TreeFeatureConfig.Builder chanceFirstFive(int chance) {
            this.chanceFirstFive = chance;
            return this;
        }

        public TreeFeatureConfig.Builder chanceSecondFive(int chance) {
            this.chanceSecondFive = chance;
            return this;
        }

        public TreeFeatureConfig.Builder noLeaves() {
            this.hasLeaves = false;
            return this;
        }

        public TreeFeatureConfig.Builder checksWater() {
            this.checkWater = true;
            return this;
        }

        public TreeFeatureConfig.Builder setSapling(SaplingBlock plant) {
            this.sapling = new SimpleBlockStateProvider(plant.getDefaultState());
            return this;
        }

        public TreeFeatureConfig build() {
            return new TreeFeatureConfig(trunkProvider, leavesProvider, branchProvider, rootsProvider, baseHeight, chanceFirstFive, chanceSecondFive, hasLeaves, checkWater, sapling);
        }
    }
}
