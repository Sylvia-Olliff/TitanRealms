package sylvantus.titanrealms.core.world.dimension;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sylvantus.titanrealms.core.world.SeedWrapper;

import java.util.*;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public class AesirBiomeProvider extends BiomeProvider {

    public static final Codec<AesirBiomeProvider> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.LONG.fieldOf("seed")
                        .orElseGet(SeedWrapper::getSeed)
                        .forGetter(aesirProvider -> aesirProvider.seed),
                    RecordCodecBuilder.<Pair<Biome.Attributes, Supplier<Biome>>>create(
                        biomeAttributes -> biomeAttributes.group(
                                Biome.Attributes.CODEC.fieldOf("parameters")
                                    .forGetter(Pair::getFirst),
                                Biome.BIOME_CODEC.fieldOf("biome")
                                    .forGetter(Pair::getSecond))
                                .apply(biomeAttributes, Pair::of))
                        .listOf().fieldOf("biomes")
                        .forGetter(aesirProvider -> aesirProvider.biomeAttributes))
                    .apply(instance, AesirBiomeProvider::new));

    private final SimplexNoiseGenerator generator;
    public final List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes;
    public long seed;

    public AesirBiomeProvider(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes) {
        super(biomeAttributes.stream().map(Pair::getSecond));
        this.biomeAttributes = biomeAttributes;
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
        sharedseedrandom.skip(17292);
        this.generator = new SimplexNoiseGenerator(sharedseedrandom);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new AesirBiomeProvider(seed, this.biomeAttributes);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        int i = x >> 2;
        int j = z >> 2;
        Biome.Attributes selectorAttributes = new Biome.Attributes(
                AesirBiomeProvider.getRandomNoise(this.generator, i * 2 + 1, j * 2 + 1),
                AesirBiomeProvider.getRandomNoise(this.generator, i * 2 + 1, j * 2 + 1),
                AesirBiomeProvider.getRandomNoise(this.generator, i * 2 + 1, j * 2 + 1),
                AesirBiomeProvider.getRandomNoise(this.generator, i * 2 + 1, j * 2 + 1),
                0.0F);

        return this.biomeAttributes.stream().min(Comparator.comparing((attributeBiomePair) ->
            attributeBiomePair.getFirst().getAttributeDifference(selectorAttributes)
        )).map(Pair::getSecond).map(Supplier::get).orElse(BiomeRegistry.THE_VOID);
    }

    private static float getRandomNoise(SimplexNoiseGenerator noiseGenerator, int x, int z) {
        int i = x / 2;
        int j = z / 2;
        int k = x % 2;
        int l = z % 2;
        float f = 100.0F - MathHelper.sqrt((float)(x * x + z * z)) * 8.0F;
        f = MathHelper.clamp(f, -100.0F, 80.0F);

        for(int i1 = -12; i1 <= 12; ++i1) {
            for(int j1 = -12; j1 <= 12; ++j1) {
                long k1 = (long)(i + i1);
                long l1 = (long)(j + j1);
                if (k1 * k1 + l1 * l1 > 4096L && noiseGenerator.getValue((double)k1, (double)l1) < (double)-0.9F) {
                    float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
                    float f2 = (float)(k - i1 * 2);
                    float f3 = (float)(l - j1 * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
                    f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
                    f = Math.max(f, f4);
                }
            }
        }

        return f;
    }
}
