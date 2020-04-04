package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayBakeManager;
import roito.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayIndoorsManager;
import roito.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayOutdoorsManager;
import roito.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayWetManager;

public final class RecipesRegistry
{
    public final static ISingleInRecipeManager MANAGER_BAMBOO_TRAY_OUTDOORS = new BambooTrayOutdoorsManager();
    public final static ISingleInRecipeManager MANAGER_BAMBOO_TRAY_INDOORS = new BambooTrayIndoorsManager();
    public final static ISingleInRecipeManager MANAGER_BAMBOO_TRAY_IN_RAIN = new BambooTrayWetManager();
    public final static ISingleInRecipeManager MANAGER_BAMBOO_TRAY_BAKE = new BambooTrayBakeManager();

    public RecipesRegistry()
    {
        addBasketOutdoorsRecipes();
        addBasketIndoorsRecipes();
        addBasketWetRecipes();
    }

    private static void addBasketOutdoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.RABBIT), new ItemStack(ItemsRegistry.RABBIT_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ItemsRegistry.PORK_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.BEEF), new ItemStack(ItemsRegistry.BEEF_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.MUTTON), new ItemStack(ItemsRegistry.MUTTON_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ItemsRegistry.CHICKEN_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.BEETROOT), new ItemStack(ItemsRegistry.DRIED_BEETROOT)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe(new ItemStack(Items.CARROT), new ItemStack(ItemsRegistry.DRIED_CARROT)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new SingleInRecipe("forge:food/jerky", new ItemStack(Items.LEATHER)));
    }

    private static void addBasketIndoorsRecipes()
    {
    }

    private static void addBasketWetRecipes()
    {
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.RABBIT_JERKY), new ItemStack(Items.RABBIT)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.PORK_JERKY), new ItemStack(Items.PORKCHOP)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.BEEF_JERKY), new ItemStack(Items.BEEF)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.MUTTON_JERKY), new ItemStack(Items.MUTTON)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.CHICKEN_JERKY), new ItemStack(Items.CHICKEN)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.DRIED_CARROT), new ItemStack(Items.CARROT)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new SingleInRecipe(new ItemStack(ItemsRegistry.DRIED_BEETROOT), new ItemStack(Items.BEETROOT)));
    }
}