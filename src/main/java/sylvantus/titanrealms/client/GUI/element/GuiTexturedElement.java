package sylvantus.titanrealms.client.GUI.element;

import java.util.List;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;

public abstract class GuiTexturedElement extends GuiRelativeElement {

    protected final ResourceLocation resource;

    public GuiTexturedElement(ResourceLocation resource, IGuiWrapper gui, int x, int y, int width, int height) {
        super(gui, x, y, width, height);
        this.resource = resource;
    }

    protected ResourceLocation getResource() {
        return resource;
    }

    public interface IInfoHandler {

        List<ITextComponent> getInfo();
    }
}