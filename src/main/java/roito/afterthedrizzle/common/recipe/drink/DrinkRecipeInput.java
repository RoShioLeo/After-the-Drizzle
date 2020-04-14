package roito.afterthedrizzle.common.recipe.drink;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.common.drink.DrinkIngredientsManager;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;

import java.util.*;

public class DrinkRecipeInput
{
    public final Map<String, Integer> TAG_MAP = new HashMap<>();
    public final Fluid INPUT;

    public DrinkRecipeInput(List<String> tags, Fluid input)
    {
        for (String tag : tags)
        {
            int count = TAG_MAP.getOrDefault(tag, 0);
            TAG_MAP.put(tag, ++count);
        }
        this.INPUT = input;
    }

    public DrinkRecipeInput(String... tags)
    {
        this(Arrays.asList(tags), FluidsRegistry.BOILING_WATER_STILL.get());
    }

    public static DrinkRecipeInput toRecipe(ItemStackHandler inventory, Fluid input)
    {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            if (!inventory.getStackInSlot(i).isEmpty())
            {
                ingredients.add(DrinkIngredientsManager.getIngredientName(inventory.getStackInSlot(i).getItem()));
            }
        }
        return new DrinkRecipeInput(ingredients, input);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(TAG_MAP, INPUT);
    }
}
