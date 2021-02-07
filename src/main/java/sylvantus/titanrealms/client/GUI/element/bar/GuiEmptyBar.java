package sylvantus.titanrealms.client.GUI.element.bar;

import com.mojang.blaze3d.matrix.MatrixStack;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.client.GUI.element.bar.GuiBar.IBarInfoHandler;

public class GuiEmptyBar extends GuiBar<IBarInfoHandler> {

    private static final IBarInfoHandler EMPTY_INFO = () -> 0;

    public GuiEmptyBar(IGuiWrapper gui, int x, int y, int width, int height) {
        super(null, gui, EMPTY_INFO, x, y, width, height);
        //Pass null as we technically don't need a resource given our handler always gives a zero size
    }

    @Override
    protected void renderBarOverlay(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
    }
}