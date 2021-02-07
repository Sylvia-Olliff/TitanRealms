package sylvantus.titanrealms.client.GUI.element.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import mekanism.api.text.EnumColor;
import mekanism.api.text.ILangEntry;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.client.render.TitanRealmsRenderer;

public class TranslationButton extends TitanRealmsButton {

    private final Supplier<EnumColor> colorSupplier;

    public TranslationButton(IGuiWrapper gui, int x, int y, int width, int height, ILangEntry translationHelper, Runnable onPress) {
        this(gui, x, y, width, height, translationHelper, onPress, null, null);
    }

    public TranslationButton(IGuiWrapper gui, int x, int y, int width, int height, ILangEntry translationHelper, Runnable onPress, IHoverable onHover,
          Supplier<EnumColor> colorSupplier) {
        super(gui, x, y, width, height, translationHelper.translate(), onPress, onHover);
        this.colorSupplier = colorSupplier;
    }

    @Override
    public void drawBackground(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (colorSupplier == null) {
            TitanRealmsRenderer.resetColor();
            super.drawBackground(matrix, mouseX, mouseY, partialTicks);
            return;
        }

        TitanRealmsRenderer.color(colorSupplier.get());
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
        TitanRealmsRenderer.resetColor();
    }

    @Override
    protected boolean resetColorBeforeRender() {
        return false;
    }
}