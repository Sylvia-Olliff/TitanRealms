package sylvantus.titanrealms.core.registration;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Function;
import java.util.function.Supplier;

public class WrappedDeferredRegister<T extends IForgeRegistryEntry<T>> {

    protected final DeferredRegister<T> internal;

    protected WrappedDeferredRegister(String modid, IForgeRegistry<T> registry) {
        internal = DeferredRegister.create(registry, modid);
    }

    /**
     * For use with custom registries (Just in case)
     */
    protected WrappedDeferredRegister(String modid, Class<T> base) {
        internal = DeferredRegister.create(base, modid);
    }

    protected <I extends T, W extends  WrappedRegistryObject<I>> W register(String name, Supplier<? extends I> supplier, Function<RegistryObject<I>, W> objectWrapper) {
        return objectWrapper.apply(internal.register(name, supplier));
    }

    public void createAndRegister(IEventBus bus, String name) {
        internal.makeRegistry(name, RegistryBuilder::new);
        register(bus);
    }

    public void createAndRegisterWithTags(IEventBus bus, String name, String tagFolder) {
        internal.makeRegistry(name, () -> new RegistryBuilder<T>().tagFolder(tagFolder));
        register(bus);
    }

    public void register(IEventBus bus) {
        internal.register(bus);
    }
}
