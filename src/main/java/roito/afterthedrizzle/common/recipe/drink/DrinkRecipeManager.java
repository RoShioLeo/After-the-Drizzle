package roito.afterthedrizzle.common.recipe.drink;


import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DrinkRecipeManager
{
    private static final Map<IDrinkRecipe, FluidStack> RECIPES = new HashMap<>();

    public boolean equal(IDrinkRecipe recipe1, IDrinkRecipe recipe2)
    {
        return recipe1.equals(recipe2);
    }

    public void add(IDrinkRecipe recipe, FluidStack output)
    {
        getRecipes().put(recipe, output);
    }

    public void remove(IDrinkRecipe recipe)
    {
        getRecipes().remove(recipe.hashCode());
    }

    public void removeAll()
    {
        getRecipes().clear();
    }

    public Map<IDrinkRecipe, FluidStack> getRecipes()
    {
        return RECIPES;
    }

    @Nullable
    public FluidStack getOutput(IDrinkRecipe recipeIn)
    {
        return getRecipes().getOrDefault(recipeIn, null);
    }
}
