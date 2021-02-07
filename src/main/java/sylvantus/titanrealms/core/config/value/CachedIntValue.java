package sylvantus.titanrealms.core.config.value;

import java.util.function.IntSupplier;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

public class CachedIntValue extends CachedPrimitiveValue<Integer> implements IntSupplier {

    private int cachedValue;

    private CachedIntValue(ITitanRealmsConfig config, ConfigValue<Integer> internal) {
        super(config, internal);
    }

    public static CachedIntValue wrap(ITitanRealmsConfig config, ConfigValue<Integer> internal) {
        return new CachedIntValue(config, internal);
    }

    public int get() {
        if (!resolved) {
            //If we don't have a cached value or need to resolve it again, get it from the actual ConfigValue
            cachedValue = internal.get();
            resolved = true;
        }
        return cachedValue;
    }

    @Override
    public int getAsInt() {
        return get();
    }

    public void set(int value) {
        internal.set(value);
        cachedValue = value;
    }
}