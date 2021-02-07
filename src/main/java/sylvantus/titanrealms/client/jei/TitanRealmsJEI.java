package sylvantus.titanrealms.client.jei;

import mekanism.api.providers.IItemProvider;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.client.GUI.GuiTitanRealms;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;
import sylvantus.titanrealms.core.registries.TitanRealmsItems;

import javax.annotation.Nonnull;
import java.util.List;

@JeiPlugin
public class TitanRealmsJEI implements IModPlugin {

    private static String addInterpretation(String nbtRepresentation, String component) {
        if (nbtRepresentation.isEmpty()) {
            return component;
        }
        return nbtRepresentation + ":" + component;
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return TitanRealms.rl("jei_plugin");
    }

    public static void registerItemSubtypes(ISubtypeRegistration registry, List<? extends IItemProvider> itemProviders) {
//        for (IItemProvider itemProvider : itemProviders) {
//            //Handle items
//            ItemStack itemStack = itemProvider.getItemStack();
//            if (itemStack.getCapability(Capabilities.STRICT_ENERGY_CAPABILITY).isPresent() || itemStack.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).isPresent() ||
//                    itemStack.getCapability(Capabilities.INFUSION_HANDLER_CAPABILITY).isPresent() || itemStack.getCapability(Capabilities.PIGMENT_HANDLER_CAPABILITY).isPresent() ||
//                    itemStack.getCapability(Capabilities.SLURRY_HANDLER_CAPABILITY).isPresent() || FluidUtil.getFluidHandler(itemStack).isPresent()) {
//                registry.registerSubtypeInterpreter(itemProvider.getItem(), MEKANISM_NBT_INTERPRETER);
//            }
//        }
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registry) {
        registerItemSubtypes(registry, TitanRealmsItems.ITEMS.getAllItems());
        registerItemSubtypes(registry, TitanRealmsBlocks.BLOCKS.getAllBlocks());
    }

    @Override
    @SuppressWarnings("RedundantTypeArguments")
    public void registerIngredients(IModIngredientRegistration registry) {
        // TODO: Register Item stack ingredients types with JEI
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        // TODO: Register Item and Block recipe categories with JEI
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
        registry.addGenericGuiContainerHandler(GuiTitanRealms.class, new GuiElementHandler());
        registry.addGhostIngredientHandler(GuiTitanRealms.class, new GhostIngredientHandler<>());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        // TODO: Register machine and device recipes (Non-Ingredient) with JEI
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
        // TODO: Register handlers for custom crafting containers with JEI. (Allows ingredient insertion for custom crafting grids)
    }
}
