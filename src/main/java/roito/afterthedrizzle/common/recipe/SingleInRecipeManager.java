package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.ItemStack;

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
    public void remove(ItemStack in)
    {
        SingleInRecipe recipe = getRecipe(in);
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
        return new SingleInRecipe(input, ItemStack.EMPTY);
    }
}
