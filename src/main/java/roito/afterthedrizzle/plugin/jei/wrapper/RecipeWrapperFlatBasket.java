package roito.afterthedrizzle.plugin.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import roito.afterthedrizzle.common.recipe.normal.SingleInRecipe;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeFlatBasket;

public class RecipeWrapperFlatBasket implements IRecipeWrapperFactory<SingleInRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(SingleInRecipe SingleInRecipe)
    {
        return new RecipeFlatBasket(SingleInRecipe);
    }
}
