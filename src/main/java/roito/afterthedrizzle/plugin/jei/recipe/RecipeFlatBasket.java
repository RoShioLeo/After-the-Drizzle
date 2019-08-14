package roito.afterthedrizzle.plugin.jei.recipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roito.silveroakoutpost.helper.NonNullListHelper;
import roito.silveroakoutpost.recipe.ISingleInRecipe;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;

import java.util.ArrayList;
import java.util.List;

public class RecipeFlatBasket implements IRecipeWrapper
{
    private final ISingleInRecipe recipe;

    public RecipeFlatBasket(ISingleInRecipe recipe)
    {
        this.recipe = recipe;
    }

    public static List<RecipeFlatBasket> getWrappedRecipeList(ISingleInRecipeManager manager)
    {
        List<RecipeFlatBasket> recipesToReturn = new ArrayList<>();
        for (ISingleInRecipe recipe : manager.getRecipes())
        {
            recipesToReturn.add(new RecipeFlatBasket(recipe));
        }
        return recipesToReturn;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInputs(VanillaTypes.ITEM, getInputs());
        ingredients.setOutputs(VanillaTypes.ITEM, getOutputs());
    }

    public NonNullList<ItemStack> getInputs()
    {
        return recipe.getInputs();
    }

    public NonNullList<ItemStack> getOutputs()
    {
        return NonNullListHelper.createNonNullList(recipe.getOutput());
    }
}
