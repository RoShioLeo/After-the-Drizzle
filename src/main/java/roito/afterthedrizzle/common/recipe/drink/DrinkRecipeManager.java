package roito.afterthedrizzle.common.recipe.drink;


import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class DrinkRecipeManager
{
    private static final Map<Integer, FluidStack> RECIPES = new HashMap<>();

    public void add(DrinkRecipeInput recipe, FluidStack output)
    {
        getRecipes().put(recipe.hashCode(), output);
    }

    public void remove(DrinkRecipeInput recipe)
    {
        getRecipes().remove(recipe.hashCode());
    }

    public void removeAll()
    {
        getRecipes().clear();
    }

    public Map<Integer, FluidStack> getRecipes()
    {
        return RECIPES;
    }

    public FluidStack getOutput(DrinkRecipeInput inventoryRecipe)
    {
        return RECIPES.getOrDefault(inventoryRecipe.hashCode(), FluidStack.EMPTY);
    }
}
