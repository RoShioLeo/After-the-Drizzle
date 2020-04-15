package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import roito.afterthedrizzle.common.drink.DrinkIngredientsManager;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.bamboo_tray.*;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeManager;

public final class RecipesRegistry
{
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_OUTDOORS = new BambooTrayOutdoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_INDOORS = new BambooTrayIndoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_IN_RAIN = new BambooTrayWetManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_BAKE = new BambooTrayBakeManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_PROCESS = new BambooTrayProcessManager();
    public final static DrinkRecipeManager MANAGER_DRINK_MAKER = new DrinkRecipeManager();

    public RecipesRegistry()
    {
        addBasketOutdoorsRecipes();
        addBasketIndoorsRecipes();
        addBasketWetRecipes();
        registerDrinkIngredients();
        addDrinkRecipes();
    }

    private static void addBasketOutdoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.ROTTEN_FLESH, Items.LEATHER));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.RABBIT, ItemsRegistry.RABBIT_JERKY));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.PORKCHOP, ItemsRegistry.PORK_JERKY));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.BEEF, ItemsRegistry.BEEF_JERKY));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.MUTTON, ItemsRegistry.MUTTON_JERKY));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.CHICKEN, ItemsRegistry.CHICKEN_JERKY));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.BEETROOT, ItemsRegistry.DRIED_BEETROOT));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(Items.CARROT, ItemsRegistry.DRIED_CARROT));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe("forge:food/jerky", Items.LEATHER));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe("forge:crops/tealeaf", ItemsRegistry.GREEN_TEA_LEAVES));
    }

    private static void addBasketIndoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe("forge:food/meat", Items.ROTTEN_FLESH));
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE));
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe("forge:crops/tealeaf", ItemsRegistry.BLACK_TEA_LEAVES));
    }

    private static void addBasketWetRecipes()
    {
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.RABBIT_JERKY, Items.RABBIT));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.PORK_JERKY, Items.PORKCHOP));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.BEEF_JERKY, Items.BEEF));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.MUTTON_JERKY, Items.MUTTON));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.CHICKEN_JERKY, Items.CHICKEN));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.DRIED_CARROT, Items.CARROT));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(ItemsRegistry.DRIED_BEETROOT, Items.BEETROOT));
    }

    private static void registerDrinkIngredients()
    {
        DrinkIngredientsManager.registerIngredientItem(Items.SUGAR, "sugar");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.GREEN_TEA_LEAVES, "green_tea_leaves");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.BLACK_TEA_LEAVES, "black_tea_leaves");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.GREEN_TEA_BAG, "green_tea_bag");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.BLACK_TEA_BAG, "black_tea_bag");
    }

    private static void addDrinkRecipes()
    {
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("sugar", "sugar", "sugar", "sugar"), new FluidStack(FluidsRegistry.SUGARY_WATER_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_leaves", "green_tea_leaves", "green_tea_leaves", "green_tea_leaves"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_bag"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_leaves", "black_tea_leaves", "black_tea_leaves", "black_tea_leaves"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_bag"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
    }
}
