package sylvantus.titanrealms.client.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import mekanism.api.providers.IBaseProvider;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.client.GUI.element.GuiTexturedElement;

import javax.annotation.Nullable;
import java.util.Set;

public abstract class BaseRecipeCategory<RECIPE> implements IRecipeCategory<RECIPE>, IGuiWrapper {

    protected final ITickTimer timer;
    protected final int xOffset;
    protected final int yOffset;
    protected final Set<GuiTexturedElement> guiElements = new ObjectOpenHashSet<>();
    private final ResourceLocation id;
    private final ITextComponent component;
    private final IDrawable background;

    @Nullable
    protected IDrawable icon;

    protected BaseRecipeCategory(IGuiHelper helper, IBaseProvider provider, int xOffset, int yOffset, int width, int height) {
        this(helper, provider.getRegistryName(), provider.getTextComponent(), xOffset, yOffset, width, height);
    }

    protected BaseRecipeCategory(IGuiHelper helper, ResourceLocation id, ITextComponent component, int xOffset, int yOffset, int width, int height) {
        this.id = id;
        this.component = component;
        //TODO: Only make a timer for ones we need it
        this.timer = helper.createTickTimer(20, 20, false);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.background = new NOOPDrawable(width, height);
        addGuiElements();
    }

//    private IDrawable createDrawable(IGuiHelper helper, GaugeOverlay gaugeOverlay) {
//        return helper.drawableBuilder(gaugeOverlay.getBarOverlay(), 0, 0, gaugeOverlay.getWidth(), gaugeOverlay.getHeight())
//                .setTextureSize(gaugeOverlay.getWidth(), gaugeOverlay.getHeight())
//                .build();
//    }

    @Override
    public int getLeft() {
        return -xOffset;
    }

    @Override
    public int getTop() {
        return -yOffset;
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public ResourceLocation getUid() {
        return id;
    }

    @Override
    public String getTitle() {
        return component.getString();
    }

    @Override
    public void draw(RECIPE recipe, MatrixStack matrix, double mouseX, double mouseY) {
        guiElements.forEach(e -> e.render(matrix, (int) mouseX, (int) mouseY, 0));
        guiElements.forEach(e -> e.drawBackground(matrix, (int) mouseX, (int) mouseY, 0));
    }

    @Override
    public ItemRenderer getItemRenderer() {
        return Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public FontRenderer getFont() {
        return Minecraft.getInstance().fontRenderer;
    }

    protected void addGuiElements() {
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        //Note: Even though we usually return null form here, this is allowed even though annotations imply it isn't supposed to be
        return icon;
    }
}
