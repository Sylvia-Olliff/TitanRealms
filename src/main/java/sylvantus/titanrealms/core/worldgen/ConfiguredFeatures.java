package sylvantus.titanrealms.core.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import sylvantus.titanrealms.TitanRealms;

import java.util.List;
import java.util.stream.Collectors;

public final class ConfiguredFeatures {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ? extends Feature<?>> AIRWOOD_TREE_BASE = TitanRealmsFeatures.registerWorldFeature(TitanRealms.rl("tree/base/airwood_tree"), Feature.TREE.withConfiguration(TreeConfigurations.AIRWOOD_TREE));

    private static final List<ConfiguredFeature<? extends IFeatureConfig, ? extends Feature<?>>> ALL_AESIR_TREES = ImmutableList.of(AIRWOOD_TREE_BASE);

    public static final ConfiguredFeature<?, ?> DEFAULT_AESIR_TREES = TitanRealmsFeatures.registerWorldFeature(TitanRealms.rl("tree/placeholder_filler_trees"),
            Feature.RANDOM_SELECTOR
                    .withConfiguration(new MultipleRandomFeatureConfig(ALL_AESIR_TREES.stream().map(configuredFeature -> configuredFeature.withChance(1f / (ALL_AESIR_TREES.size() + 0.5f))).collect(Collectors.toList()), AIRWOOD_TREE_BASE)) // TODO: Replace with CloudTree once that exists
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.5f, 1)))
    );

    // TODO Actually figure out how the hell placement logic works
    private static final ConfiguredPlacement<?> DEFAULT_TREE_PLACEMENT = Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(8, 0.4F, 4)).square();

    public static final ConfiguredFeature<?, ?> AIRWOOD_TREES = TitanRealmsFeatures.registerWorldFeature(TitanRealms.rl("tree/airwood_trees"), AIRWOOD_TREE_BASE.withPlacement(DEFAULT_TREE_PLACEMENT));
}
