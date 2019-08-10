package roito.afterthedrizzle.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.LoaderState;
import roito.afterthedrizzle.api.recipe.BasketBakeManager;
import roito.afterthedrizzle.api.recipe.BasketIndoorsManager;
import roito.afterthedrizzle.api.recipe.BasketOutdoorsManager;
import roito.afterthedrizzle.api.recipe.BasketWetManager;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;
import roito.silveroakoutpost.recipe.SingleInRecipe;
import roito.silveroakoutpost.register.annotation.Load;

public final class RecipesRegistry
{
    public final static ISingleInRecipeManager MANAGER_BASKET_OUTDOORS = new BasketOutdoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_INDOORS = new BasketIndoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_IN_RAIN = new BasketWetManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_BAKE = new BasketBakeManager();


    @Load(value = LoaderState.INITIALIZATION)
    private static void registerRecipes()
    {
        addBasketDryingRecipes();
        addBasketWetRecipes();
    }

    private static void addBasketDryingRecipes()
    {
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.RABBIT), new ItemStack(ItemsRegistry.RABBIT_JERKY)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ItemsRegistry.PORK_JERKY)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.BEEF), new ItemStack(ItemsRegistry.BEEF_JERKY)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.MUTTON), new ItemStack(ItemsRegistry.MUTTON_JERKY)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ItemsRegistry.CHICKEN_JERKY)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.BEETROOT), new ItemStack(ItemsRegistry.DRIED_BEETROOT)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.CARROT), new ItemStack(ItemsRegistry.DRIED_CARROT)));
        MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe("foodJerky", new ItemStack(Items.LEATHER)));
    }

    private static void addBasketWetRecipes()
    {
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.RABBIT_JERKY), new ItemStack(Items.RABBIT)));
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.PORK_JERKY), new ItemStack(Items.PORKCHOP)));
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.BEEF_JERKY), new ItemStack(Items.BEEF)));
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.MUTTON_JERKY), new ItemStack(Items.MUTTON)));
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.CHICKEN_JERKY), new ItemStack(Items.CHICKEN)));
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.DRIED_CARROT), new ItemStack(Items.CARROT)));
        MANAGER_BASKET_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.DRIED_BEETROOT), new ItemStack(Items.BEETROOT)));
    }
}
