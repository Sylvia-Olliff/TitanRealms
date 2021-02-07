package sylvantus.titanrealms.datagen.client.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;

public abstract class ConvertableLanguageProvider extends LanguageProvider {

    public ConvertableLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    public abstract void convert(String key, List<FormatSplitter.Component> splitEnglish);

    @Override
    protected void addTranslations() {}
}
