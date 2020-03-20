package roito.afterthedrizzle.common.recipe;

import roito.afterthedrizzle.common.recipe.normal.SingleInRecipe;
import roito.afterthedrizzle.common.recipe.normal.SingleInRecipeManager;

import java.util.ArrayList;
import java.util.Collection;

public class BasketIndoorsManager extends SingleInRecipeManager
{
    private static final ArrayList<SingleInRecipe> RECIPES = new ArrayList<>();

    @Override
    public Collection<SingleInRecipe> getRecipes()
    {
        return RECIPES;
    }
}
