package sylvantus.titanrealms.client.jei;

import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.MekanismRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import sylvantus.titanrealms.common.recipes.TitanRealmsRecipeType;

public class RecipeRegistryHelper {

    private RecipeRegistryHelper() {}

    public static <RECIPE extends MekanismRecipe> void register(IRecipeRegistration registry, IBlockProvider titanRealmsBlock, TitanRealmsRecipeType<RECIPE> type) {

    }
}
