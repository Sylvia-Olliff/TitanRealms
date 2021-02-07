package sylvantus.titanrealms.common.containers.slot;

import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;

public enum SlotOverlay {
    MINUS("overlay_minus.png", 18, 18),
    PLUS("overlay_plus.png", 18, 18),
    POWER("overlay_power.png", 18, 18),
    INPUT("overlay_input.png", 18, 18),
    OUTPUT("overlay_output.png", 18, 18),
    CHECK("overlay_check.png", 18, 18),
    X("overlay_x.png", 18, 18),
    FORMULA("overlay_formula.png", 18, 18),
    UPGRADE("overlay_upgrade.png", 18, 18),
    SELECT("overlay_select.png", 18, 18);

    private final ResourceLocation texture;
    private final int width;
    private final int height;

    SlotOverlay(String texture, int width, int height) {
        this.texture = TitanRealmsUtils.getResource(ResourceType.GUI_SLOT, texture);
        this.width = width;
        this.height = height;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}