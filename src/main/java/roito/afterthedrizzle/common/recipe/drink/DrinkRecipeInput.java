package roito.afterthedrizzle.common.recipe.drink;

import net.minecraft.fluid.Fluid;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DrinkRecipeInput
{
    Map<String, Integer> ingredients = new HashMap<>();
    Fluid input;

    public DrinkRecipeInput(Fluid input, String... ingredients)
    {
        this.input = input;
        for (String ingredient : ingredients)
        {
            int j = this.ingredients.getOrDefault(ingredient, 0);
            this.ingredients.put(ingredient, ++j);
        }
    }

    public DrinkRecipeInput(String... ingredients)
    {
        this(FluidsRegistry.BOILING_WATER_STILL.get(), ingredients);
    }

    public Map<String, Integer> getInputIngredients()
    {
        return ingredients;
    }

    public Fluid getInputFluid()
    {
        return input;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ingredients, input);
    }
}
