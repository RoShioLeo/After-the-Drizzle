package roito.afterthedrizzle.common.recipe.drink;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DrinkRecipeInput implements IDrinkRecipe
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
        this(FluidRegistry.WATER, ingredients);
    }

    @Override
    public Map<String, Integer> getInputIngredients()
    {
        return ingredients;
    }

    @Override
    public Fluid getInputFluid()
    {
        return input;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        DrinkRecipeInput that = (DrinkRecipeInput) o;
        if (that.getInputIngredients().getOrDefault("empty", 0) >= 4)
        {
            return false;
        }
        return Objects.equals(ingredients, that.ingredients) &&
                Objects.equals(input, that.input);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ingredients, input);
    }
}
