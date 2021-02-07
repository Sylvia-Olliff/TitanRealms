package sylvantus.titanrealms.client.GUI.element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.core.util.interfaces.client.IGuiWrapper;
import sylvantus.titanrealms.client.jei.interfaces.IJEIRecipeArea;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;

public class GuiRightArrow extends GuiTextureOnlyElement implements IJEIRecipeArea<GuiRightArrow> {

    private static final ResourceLocation ARROW = TitanRealmsUtils.getResource(ResourceType.GUI, "right_arrow.png");

    private ResourceLocation[] recipeCategories;

    public GuiRightArrow(IGuiWrapper gui, int x, int y) {
        super(ARROW, gui, x, y, 22, 15);
    }

    @Nonnull
    @Override
    public GuiRightArrow jeiCategories(@Nullable ResourceLocation... recipeCategories) {
        this.recipeCategories = recipeCategories;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation[] getRecipeCategories() {
        return recipeCategories;
    }
}