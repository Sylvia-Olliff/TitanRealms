package sylvantus.titanrealms.core.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ConfigFileTypeHandler;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.util.interfaces.ITitanRealmsConfig;

import java.nio.file.Path;
import java.util.function.Function;

public class TitanRealmsModConfig extends ModConfig {
    private static final TitanRealmsConfigFileTypeHandler TR_TOML = new TitanRealmsConfigFileTypeHandler();

    private final ITitanRealmsConfig titanRealmsConfig;

    public TitanRealmsModConfig(ModContainer container, ITitanRealmsConfig config) {
        super(config.getConfigType(), config.getConfigSpec(), container, TitanRealms.MOD_NAME + "/" + config.getFileName() + ".toml");
        this.titanRealmsConfig = config;
    }

    @Override
    public ConfigFileTypeHandler getHandler() { return TR_TOML; }

    public void clearCache() { titanRealmsConfig.clearCache(); }

    private static class TitanRealmsConfigFileTypeHandler extends ConfigFileTypeHandler {

        private static Path getPath(Path configBasePath) {
            if (configBasePath.endsWith("serverconfig")) {
                return FMLPaths.CONFIGDIR.get();
            }
            return configBasePath;
        }

        @Override
        public Function<ModConfig, CommentedFileConfig> reader(Path configBasePath) {
            return super.reader(getPath(configBasePath));
        }

        @Override
        public void unload(Path configBasePath, ModConfig config) {
            super.unload(getPath(configBasePath), config);
        }
    }
}
