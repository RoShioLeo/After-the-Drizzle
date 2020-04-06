package roito.afterthedrizzle.common.recipe.drink;

import net.minecraft.item.Item;
import roito.afterthedrizzle.helper.LogHelper;

import java.util.HashMap;
import java.util.Map;

public final class DrinkIngredientsRegistry
{
    private final static Map<String, String> ingredientsMap = new HashMap<>();
    private final static Map<String, Item> residuesMap = new HashMap<>();

    public static boolean registerIngredientItem(Item item, String ingredientName)
    {
        if (ingredientsMap.containsKey(item.toString()))
        {
            LogHelper.info("%s already has the ingredient name \"%s\", now it changes to \"%s\"", item.getRegistryName(), ingredientsMap.get(item), ingredientName);
        }
        ingredientsMap.put(item.toString(), ingredientName);
        return true;
    }

    public static String getIngredientName(Item ingredient)
    {
        return ingredientsMap.getOrDefault(ingredient.toString(), "empty");
    }

    public static boolean registerResidueItem(Item ingredient, Item residue)
    {
        residuesMap.put(ingredient.toString(), residue);
        return true;
    }

    public static Item getResidue(Item ingredient)
    {
        return residuesMap.getOrDefault(ingredient.toString(), null);
    }
}
