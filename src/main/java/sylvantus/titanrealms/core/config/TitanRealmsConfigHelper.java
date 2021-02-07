package sylvantus.titanrealms.core.config;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLPaths;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

import java.nio.file.Path;

public class TitanRealmsConfigHelper {

    private TitanRealmsConfigHelper() {}

    public static final Path CONFIG_DIR;

    static {
        CONFIG_DIR = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(TitanRealms.MOD_NAME), TitanRealms.MOD_NAME);
    }

    /**
     * Creates a mod config so that {@link net.minecraftforge.fml.config.ConfigTracker} will track it and sync server configs from server to client.
     */
    public static void registerConfig(ModContainer modContainer, ITitanRealmsConfig config) {
        TitanRealmsModConfig modConfig = new TitanRealmsModConfig(modContainer, config);
        if (config.addToContainer()) {
            modContainer.addConfig(modConfig);
        }
    }
}
