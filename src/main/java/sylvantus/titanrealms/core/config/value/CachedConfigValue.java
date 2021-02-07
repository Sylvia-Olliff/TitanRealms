package sylvantus.titanrealms.core.config.value;


import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

public class CachedConfigValue<T> extends CachedResolvableConfigValue<T, T> {

    protected CachedConfigValue(ITitanRealmsConfig config, ConfigValue<T> internal) {
        super(config, internal);
    }

    public static <T> CachedConfigValue<T> wrap(ITitanRealmsConfig config, ConfigValue<T> internal) {
        return new CachedConfigValue<>(config, internal);
    }

    @Override
    protected T resolve(T encoded) {
        return encoded;
    }

    @Override
    protected T encode(T value) {
        return value;
    }
}