package roito.afterthedrizzle.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import roito.silveroakoutpost.helper.NonNullListHelper;

import java.util.ArrayList;
import java.util.Collection;

public class StoneMillRecipeManager implements IStoneMillRecipeManager
{
    private static final ArrayList<IStoneMillRecipe> RECIPES = new ArrayList<>();

    @Override
    public boolean equal(IStoneMillRecipe recipe1, IStoneMillRecipe recipe2)
    {
        return recipe1.equals(recipe2);
    }

    @Override
    public void add(IStoneMillRecipe recipe)
    {
        getRecipes().add(recipe);
    }

    @Override
    public void remove(IStoneMillRecipe recipe)
    {
        java.util.Iterator<IStoneMillRecipe> iter = getRecipes().iterator();
        while (iter.hasNext())
        {
            if (iter.next().equals(recipe))
            {
                iter.remove();
                return;
            }
        }
    }

    @Override
    public void remove(FluidStack inputFluid, NonNullList<ItemStack> inputStacks)
    {
        IStoneMillRecipe recipe = getRecipe(inputFluid, inputStacks);
        remove(recipe);
    }

    @Override
    public void removeAll()
    {
        getRecipes().clear();
    }

    @Override
    public Collection<IStoneMillRecipe> getRecipes()
    {
        return RECIPES;
    }

    @Override
    public IStoneMillRecipe getRecipe(FluidStack inputFluid, ItemStack inputStack)
    {
        for (IStoneMillRecipe recipe : getRecipes())
        {
            if (recipe.canWork(inputFluid, inputStack))
            {
                return recipe;
            }
        }
        return new StoneMillRecipe(inputFluid, NonNullListHelper.createNonNullList(inputStack), NonNullList.create(), new FluidStack(FluidRegistry.WATER, 0));
    }

    @Override
    public IStoneMillRecipe getRecipe(FluidStack inputFluid, NonNullList<ItemStack> inputStacks)
    {
        for (ItemStack input : inputStacks)
        {
            IStoneMillRecipe recipe = getRecipe(inputFluid, input);
            if (!recipe.getOutputStacks().isEmpty())
            {
                return recipe;
            }
        }
        return new StoneMillRecipe(inputFluid, NonNullListHelper.createNonNullList(inputStacks), NonNullList.create(), new FluidStack(FluidRegistry.WATER, 0));
    }
}
