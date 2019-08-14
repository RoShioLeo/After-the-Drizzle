package roito.afterthedrizzle.registry;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import roito.afterthedrizzle.api.recipe.*;
import roito.silveroakoutpost.helper.NonNullListHelper;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;
import roito.silveroakoutpost.recipe.SingleInRecipe;
import roito.silveroakoutpost.register.annotation.Load;

public final class RecipesRegistry
{
    public final static ISingleInRecipeManager MANAGER_BASKET_OUTDOORS = new BasketOutdoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_INDOORS = new BasketIndoorsManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_IN_RAIN = new BasketWetManager();
    public final static ISingleInRecipeManager MANAGER_BASKET_BAKE = new BasketBakeManager();
    public final static IStoneMillRecipeManager MANAGER_STONE_MILL = new StoneMillRecipeManager();


    @Load(value = LoaderState.INITIALIZATION)
    private static void registerRecipes()
    {
        addBasketDryingRecipes();
        addBasketWetRecipes();
        addStoneMillRecipes();
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
        MANAGER_STONE_MILL.add(new StoneMillRecipe(new FluidStack(FluidRegistry.WATER, 100), new ItemStack(Blocks.STONE), NonNullListHelper.createNonNullList(new ItemStack(ItemsRegistry.ASH)), new FluidStack(FluidRegistry.LAVA, 100)));
        if (Loader.isModLoaded("tofucraft"))
        {
            MANAGER_STONE_MILL.add(new StoneMillRecipe(new FluidStack(FluidRegistry.WATER, 100), "cropSoybean", NonNullListHelper.createNonNullList(new ItemStack(ItemLoader.material, 1, 11)), new FluidStack(BlockLoader.SOYMILK_FLUID, 100)));
        }
        MANAGER_STONE_MILL.add(new StoneMillRecipe(new ItemStack(Items.GOLD_INGOT), NonNullListHelper.createNonNullList(new ItemStack(Items.GOLD_NUGGET, 9))));
    }
}
