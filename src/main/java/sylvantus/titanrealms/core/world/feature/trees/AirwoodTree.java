package sylvantus.titanrealms.core.world.feature.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import sylvantus.titanrealms.core.worldgen.ConfiguredFeatures;

import java.util.Random;

public class AirwoodTree extends Tree {

    @Override
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random rand, boolean largeHive) {
        return ConfiguredFeatures.AIRWOOD_TREE_BASE;
    }
}
