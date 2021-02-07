package sylvantus.titanrealms.core.registration.impl;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import sylvantus.titanrealms.core.registration.WrappedDeferredRegister;

import java.util.function.Supplier;

public class BiomeDeferredRegister extends WrappedDeferredRegister<Biome> {

    public BiomeDeferredRegister(String modid) {
        super(modid, ForgeRegistries.BIOMES);
    }

    public <BIOME extends Biome> BiomeRegistryObject<BIOME> register(String name, Supplier<BIOME> supplier) {
        return register(name, supplier, BiomeRegistryObject::new);
    }
}
