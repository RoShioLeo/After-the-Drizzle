package cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class BambooTrayRecipeManager implements IBambooTrayRecipeManager
{
    @Override
    public boolean equal(BambooTaryRecipe recipe1, BambooTaryRecipe recipe2)
    {
        return recipe1.equals(recipe2);
    }

    @Override
    public void add(BambooTaryRecipe recipe)
    {
        getRecipes().add(recipe);
    }

    @Override
    public void remove(BambooTaryRecipe recipe)
    {
        java.util.Iterator<BambooTaryRecipe> iter = getRecipes().iterator();
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
        BambooTaryRecipe recipe = getRecipe(in);
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
    public BambooTaryRecipe getRecipe(@Nonnull ItemStack input)
    {
        for (BambooTaryRecipe recipe : getRecipes())
        {
            if (recipe.isTheSameInput(input))
            {
                return recipe;
            }
        }
        return new BambooTaryRecipe(input, ItemStack.EMPTY);
    }
}
