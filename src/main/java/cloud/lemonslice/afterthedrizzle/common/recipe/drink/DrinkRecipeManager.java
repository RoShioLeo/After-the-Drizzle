package cloud.lemonslice.afterthedrizzle.common.recipe.drink;


import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DrinkRecipeManager
{
    private static final Map<Integer, FluidStack> RECIPES = new HashMap<>();
    private static final Set<DrinkRecipeInput> RECIPE_SET = new HashSet<>();

    public void add(DrinkRecipeInput recipe, FluidStack output)
    {
        getRecipes().put(recipe.hashCode(), output);
        RECIPE_SET.add(recipe);
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

    public Set<DrinkRecipeInput> getSet()
    {
        return RECIPE_SET;
    }

    public FluidStack getOutput(DrinkRecipeInput inventoryRecipe)
    {
        return RECIPES.getOrDefault(inventoryRecipe.hashCode(), FluidStack.EMPTY);
    }
}
