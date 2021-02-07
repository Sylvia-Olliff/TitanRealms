package sylvantus.titanrealms.client.GUI.element.text;

import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;

// TODO: Digital doesn't really apply but this layout should be useful for something else
public enum IconType {
    DIGITAL(TitanRealmsUtils.getResource(ResourceType.GUI, "digital_text_input.png"), 4, 7);

    private final ResourceLocation icon;
    private final int xSize, ySize;

    IconType(ResourceLocation icon, int xSize, int ySize) {
        this.icon = icon;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public int getWidth() {
        return xSize;
    }

    public int getHeight() {
        return ySize;
    }

    public int getOffsetX() {
        return xSize + 4;
    }
}