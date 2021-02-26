package sylvantus.titanrealms.core.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import sylvantus.titanrealms.core.worldgen.treeplacers.BranchesConfig;
import sylvantus.titanrealms.core.worldgen.treeplacers.BranchingTrunkPlacer;
import sylvantus.titanrealms.core.worldgen.treeplacers.TreeRootsDecorator;
import sylvantus.titanrealms.core.worldgen.treeplacers.TrunkRiser;

public final class TreeConfigurations {

    public static final BaseTreeFeatureConfig AIRWOOD_TREE = new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockConstants.AIRWOOD_LOG),
            new SimpleBlockStateProvider(BlockConstants.OAK_LEAVES), // TODO: Replace with Airwood leaves
            new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(0)),
            new TrunkRiser(6, new BranchingTrunkPlacer(5, 4, 0, 1, new BranchesConfig(0, 3, 6, 2, 0.3, 0.25), false)),
            new TwoLayerFeature(1, 0, 1)
    )
            .setMaxWaterDepth(2)
            .setDecorators(ImmutableList.of(
                    new TreeRootsDecorator(3, 1, 12, new SimpleBlockStateProvider(BlockConstants.AIRWOOD_LOG), (new WeightedBlockStateProvider())
                            .addWeightedBlockstate(BlockConstants.AIRWOOD_LOG, 4)),
                    LeaveVineTreeDecorator.field_236871_b_
                    )
            ).build();


}
