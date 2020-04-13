package roito.afterthedrizzle.common.recipe.drink;

import net.minecraft.item.Item;
import roito.afterthedrizzle.helper.LogHelper;

import java.util.HashMap;
import java.util.Map;

public final class DrinkIngredientsManager
{
    private final static Map<String, String> INGREDIENTS = new HashMap<>();

    public static void registerIngredientItem(Item item, String ingredientName)
    {
        if (INGREDIENTS.containsKey(item))
        {
            LogHelper.info("%s already has the ingredient name \"%s\", now it changes to \"%s\"", item.getRegistryName(), INGREDIENTS.get(item.getRegistryName().toString()), ingredientName);
        }
        INGREDIENTS.put(item.getRegistryName().toString(), ingredientName.toLowerCase());
    }

    public static String getIngredientName(Item ingredient)
    {
        return INGREDIENTS.getOrDefault(ingredient.getRegistryName().toString(), "empty");
    }
}
