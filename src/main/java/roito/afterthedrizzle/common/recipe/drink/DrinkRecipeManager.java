package roito.afterthedrizzle.common.recipe.drink;


import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DrinkRecipeManager
{
    private static final Map<DrinkRecipeInput, FluidStack> RECIPES = new HashMap<>();

    public void add(DrinkRecipeInput recipe, FluidStack output)
    {
        getRecipes().put(recipe, output);
    }

    public void remove(DrinkRecipeInput recipe)
    {
        getRecipes().remove(recipe.hashCode());
    }

    public void removeAll()
    {
        getRecipes().clear();
    }

    public Map<DrinkRecipeInput, FluidStack> getRecipes()
    {
        return RECIPES;
    }

    @Nullable
    public FluidStack getOutput(DrinkRecipeInput recipeIn)
    {
        return getRecipes().getOrDefault(recipeIn, null);
    }
}
