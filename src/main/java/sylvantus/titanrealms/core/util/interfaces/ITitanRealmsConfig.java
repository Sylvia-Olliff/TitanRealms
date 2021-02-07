package sylvantus.titanrealms.core.util.interfaces;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import sylvantus.titanrealms.core.config.value.CachedPrimitiveValue;
import sylvantus.titanrealms.core.config.value.CachedResolvableConfigValue;

public interface ITitanRealmsConfig {

    String getFileName();

    ForgeConfigSpec getConfigSpec();

    ModConfig.Type getConfigType();

    void clearCache();

    <T, R> void addCachedValue(CachedResolvableConfigValue<T, R> configValue);

    <T> void addCachedValue(CachedPrimitiveValue<T> configValue);

    /**
     * Should this config be added to the mods "config" files. Make this return false to only create the config. This will allow it to be tracked, but not override the
     * value that has already been added to this mod's container. As the list is from config type to mod config.
     */
    default boolean addToContainer() {
        return true;
    }
}