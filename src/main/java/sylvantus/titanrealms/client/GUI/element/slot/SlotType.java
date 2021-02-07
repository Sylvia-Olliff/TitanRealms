package sylvantus.titanrealms.client.GUI.element.slot;

import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.core.config.tile.components.DataType;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;

public enum SlotType {
    NORMAL("normal.png", 18, 18),
    DIGITAL("digital.png", 18, 18),
    POWER("power.png", 18, 18),
    EXTRA("extra.png", 18, 18),
    INPUT("input.png", 18, 18),
    INPUT_2("input_2.png", 18, 18),
    OUTPUT("output.png", 18, 18),
    OUTPUT_2("output_2.png", 18, 18),
    OUTPUT_WIDE("output_wide.png", 42, 26),
    OUTPUT_LARGE("output_large.png", 36, 54),
    ORE("ore.png", 18, 18),
    INNER_HOLDER_SLOT("inner_holder_slot.png", 18, 18);

    private final ResourceLocation texture;
    private final int width;
    private final int height;

    SlotType(String texture, int width, int height) {
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

    public static SlotType get(DataType type) {
        switch (type) {
            // TODO: Add new GUI data types here for slots
            default:
                return NORMAL;
        }
    }
}