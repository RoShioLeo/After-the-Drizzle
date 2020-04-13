package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface ISingleInRecipeManager
{
    boolean equal(SingleInRecipe recipe1, SingleInRecipe recipe2);

    void add(SingleInRecipe recipe);

    void remove(SingleInRecipe recipe);

    void remove(ItemStack in);

    void removeAll();

    Collection<SingleInRecipe> getRecipes();

    SingleInRecipe getRecipe(ItemStack input);
}
