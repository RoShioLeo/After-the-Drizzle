package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.bamboo_tray.*;
import roito.afterthedrizzle.common.recipe.drink.DrinkIngredientsRegistry;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeManager;

public final class RecipesRegistry
{
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_OUTDOORS = new BambooTrayOutdoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_INDOORS = new BambooTrayIndoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_IN_RAIN = new BambooTrayWetManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_BAKE = new BambooTrayBakeManager();
    public final static DrinkRecipeManager MANAGER_DRINK_MAKER = new DrinkRecipeManager();

    public RecipesRegistry()
    {
        addBasketOutdoorsRecipes();
        addBasketIndoorsRecipes();
        addBasketWetRecipes();
        addDrinkRecipes();
    }

    private static void addBasketOutdoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.RABBIT), new ItemStack(ItemsRegistry.RABBIT_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ItemsRegistry.PORK_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.BEEF), new ItemStack(ItemsRegistry.BEEF_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.MUTTON), new ItemStack(ItemsRegistry.MUTTON_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ItemsRegistry.CHICKEN_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.BEETROOT), new ItemStack(ItemsRegistry.DRIED_BEETROOT)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.CARROT), new ItemStack(ItemsRegistry.DRIED_CARROT)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe("forge:food/jerky", new ItemStack(Items.LEATHER)));
    }

    private static void addBasketIndoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe("forge:food/jerky", new ItemStack(Items.ROTTEN_FLESH)));
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe(new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE)));
    }

    private static void addBasketWetRecipes()
    {
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.RABBIT_JERKY), new ItemStack(Items.RABBIT)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.PORK_JERKY), new ItemStack(Items.PORKCHOP)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.BEEF_JERKY), new ItemStack(Items.BEEF)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.MUTTON_JERKY), new ItemStack(Items.MUTTON)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.CHICKEN_JERKY), new ItemStack(Items.CHICKEN)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.DRIED_CARROT), new ItemStack(Items.CARROT)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.DRIED_BEETROOT), new ItemStack(Items.BEETROOT)));
    }

    private static void addDrinkRecipes()
    {
        DrinkIngredientsRegistry.registerIngredientItem(Items.SUGAR, "sugar");
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("sugar"), new FluidStack(FluidsRegistry.SUGARY_WATER_STILL.get(), 100));
    }
}
