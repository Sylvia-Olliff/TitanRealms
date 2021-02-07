package sylvantus.titanrealms.client.GUI.element.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import mekanism.api.text.EnumColor;
import net.minecraft.util.text.StringTextComponent;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.client.render.TitanRealmsRenderer;
import sylvantus.titanrealms.core.TitanRealmsLang;

public class ColorButton extends TitanRealmsButton {

    private final Supplier<EnumColor> colorSupplier;

    public ColorButton(IGuiWrapper gui, int x, int y, int width, int height, Supplier<EnumColor> colorSupplier, Runnable onPress, Runnable onRightClick) {
        super(gui, x, y, width, height, StringTextComponent.EMPTY, onPress, onRightClick, (onHover, matrix, xAxis, yAxis) -> {
            EnumColor color = colorSupplier.get();
            if (color != null) {
                gui.displayTooltip(matrix, color.getColoredName(), xAxis, yAxis);
            } else {
                gui.displayTooltip(matrix, TitanRealmsLang.NONE.translate(), xAxis, yAxis);
            }
        });
        this.colorSupplier = colorSupplier;
    }

    @Override
    public void drawBackground(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        //Ensure the color gets reset. The default GuiButtonImage doesn't so other GuiButton's can have the color leak out of them
        EnumColor color = colorSupplier.get();
        if (color != null) {
            fill(matrix, this.x, this.y, this.x + this.width, this.y + this.height, TitanRealmsRenderer.getColorARGB(color, 1));
            TitanRealmsRenderer.resetColor();
        }
    }
}