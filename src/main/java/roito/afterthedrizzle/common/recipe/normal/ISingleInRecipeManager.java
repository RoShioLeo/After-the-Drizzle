package roito.afterthedrizzle.common.recipe.normal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collection;

public interface ISingleInRecipeManager
{
    boolean equal(SingleInRecipe recipe1, SingleInRecipe recipe2);

    void add(SingleInRecipe recipe);

    void remove(SingleInRecipe recipe);

    void remove(NonNullList<ItemStack> listIn);

    void removeAll();

    Collection<SingleInRecipe> getRecipes();

    SingleInRecipe getRecipe(ItemStack input);

    SingleInRecipe getRecipe(NonNullList<ItemStack> inputs);
}
