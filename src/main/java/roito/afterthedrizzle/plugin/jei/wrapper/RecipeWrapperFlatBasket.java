package roito.afterthedrizzle.plugin.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeFlatBasket;
import roito.silveroakoutpost.recipe.ISingleInRecipe;

public class RecipeWrapperFlatBasket implements IRecipeWrapperFactory<ISingleInRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(ISingleInRecipe iSingleInRecipe)
    {
        return new RecipeFlatBasket(iSingleInRecipe);
    }
}
