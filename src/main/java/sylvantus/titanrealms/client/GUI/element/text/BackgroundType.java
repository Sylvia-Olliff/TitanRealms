package sylvantus.titanrealms.client.GUI.element.text;

import com.mojang.blaze3d.matrix.MatrixStack;
import sylvantus.titanrealms.client.GUI.element.GuiElementHolder;
import sylvantus.titanrealms.client.GUI.element.GuiInnerScreen;
import sylvantus.titanrealms.core.util.GuiUtils;

import java.util.function.BiConsumer;

public enum BackgroundType {
    INNER_SCREEN((field, matrix) -> GuiUtils.renderBackgroundTexture(matrix, GuiInnerScreen.SCREEN, 32, 32, field.x - 1, field.y - 1, field.getWidth() + 2, field.getHeightRealms() + 2, 256, 256)),
    ELEMENT_HOLDER((field, matrix) -> GuiUtils.renderBackgroundTexture(matrix, GuiElementHolder.HOLDER, 2, 2, field.x - 1, field.y - 1, field.getWidth() + 2, field.getHeightRealms() + 2, 256, 256)),
    DEFAULT((field, matrix) -> {
        GuiUtils.fill(matrix, field.x - 1, field.y - 1, field.getWidth() + 2, field.getHeightRealms() + 2, GuiTextField.DEFAULT_BORDER_COLOR);
        GuiUtils.fill(matrix, field.x, field.y, field.getWidth(), field.getHeightRealms(), GuiTextField.DEFAULT_BACKGROUND_COLOR);
    }),
    DIGITAL((field, matrix) -> {
        GuiUtils.fill(matrix, field.x - 1, field.y - 1, field.getWidth() + 2, field.getHeightRealms() + 2, field.isTextFieldFocused() ? GuiTextField.SCREEN_COLOR.getAsInt() : GuiTextField.DARK_SCREEN_COLOR.getAsInt());
        GuiUtils.fill(matrix, field.x, field.y, field.getWidth(), field.getHeightRealms(), GuiTextField.DEFAULT_BACKGROUND_COLOR);
    }),
    NONE((field, matrix) -> {
    });

    private final BiConsumer<GuiTextField, MatrixStack> renderFunction;

    BackgroundType(BiConsumer<GuiTextField, MatrixStack> renderFunction) {
        this.renderFunction = renderFunction;
    }

    public void render(GuiTextField field, MatrixStack matrix) {
        renderFunction.accept(field, matrix);
    }
}