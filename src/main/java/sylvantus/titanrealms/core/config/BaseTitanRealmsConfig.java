package sylvantus.titanrealms.core.config;

import sylvantus.titanrealms.core.config.value.CachedPrimitiveValue;
import sylvantus.titanrealms.core.config.value.CachedResolvableConfigValue;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTitanRealmsConfig implements ITitanRealmsConfig {

    private final List<CachedResolvableConfigValue<?, ?>> cachedConfigValues = new ArrayList<>();
    private final List<CachedPrimitiveValue<?>> cachedPrimitiveValues = new ArrayList<>();

    @Override
    public void clearCache() {
        cachedConfigValues.forEach(CachedResolvableConfigValue::clearCache);
        cachedPrimitiveValues.forEach(CachedPrimitiveValue::clearCache);
    }

    @Override
    public <T, R> void addCachedValue(CachedResolvableConfigValue<T, R> configValue) {
        cachedConfigValues.add(configValue);
    }

    @Override
    public <T> void addCachedValue(CachedPrimitiveValue<T> configValue) {
        cachedPrimitiveValues.add(configValue);
    }
}
