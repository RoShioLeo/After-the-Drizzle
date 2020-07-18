package cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray;

import java.util.ArrayList;
import java.util.Collection;

public class BambooTrayWetManager extends BambooTrayRecipeManager
{
    private static final ArrayList<BambooTaryRecipe> RECIPES = new ArrayList<>();

    @Override
    public Collection<BambooTaryRecipe> getRecipes()
    {
        return RECIPES;
    }
}
