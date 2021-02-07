package sylvantus.titanrealms.common.recipes;

import mekanism.api.inventory.IgnoredIInventory;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.inputs.ItemStackIngredient;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.IForgeRegistry;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.client.TitanRealmsClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TitanRealmsRecipeType<RECIPE_TYPE extends MekanismRecipe> implements IRecipeType<RECIPE_TYPE> {

    private static final List<TitanRealmsRecipeType<? extends MekanismRecipe>> types = new ArrayList<>();

    public static final TitanRealmsRecipeType<ItemStackToItemStackRecipe> SMELTING = create("smelting");

    // TODO: Add recipe types

    private static <RECIPE_TYPE extends MekanismRecipe> TitanRealmsRecipeType<RECIPE_TYPE> create(String name) {
        TitanRealmsRecipeType<RECIPE_TYPE> type = new TitanRealmsRecipeType<>(name);
        types.add(type);
        return type;
    }

    public static void registerRecipeTypes(IForgeRegistry<IRecipeSerializer<?>> registry) {
        types.forEach(type -> Registry.register(Registry.RECIPE_TYPE, type.registryName, type));
    }

    public static void clearCache() {
        //TODO: Does this need to also get cleared on disconnect
        types.forEach(type -> type.cachedRecipes.clear());
    }

    private List<RECIPE_TYPE> cachedRecipes = Collections.emptyList();
    private final ResourceLocation registryName;

    private TitanRealmsRecipeType(String name) { this.registryName = TitanRealms.rl(name); }

    @Nonnull
    public List<RECIPE_TYPE> getRecipes(@Nullable World world) {
        if (world == null) {
            //Try to get a fallback world if we are in a context that may not have one
            //If we are on the client get the client's world, if we are on the server get the current server's world
            world = DistExecutor.safeRunForDist(() -> TitanRealmsClient::tryGetClientWorld, () -> () -> ServerLifecycleHooks.getCurrentServer().func_241755_D_());
            if (world == null) {
                //If we failed, then return no recipes
                return Collections.emptyList();
            }
        }
        if (cachedRecipes.isEmpty()) {
            RecipeManager recipeManager = world.getRecipeManager();
            //TODO: Should we use the getRecipes(RecipeType) that we ATd so that our recipes don't have to always return true for matching?
            List<RECIPE_TYPE> recipes = recipeManager.getRecipes(this, IgnoredIInventory.INSTANCE, world);
            if (this == SMELTING) {
                Map<ResourceLocation, IRecipe<IInventory>> smeltingRecipes = recipeManager.getRecipes(IRecipeType.SMELTING);
                //Copy recipes our recipes to make sure it is mutable
                recipes = new ArrayList<>(recipes);
                for (Map.Entry<ResourceLocation, IRecipe<IInventory>> entry : smeltingRecipes.entrySet()) {
                    IRecipe<IInventory> smeltingRecipe = entry.getValue();
                    //TODO: Allow for specifying not copying all smelting recipes, maybe do it by the resource location
                    ItemStack recipeOutput = smeltingRecipe.getRecipeOutput();
                    if (!smeltingRecipe.isDynamic() && !recipeOutput.isEmpty()) {
                        //TODO: Can Smelting recipes even "dynamic", if so can we add some sort of checker to make getOutput return the correct result
                        NonNullList<Ingredient> ingredients = smeltingRecipe.getIngredients();
                        ItemStackIngredient input;
                        if (ingredients.isEmpty()) {
                            //Something went wrong
                            continue;
                        } else if (ingredients.size() == 1) {
                            input = ItemStackIngredient.from(ingredients.get(0));
                        } else {
                            ItemStackIngredient[] itemIngredients = new ItemStackIngredient[ingredients.size()];
                            for (int i = 0; i < ingredients.size(); i++) {
                                itemIngredients[i] = ItemStackIngredient.from(ingredients.get(i));
                            }
                            input = ItemStackIngredient.createMulti(itemIngredients);
                        }
                        // recipes.add((RECIPE_TYPE) new SmeltingIRecipe(entry.getKey(), input, recipeOutput));
                    }
                }
            }
            cachedRecipes = recipes;
        }
        return cachedRecipes;
    }


    public Stream<RECIPE_TYPE> stream(@Nullable World world) {
        return getRecipes(world).stream();
    }

    @Nullable
    public RECIPE_TYPE findFirst(@Nullable World world, Predicate<RECIPE_TYPE> matchCriteria) {
        return stream(world).filter(matchCriteria).findFirst().orElse(null);
    }

    public boolean contains(@Nullable World world, Predicate<RECIPE_TYPE> matchCriteria) {
        return stream(world).anyMatch(matchCriteria);
    }
}
