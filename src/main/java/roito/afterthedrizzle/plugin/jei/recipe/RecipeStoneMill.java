package roito.afterthedrizzle.plugin.jei.recipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import roito.afterthedrizzle.api.recipe.IStoneMillRecipe;
import roito.afterthedrizzle.registry.RecipesRegistry;

import java.util.ArrayList;
import java.util.List;

public class RecipeStoneMill implements IRecipeWrapper
{
    private final IStoneMillRecipe recipe;

    public RecipeStoneMill(IStoneMillRecipe recipe)
    {
        this.recipe = recipe;
    }

    public static List<RecipeStoneMill> getWrappedRecipeList()
    {
        List<RecipeStoneMill> recipesToReturn = new ArrayList<>();
        for (IStoneMillRecipe recipe : RecipesRegistry.MANAGER_STONE_MILL.getRecipes())
        {
            recipesToReturn.add(new RecipeStoneMill(recipe));
        }
        return recipesToReturn;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInputs(VanillaTypes.ITEM, getItemInputs());
        ingredients.setOutputs(VanillaTypes.ITEM, getItemOutputs());
        ingredients.setInput(VanillaTypes.FLUID, getFluidInput());
        ingredients.setOutput(VanillaTypes.FLUID, getFluidOutput());
    }

    public NonNullList<ItemStack> getItemInputs()
    {
        return recipe.getInputStacks();
    }

    public NonNullList<ItemStack> getItemOutputs()
    {
        return recipe.getOutputStacks();
    }

    public FluidStack getFluidInput()
    {
        return recipe.getInputFluid();
    }

    public FluidStack getFluidOutput()
    {
        return recipe.getOutputFluid();
    }
}
