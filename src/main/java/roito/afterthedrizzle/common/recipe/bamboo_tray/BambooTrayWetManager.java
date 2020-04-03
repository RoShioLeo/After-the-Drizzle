package roito.afterthedrizzle.common.recipe.bamboo_tray;

import roito.afterthedrizzle.common.recipe.SingleInRecipe;
import roito.afterthedrizzle.common.recipe.SingleInRecipeManager;

import java.util.ArrayList;
import java.util.Collection;

public class BambooTrayWetManager extends SingleInRecipeManager
{
    private static final ArrayList<SingleInRecipe> RECIPES = new ArrayList<>();

    @Override
    public Collection<SingleInRecipe> getRecipes()
    {
        return RECIPES;
    }
}
