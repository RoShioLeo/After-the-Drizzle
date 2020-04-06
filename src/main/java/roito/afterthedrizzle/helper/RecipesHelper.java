package roito.afterthedrizzle.helper;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.common.recipe.drink.DrinkIngredientsRegistry;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;

import java.util.ArrayList;
import java.util.List;

public final class RecipesHelper
{
    public static DrinkRecipeInput inventoryToDrinkRecipe(ItemStackHandler inventory, Fluid input)
    {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            if (!inventory.getStackInSlot(i).isEmpty())
            {
                ingredients.add(DrinkIngredientsRegistry.getIngredientName(inventory.getStackInSlot(i).getItem()));
            }
        }
        return new DrinkRecipeInput(input, ingredients.toArray(new String[]{}));
    }
}
