package sylvantus.titanrealms.core.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.worldgen.treeplacers.*;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TitanRealms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class TitanRealmsFeatures {
    private static final List<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = new ArrayList<>();
    private static final List<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = new ArrayList<>();

    public static final TrunkPlacerType<BranchingTrunkPlacer> TRUNK_BRANCHING = registerTrunk(TitanRealms.rl("branching_trunk_placer"), BranchingTrunkPlacer.CODEC);
    public static final TrunkPlacerType<TrunkRiser> TRUNK_RISER = registerTrunk(TitanRealms.rl("trunk_riser"), TrunkRiser.CODEC);

    public static final TreeDecoratorType<TreeCorePlacer> CORE_PLACER = registerTreeFeature(TitanRealms.rl("core_placer"), TreeCorePlacer.CODEC);
    public static final TreeDecoratorType<TreeRootsDecorator> TREE_ROOTS = registerTreeFeature(TitanRealms.rl("tree_roots"), TreeRootsDecorator.CODEC);
    public static final TreeDecoratorType<TrunkSideDecorator> TRUNKSIDE_DECORATOR = registerTreeFeature(TitanRealms.rl("trunkside_decorator"), TrunkSideDecorator.CODEC);

    private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(ResourceLocation name, Codec<P> codec) {
        FoliagePlacerType<P> type = new FoliagePlacerType<>(codec);
        type.setRegistryName(name);
        FOLIAGE_PLACER_TYPES.add(type);
        return type;
    }

    private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerTrunk(ResourceLocation name, Codec<P> codec) {
        // TRUNK_REPLACER is wrong, it only places, not replacing
        return Registry.register(Registry.TRUNK_REPLACER, name, new TrunkPlacerType<>(codec));
    }

    private static <P extends TreeDecorator> TreeDecoratorType<P> registerTreeFeature(ResourceLocation name, Codec<P> codec) {
        // TRUNK_REPLACER is wrong, it only places, not replacing
        TreeDecoratorType<P> type = new TreeDecoratorType<>(codec);
        type.setRegistryName(name);
        TREE_DECORATOR_TYPES.add(type);
        return type;
    }

    protected static <FC extends IFeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> registerWorldFeature(ResourceLocation rl, ConfiguredFeature<FC, F> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, rl, feature);
    }

    @SubscribeEvent
    public static void registerFoliagePlacers(RegistryEvent.Register<FoliagePlacerType<?>> evt) {
        evt.getRegistry().registerAll(FOLIAGE_PLACER_TYPES.toArray(new FoliagePlacerType<?>[0]));
    }

    @SubscribeEvent
    public static void registerTreeDecorators(RegistryEvent.Register<TreeDecoratorType<?>> evt) {
        evt.getRegistry().registerAll(TREE_DECORATOR_TYPES.toArray(new TreeDecoratorType<?>[0]));
    }
}
