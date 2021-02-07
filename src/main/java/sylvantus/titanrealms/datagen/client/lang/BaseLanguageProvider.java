package sylvantus.titanrealms.datagen.client.lang;

import mekanism.api.providers.IBlockProvider;
import mekanism.api.text.IHasTranslationKey;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.util.Util;
import net.minecraftforge.common.data.LanguageProvider;
import sylvantus.titanrealms.common.blocks.attributes.AttributeGui;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;
import sylvantus.titanrealms.datagen.client.lang.FormatSplitter.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public abstract class BaseLanguageProvider extends LanguageProvider {
    private final ConvertableLanguageProvider[] altProviders;
    private final String modid;

    public BaseLanguageProvider(DataGenerator gen, String modid) {
        super(gen, modid, "en_us");
        this.modid = modid;
        this.altProviders = new ConvertableLanguageProvider[]{
                new UpsideDownLanguageProvider(gen, modid)
        };
    }

    @Nonnull
    @Override
    public String getName() {
        return super.getName() + ": " + modid;
    }

    protected void add(IHasTranslationKey key, String value) {
        if (key instanceof IBlockProvider) {
            Block block = ((IBlockProvider) key).getBlock();
            if (IAttribute.has(block, AttributeGui.class)) {
                add(Util.makeTranslationKey("container", block.getRegistryName()), value);
            }
        }
        add(key.getTranslationKey(), value);
    }

    @Override
    public void add(String key, String value) {
        super.add(key, value);
        if (altProviders.length > 0) {
            List<Component> splitEnglish = FormatSplitter.split(value);
            for (ConvertableLanguageProvider provider : altProviders) {
                provider.convert(key, splitEnglish);
            }
        }
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        super.act(cache);
        if (altProviders.length > 0) {
            for (ConvertableLanguageProvider provider : altProviders) {
                provider.act(cache);
            }
        }
    }
}
