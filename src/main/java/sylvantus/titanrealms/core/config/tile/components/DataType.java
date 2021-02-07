package sylvantus.titanrealms.core.config.tile.components;

import mekanism.api.IIncrementalEnum;
import mekanism.api.math.MathUtils;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTranslationKey;
import mekanism.api.text.ILangEntry;
import sylvantus.titanrealms.core.TitanRealmsLang;

import javax.annotation.Nonnull;

public enum DataType implements IIncrementalEnum<DataType>, IHasTranslationKey {
    NONE(TitanRealmsLang.SIDE_DATA_NONE, EnumColor.DARK_GRAY);

    private static final DataType[] TYPES = values();
    private final EnumColor color;
    private final ILangEntry langEntry;

    DataType(ILangEntry langEntry, EnumColor color) {
        this.color = color;
        this.langEntry = langEntry;
    }

    public EnumColor getColor() {
        return color;
    }

    @Override
    public String getTranslationKey() {
        return langEntry.getTranslationKey();
    }

    @Nonnull
    @Override
    public DataType byIndex(int index) {
        return byIndexStatic(index);
    }

//    public boolean canOutput() {
//        return this == OUTPUT || this == INPUT_OUTPUT || this == OUTPUT_1 || this == OUTPUT_2;
//    }

    public static DataType byIndexStatic(int index) {
        return MathUtils.getByIndexMod(TYPES, index);
    }
}
