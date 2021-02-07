package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.WrappedRegistryObject;

import javax.annotation.Nonnull;

public class BiomeRegistryObject<BIOME extends Biome> extends WrappedRegistryObject<BIOME> {
    public BiomeRegistryObject(RegistryObject<BIOME> registryObject) {
        super(registryObject);
    }

    @Nonnull
    public BIOME getBiome() {
        return get();
    }
}
