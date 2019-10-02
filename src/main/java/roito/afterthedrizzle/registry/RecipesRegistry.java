package roito.afterthedrizzle.registry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.LoaderState;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.api.recipe.*;
import roito.silveroakoutpost.helper.LogHelper;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;
import roito.silveroakoutpost.recipe.SingleInRecipe;
import roito.silveroakoutpost.register.annotation.Load;

import java.util.ArrayList;
import java.util.List;

public final class RecipesRegistry
{
    public final static ISingleInRecipeManager MANAGER_BASKET_OUTDOORS = new BasketOutdoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_INDOORS = new BasketIndoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_IN_RAIN = new BasketWetManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_BAKE = new BasketBakeManager();
    public final static IStoneMillRecipeManager MANAGER_STONE_MILL = new StoneMillRecipeManager();

    private static List<IAction> actions = new ArrayList<IAction>();

    @Load(value = LoaderState.INITIALIZATION)
    private static void registerRecipes()
    {
        addBasketDryingRecipes();
        addBasketWetRecipes();
        addStoneMillRecipes();
        doDelayTask();
        actions = null;
    }

    public static void addAction(IAction action)
    {
        actions.add(action);
    }

    public static void doDelayTask()
    {
        for (IAction act : actions)
        {
            CraftTweakerAPI.apply(act);
            if (act.describe() != null)
            {
                LogHelper.info(AfterTheDrizzle.logger, act.describe());
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
}
