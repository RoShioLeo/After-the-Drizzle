package cloud.lemonslice.afterthedrizzle.common.drink;

import cloud.lemonslice.afterthedrizzle.helper.LogHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

public final class DrinkIngredientsManager
{
    private final static Map<String, String> INGREDIENTS = new HashMap<>();
    private final static Map<String, List<ItemStack>> ITEMS = new HashMap<>();

    public static void registerIngredientItem(Item item, String ingredientName)
    {
        if (INGREDIENTS.containsKey(item))
        {
            LogHelper.info("%s already has the ingredient name \"%s\", now it changes to \"%s\"", item.getRegistryName(), INGREDIENTS.get(item.getRegistryName().toString()), ingredientName);
        }
        INGREDIENTS.put(item.getRegistryName().toString(), ingredientName.toLowerCase());
        List<ItemStack> list = ITEMS.getOrDefault(ingredientName, new ArrayList<>());
        list.add(new ItemStack(item));
        ITEMS.put(ingredientName, list);
    }

    public static String getIngredientName(Item ingredient)
    {
        return INGREDIENTS.getOrDefault(ingredient.getRegistryName().toString(), "empty");
    }

    public static List<ItemStack> getIngredientItems(String name)
    {
        return ITEMS.getOrDefault(name, Collections.emptyList());
    }
}
