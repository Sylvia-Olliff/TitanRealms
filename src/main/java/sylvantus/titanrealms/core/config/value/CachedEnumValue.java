package sylvantus.titanrealms.core.config.value;

import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

public class CachedEnumValue<T extends Enum<T>> extends CachedConfigValue<T> {

    private CachedEnumValue(ITitanRealmsConfig config, EnumValue<T> internal) {
        super(config, internal);
    }

    public static <T extends Enum<T>> CachedEnumValue<T> wrap(ITitanRealmsConfig config, EnumValue<T> internal) {
        return new CachedEnumValue<>(config, internal);
    }
}