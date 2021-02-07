package sylvantus.titanrealms.client.GUI.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.inventory.container.Container;
import org.lwjgl.glfw.GLFW;
import sylvantus.titanrealms.client.GUI.GuiTitanRealms;
import sylvantus.titanrealms.client.GUI.element.button.GuiCloseButton;
import sylvantus.titanrealms.core.util.GuiUtils;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.client.render.TitanRealmsRenderer;
import sylvantus.titanrealms.core.lib.Color;
import sylvantus.titanrealms.core.util.interfaces.containers.IEmptyContainer;

import java.util.function.Supplier;

public class GuiWindow extends GuiTexturedElement {

    private static final Color OVERLAY_COLOR = Color.rgbai(60, 60, 60, 128);

    private Runnable closeListener;
    private Runnable reattachListener;

    private boolean dragging = false;
    private double dragX, dragY;
    private int prevDX, prevDY;

    protected InteractionStrategy interactionStrategy = InteractionStrategy.CONTAINER;

    public GuiWindow(IGuiWrapper gui, int x, int y, int width, int height) {
        super(GuiTitanRealms.BASE_BACKGROUND, gui, x, y, width, height);
        isOverlay = true;
        active = true;
        if (!isFocusOverlay()) {
            addCloseButton();
        }
    }

    protected void addCloseButton() {
        addChild(new GuiCloseButton(getGuiObj(), this.x + 6, this.y + 6, this));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean ret = super.mouseClicked(mouseX, mouseY, button);
        // drag 'safe area'
        if (isMouseOver(mouseX, mouseY)) {
            if (mouseY < y + 18) {
                dragging = true;
                dragX = mouseX;
                dragY = mouseY;
                prevDX = 0;
                prevDY = 0;
            }
        } else if (!ret && interactionStrategy.allowContainer()) {
            if (guiObj instanceof GuiTitanRealms) {
                Container c = ((GuiTitanRealms<?>) guiObj).getContainer();
                if (!(c instanceof IEmptyContainer)) {
                    // allow interaction with slots
                    if (mouseX >= guiObj.getLeft() && mouseX < guiObj.getLeft() + guiObj.getWidth() && mouseY >= guiObj.getTop() + guiObj.getHeight() - 90) {
                        return false;
                    }
                }
            }
        }
        // always return true to prevent background clicking
        return ret || !interactionStrategy.allowAll();
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double mouseXOld, double mouseYOld) {
        super.onDrag(mouseX, mouseY, mouseXOld, mouseYOld);
        if (dragging) {
            int newDX = (int) Math.round(mouseX - dragX), newDY = (int) Math.round(mouseY - dragY);
            int changeX = Math.max(-x, Math.min(minecraft.getMainWindow().getScaledWidth() - (x + width), newDX - prevDX));
            int changeY = Math.max(-y, Math.min(minecraft.getMainWindow().getScaledHeight() - (y + height), newDY - prevDY));
            prevDX = newDX;
            prevDY = newDY;
            move(changeX, changeY);
        }
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.onRelease(mouseX, mouseY);
        dragging = false;
    }

    @Override
    public void renderBackgroundOverlay(MatrixStack matrix, int mouseX, int mouseY) {
        if (isFocusOverlay()) {
            TitanRealmsRenderer.renderColorOverlay(matrix, 0, 0, minecraft.getMainWindow().getScaledWidth(), minecraft.getMainWindow().getScaledHeight(), OVERLAY_COLOR.rgba());
        } else {
            RenderSystem.color4f(1, 1, 1, 0.75F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            GuiUtils.renderBackgroundTexture(matrix, GuiTitanRealms.SHADOW, 4, 4, getButtonX() - 3, getButtonY() - 3, getButtonWidth() + 6, getButtonHeight() + 6, 256, 256);
            TitanRealmsRenderer.resetColor();
        }
        minecraft.textureManager.bindTexture(getResource());
        renderBackgroundTexture(matrix, getResource(), 4, 4);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }
        return false;
    }

    public void setListenerTab(Supplier<? extends GuiElement> elementSupplier) {
        closeListener = () -> elementSupplier.get().active = true;
        reattachListener = () -> elementSupplier.get().active = false;
    }

    @Override
    public void resize(int prevLeft, int prevTop, int left, int top) {
        super.resize(prevLeft, prevTop, left, top);
        if (reattachListener != null) {
            reattachListener.run();
        }
    }

    public void renderBlur(MatrixStack matrix) {
        RenderSystem.color4f(1, 1, 1, 0.3F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GuiUtils.renderBackgroundTexture(matrix, GuiTitanRealms.BLUR, 4, 4, relativeX, relativeY, width, height, 256, 256);
        TitanRealmsRenderer.resetColor();
    }

    public void close() {
        children.forEach(GuiElement::onWindowClose);
        guiObj.removeWindow(this);
        if (guiObj instanceof GuiTitanRealms) {
            ((GuiTitanRealms<?>) guiObj).setListener(null);
        }
        if (closeListener != null) {
            closeListener.run();
        }
    }

    protected boolean isFocusOverlay() {
        return false;
    }

    public enum InteractionStrategy {
        NONE,
        CONTAINER,
        ALL;

        boolean allowContainer() {
            return this != NONE;
        }

        boolean allowAll() {
            return this == ALL;
        }
    }
}
