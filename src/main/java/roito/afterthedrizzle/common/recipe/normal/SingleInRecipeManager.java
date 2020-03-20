package roito.afterthedrizzle.common.recipe.normal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roito.afterthedrizzle.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public abstract class SingleInRecipeManager implements ISingleInRecipeManager
{
    @Override
    public boolean equal(SingleInRecipe recipe1, SingleInRecipe recipe2)
    {
        return recipe1.equals(recipe2);
    }

    @Override
    public void add(SingleInRecipe recipe)
    {
        getRecipes().add(recipe);
    }

    @Override
    public void remove(SingleInRecipe recipe)
    {
        java.util.Iterator<SingleInRecipe> iter = getRecipes().iterator();
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
    public void remove(NonNullList<ItemStack> listIn)
    {
        SingleInRecipe recipe = getRecipe(listIn);
        if (!recipe.getOutput().isEmpty())
        {
            remove(recipe);
        }
    }

    @Override
    public void removeAll()
    {
        getRecipes().clear();
    }

    @Nonnull
    @Override
    public SingleInRecipe getRecipe(@Nonnull ItemStack input)
    {
        for (SingleInRecipe recipe : getRecipes())
        {
            if (recipe.isTheSameInput(input))
            {
                return recipe;
            }
        }
        return new SingleInRecipe(NonNullListHelper.createNonNullList(input), ItemStack.EMPTY);
    }

    @Nonnull
    @Override
    public SingleInRecipe getRecipe(@Nonnull NonNullList<ItemStack> inputs)
    {
        for (ItemStack input : inputs)
        {
            SingleInRecipe recipe = getRecipe(input);
            if (!recipe.getOutput().isEmpty())
            {
                return recipe;
            }
        }
        return new SingleInRecipe(inputs, ItemStack.EMPTY);
    }
}
