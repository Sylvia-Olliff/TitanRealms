package sylvantus.titanrealms.client.GUI.element;


import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;

public class GuiUpArrow extends GuiTextureOnlyElement {

    private static final ResourceLocation ARROW = TitanRealmsUtils.getResource(ResourceType.GUI, "up_arrow.png");

    public GuiUpArrow(IGuiWrapper gui, int x, int y) {
        super(ARROW, gui, x, y, 8, 10);
    }
}