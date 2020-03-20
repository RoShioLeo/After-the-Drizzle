package roito.afterthedrizzle.common.drink;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.silveroakoutpost.helper.LogHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DrinkIngredientsRegistry
{
    private final static Map<String, String> ingredientsMap = new HashMap<>();
    private final static Map<String, ItemStack> residuesMap = new HashMap<>();
    private final static Set<String> isIngredientStrict = new HashSet<>();
    private final static Set<String> isResidueStrict = new HashSet<>();

    public static boolean registerIngredientItem(ItemStack itemStack, String ingredientName)
    {
        if (itemStack.isEmpty())
        {
            return false;
        }
        itemStack.setCount(1);
        if (ingredientsMap.containsKey(itemStack.toString()))
        {
            LogHelper.info(AfterTheDrizzle.logger, "%s already has the ingredient name \"%s\", now it changes to \"%s\"", itemStack.getItem().getRegistryName(), ingredientsMap.get(itemStack), ingredientName);
        }
        ingredientsMap.put(itemStack.toString(), ingredientName);
        if (itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE)
        {
            isIngredientStrict.add(itemStack.getItem().getRegistryName().toString());
        }
        return true;
    }

    public static String getIngredientName(ItemStack ingredient)
    {
        ItemStack in = ingredient.copy();
        in.setCount(1);
        if (isIngredientStrict.contains(ingredient.getItem().getRegistryName().toString()))
        {
            in.setItemDamage(OreDictionary.WILDCARD_VALUE);
        }
        return ingredientsMap.getOrDefault(in.toString(), "empty");
    }

    public static boolean registerResidueItem(ItemStack ingredient, ItemStack residue)
    {
        if (ingredient.isEmpty() || residue.isEmpty())
        {
            return false;
        }
        ingredient.setCount(1);
        residuesMap.put(ingredient.toString(), residue);
        if (ingredient.getMetadata() == OreDictionary.WILDCARD_VALUE)
        {
            isResidueStrict.add(ingredient.getItem().getRegistryName().toString());
        }
        return true;
    }

    public static ItemStack getResidue(ItemStack ingredient)
    {
        ItemStack in = ingredient.copy();
        in.setCount(1);
        if (isResidueStrict.contains(ingredient.getItem().getRegistryName().toString()))
        {
            in.setItemDamage(OreDictionary.WILDCARD_VALUE);
        }
        return residuesMap.getOrDefault(ingredient.toString(), ItemStack.EMPTY);
    }
}
