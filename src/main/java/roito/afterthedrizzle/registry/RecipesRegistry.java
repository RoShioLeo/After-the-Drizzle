package roito.afterthedrizzle.registry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import roito.afterthedrizzle.common.drink.DrinkIngredientsRegistry;
import roito.afterthedrizzle.common.recipe.BasketBakeManager;
import roito.afterthedrizzle.common.recipe.BasketIndoorsManager;
import roito.afterthedrizzle.common.recipe.BasketOutdoorsManager;
import roito.afterthedrizzle.common.recipe.BasketWetManager;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeManager;
import roito.afterthedrizzle.common.recipe.normal.ISingleInRecipeManager;
import roito.afterthedrizzle.common.recipe.normal.SingleInRecipe;
import roito.afterthedrizzle.helper.LogHelper;
import roito.afterthedrizzle.registry.fluid.FluidsRegistry;

import java.util.ArrayList;
import java.util.List;

public final class RecipesRegistry
{
    public final static ISingleInRecipeManager MANAGER_BASKET_OUTDOORS = new BasketOutdoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_INDOORS = new BasketIndoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_IN_RAIN = new BasketWetManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_BAKE = new BasketBakeManager();
    public final static DrinkRecipeManager MANAGER_DRINK_MAKER = new DrinkRecipeManager();

    private static List<IAction> actions = new ArrayList<IAction>();

    public RecipesRegistry()
    {
        addBasketDryingRecipes();
        addBasketWetRecipes();
        addStoneMillRecipes();
        addDrinkRecipes();
        if (Loader.isModLoaded("crafttweaker"))
        {
            doDelayTask();
        }
        actions = null;
    }

    public static void addAction(IAction action)
    {
        actions.add(action);
    }

    @Optional.Method(modid = "crafttweaker")
    public static void doDelayTask()
    {
        for (IAction act : actions)
        {
            CraftTweakerAPI.apply(act);
            if (act.describe() != null)
            {
                LogHelper.info(act.describe());
            }
        }
        actions.clear();
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

    private static void addStoneMillRecipes()
    {

    }

    private static void addDrinkRecipes()
    {
        DrinkIngredientsRegistry.registerIngredientItem(new ItemStack(Items.SUGAR), "sugar");
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("sugar"), new FluidStack(FluidsRegistry.SUGARY_WATER, 100));
    }
}
