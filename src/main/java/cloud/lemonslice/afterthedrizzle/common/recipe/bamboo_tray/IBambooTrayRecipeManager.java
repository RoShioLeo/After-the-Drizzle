package cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray;

import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface IBambooTrayRecipeManager
{
    boolean equal(BambooTaryRecipe recipe1, BambooTaryRecipe recipe2);

    void add(BambooTaryRecipe recipe);

    void remove(BambooTaryRecipe recipe);

    void remove(ItemStack in);

    void removeAll();

    Collection<BambooTaryRecipe> getRecipes();

    BambooTaryRecipe getRecipe(ItemStack input);
}
