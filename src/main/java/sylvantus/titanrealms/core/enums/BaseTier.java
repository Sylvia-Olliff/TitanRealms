package sylvantus.titanrealms.core.enums;

import mekanism.api.math.MathUtils;
import mekanism.api.text.EnumColor;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum BaseTier implements IStringSerializable {
    COMMON("Common", EnumColor.GRAY, EnumColor.GRAY),
    RARE("Rare", EnumColor.AQUA, EnumColor.AQUA),
    LEGENDARY("Legendary", EnumColor.ORANGE, EnumColor.BROWN),
    ARTIFACT("Artifact", EnumColor.PURPLE, EnumColor.INDIGO),
    CREATIVE("Creative", EnumColor.BLACK, EnumColor.DARK_GRAY);

    private static final BaseTier[] TIERS = values();

    private final String name;
    private final EnumColor color;
    private final EnumColor textColor;

    BaseTier(String name, EnumColor base, EnumColor text) {
        this.name = name;
        this.color = base;
        this.textColor = text;
    }

    public String getSimpleName() {
        return name;
    }

    public String getLowerName() {
        return getSimpleName().toLowerCase(Locale.ROOT);
    }

    public EnumColor getColor() {
        return color;
    }

    public EnumColor getTextColor() {
        return textColor;
    }

    @Override
    public String getString() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static BaseTier byIndexStatic(int index) {
        return MathUtils.getByIndexMod(TIERS, index);
    }
}
