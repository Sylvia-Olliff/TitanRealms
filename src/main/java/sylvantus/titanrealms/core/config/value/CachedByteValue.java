package sylvantus.titanrealms.core.config.value;

import mekanism.api.functions.ByteSupplier;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

public class CachedByteValue extends CachedPrimitiveValue<Byte> implements ByteSupplier {

    private byte cachedValue;

    private CachedByteValue(ITitanRealmsConfig config, ConfigValue<Byte> internal) {
        super(config, internal);
    }

    public static CachedByteValue wrap(ITitanRealmsConfig config, ConfigValue<Byte> internal) {
        return new CachedByteValue(config, internal);
    }

    public byte get() {
        if (!resolved) {
            //If we don't have a cached value or need to resolve it again, get it from the actual ConfigValue
            cachedValue = internal.get();
            resolved = true;
        }
        return cachedValue;
    }

    @Override
    public byte getAsByte() {
        return get();
    }

    public void set(byte value) {
        internal.set(value);
        cachedValue = value;
    }
}