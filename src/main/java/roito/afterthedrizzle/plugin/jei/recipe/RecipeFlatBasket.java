package roito.afterthedrizzle.plugin.jei.recipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roito.afterthedrizzle.common.recipe.normal.ISingleInRecipeManager;
import roito.afterthedrizzle.common.recipe.normal.SingleInRecipe;
import roito.afterthedrizzle.helper.NonNullListHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeFlatBasket implements IRecipeWrapper
{
    private final SingleInRecipe recipe;

    public RecipeFlatBasket(SingleInRecipe recipe)
    {
        this.recipe = recipe;
    }

    public static List<RecipeFlatBasket> getWrappedRecipeList(ISingleInRecipeManager manager)
    {
        List<RecipeFlatBasket> recipesToReturn = new ArrayList<>();
        for (SingleInRecipe recipe : manager.getRecipes())
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
