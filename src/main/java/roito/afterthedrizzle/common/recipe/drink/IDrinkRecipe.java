package roito.afterthedrizzle.common.recipe.drink;

import net.minecraftforge.fluids.Fluid;

import java.util.Map;

public interface IDrinkRecipe
{
    Map<String, Integer> getInputIngredients();

    Fluid getInputFluid();
}
