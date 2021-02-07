package sylvantus.titanrealms.client.jei.interfaces;

import javax.annotation.Nullable;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.client.GUI.element.GuiElement;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;

public interface IJEIRecipeArea<ELEMENT extends GuiElement> extends IGuiEventListener {

    /**
     * @return null if not an active recipe area, otherwise the category
     */
    @Nullable
    ResourceLocation[] getRecipeCategories();

    default boolean isActive() {
        return true;
    }

    ELEMENT jeiCategories(@Nullable ResourceLocation... recipeCategories);

    default ELEMENT jeiCategory(TileEntityTitanRealms tile) {
        return jeiCategories(tile.getBlockType().getRegistryName());
    }

    default ELEMENT jeiCrafting() {
        if (TitanRealms.hooks.JEILoaded) {
            return jeiCategories(VanillaRecipeCategoryUid.CRAFTING);
        }
        return jeiCategories((ResourceLocation) null);
    }

    default boolean isMouseOverJEIArea(double mouseX, double mouseY) {
        return isMouseOver(mouseX, mouseY);
    }
}