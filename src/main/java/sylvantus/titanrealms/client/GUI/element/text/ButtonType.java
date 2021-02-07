package sylvantus.titanrealms.client.GUI.element.text;

import sylvantus.titanrealms.client.GUI.element.GuiElement.ButtonBackground;
import sylvantus.titanrealms.client.GUI.element.button.TitanRealmsImageButton;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;

import java.util.function.BiFunction;

public enum ButtonType {
    NORMAL((field, callback) -> new TitanRealmsImageButton(field.getGuiObj(), field.getGuiObj().getLeft() + field.getRelativeX() + field.getWidth() - field.getHeightRealms(),
          field.getGuiObj().getTop() + field.getRelativeY(), field.getHeightRealms(), 12,
          TitanRealmsUtils.getResource(ResourceType.GUI_BUTTON, "checkmark.png"), callback)),
    DIGITAL((field, callback) -> {
        TitanRealmsImageButton ret = new TitanRealmsImageButton(field.getGuiObj(), field.getGuiObj().getLeft() + field.getRelativeX() + field.getWidth() - field.getHeightRealms(),
              field.getGuiObj().getTop() + field.getRelativeY(), field.getHeightRealms(), 12,
              TitanRealmsUtils.getResource(ResourceType.GUI_BUTTON, "checkmark_digital.png"), callback);
        ret.setButtonBackground(ButtonBackground.DIGITAL);
        return ret;
    });

    private final BiFunction<GuiTextField, Runnable, TitanRealmsImageButton> buttonCreator;

    ButtonType(BiFunction<GuiTextField, Runnable, TitanRealmsImageButton> buttonCreator) {
        this.buttonCreator = buttonCreator;
    }

    public TitanRealmsImageButton getButton(GuiTextField field, Runnable callback) {
        return buttonCreator.apply(field, callback);
    }
}