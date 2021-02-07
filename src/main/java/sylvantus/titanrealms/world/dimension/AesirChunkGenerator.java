package sylvantus.titanrealms.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.world.SeedWrapper;

import java.util.function.Supplier;

public class AesirChunkGenerator extends NoiseChunkGenerator {

    public static final Codec<AesirChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source")
                        .forGetter(chunkGenerator -> chunkGenerator.biomeProvider),
                    Codec.LONG.fieldOf("seed")
                        .orElseGet(SeedWrapper::getSeed)
                        .forGetter(chunkGenerator -> chunkGenerator.field_236084_w_),
                    DimensionSettings.field_236098_b_.fieldOf("settings")
                        .forGetter(chunkGenerator -> chunkGenerator.field_236080_h_))
            .apply(instance, instance.stable(AesirChunkGenerator::new)));

    public AesirChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> dimensionSettingsSupplier) {
        super(biomeProvider, seed, dimensionSettingsSupplier);
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ChunkGenerator func_230349_a_(long seed) {
        return new AesirChunkGenerator(this.biomeProvider.getBiomeProvider(seed), seed, this.field_236080_h_);
    }
}
