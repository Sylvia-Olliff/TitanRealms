package sylvantus.titanrealms.core.config;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class TitanRealmsConfig {

    private TitanRealmsConfig() {}

    public static final GeneralConfig general = new GeneralConfig();
    public static final ClientConfig client = new ClientConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        TitanRealmsConfigHelper.registerConfig(modContainer, general);
        TitanRealmsConfigHelper.registerConfig(modContainer, client);
    }
}
