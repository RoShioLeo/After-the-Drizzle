package roito.afterthedrizzle.plugin.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import roito.afterthedrizzle.api.recipe.IStoneMillRecipe;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeStoneMill;

public class RecipeWrapperStoneMill implements IRecipeWrapperFactory<IStoneMillRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(IStoneMillRecipe iStoneMillRecipe)
    {
        return new RecipeStoneMill(iStoneMillRecipe);
    }
}
