package cloud.lemonslice.afterthedrizzle.common.recipe;

import cloud.lemonslice.afterthedrizzle.common.drink.DrinkEffectAttribute;
import cloud.lemonslice.afterthedrizzle.common.drink.DrinkEffectsManager;
import cloud.lemonslice.afterthedrizzle.common.drink.DrinkIngredientsManager;
import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import cloud.lemonslice.afterthedrizzle.common.item.ItemsRegistry;
import cloud.lemonslice.afterthedrizzle.common.potion.EffectsRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import cloud.lemonslice.afterthedrizzle.common.recipe.drink.DrinkRecipeManager;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraftforge.fluids.FluidStack;

public final class RecipesRegistry
{
    public final static DrinkRecipeManager MANAGER_DRINK_MAKER = new DrinkRecipeManager();

    public static void init()
    {
        registerDrinkIngredients();
        registerDrinkEffects();
        addDrinkRecipes();
    }

    private static void registerDrinkIngredients()
    {
        DrinkIngredientsManager.registerIngredientItem(Items.SUGAR, "sugar");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.GREEN_TEA_LEAVES, "green_tea_leaf");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.BLACK_TEA_LEAVES, "black_tea_leaf");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.WHITE_TEA_LEAVES, "white_tea_leaf");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.GREEN_TEA_BAG, "green_tea_bag");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.BLACK_TEA_BAG, "black_tea_bag");
    }

    private static void registerDrinkEffects()
    {
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.SUGARY_WATER_STILL.get(), new DrinkEffectAttribute(Effects.SPEED, 2, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.WEAK_GREEN_TEA_STILL.get(), new DrinkEffectAttribute(EffectsRegistry.AGILITY, 2, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.WEAK_BLACK_TEA_STILL.get(), new DrinkEffectAttribute(Effects.HEALTH_BOOST, 4, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.WEAK_WHITE_TEA_STILL.get(), new DrinkEffectAttribute(Effects.HASTE, 2, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.GREEN_TEA_STILL.get(), new DrinkEffectAttribute(EffectsRegistry.AGILITY, 2, 1), new DrinkEffectAttribute(EffectsRegistry.EXCITEMENT, 2, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.BLACK_TEA_STILL.get(), new DrinkEffectAttribute(Effects.HEALTH_BOOST, 4, 1), new DrinkEffectAttribute(EffectsRegistry.EXCITEMENT, 4, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.WHITE_TEA_STILL.get(), new DrinkEffectAttribute(Effects.HASTE, 2, 1), new DrinkEffectAttribute(EffectsRegistry.EXCITEMENT, 2, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.STRONG_GREEN_TEA_STILL.get(), new DrinkEffectAttribute(EffectsRegistry.AGILITY, 2, 2), new DrinkEffectAttribute(EffectsRegistry.EXCITEMENT, 4, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.STRONG_BLACK_TEA_STILL.get(), new DrinkEffectAttribute(Effects.HEALTH_BOOST, 4, 2), new DrinkEffectAttribute(EffectsRegistry.EXCITEMENT, 8, 0));
        DrinkEffectsManager.registerDrinkEffects(FluidsRegistry.STRONG_WHITE_TEA_STILL.get(), new DrinkEffectAttribute(Effects.HASTE, 2, 2), new DrinkEffectAttribute(EffectsRegistry.EXCITEMENT, 4, 0));
    }

    private static void addDrinkRecipes()
    {
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("sugar", "sugar", "sugar", "sugar"), new FluidStack(FluidsRegistry.SUGARY_WATER_STILL.get(), 500));

        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_leaf", "green_tea_leaf"), new FluidStack(FluidsRegistry.WEAK_GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput(FluidsRegistry.WEAK_GREEN_TEA_STILL.get(), "green_tea_leaf", "green_tea_leaf", "green_tea_leaf"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_leaf", "green_tea_leaf", "green_tea_leaf", "green_tea_leaf"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_bag"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_bag", "green_tea_bag"), new FluidStack(FluidsRegistry.STRONG_GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput(FluidsRegistry.GREEN_TEA_STILL.get(), "green_tea_leaf", "green_tea_leaf", "green_tea_leaf", "green_tea_leaf"), new FluidStack(FluidsRegistry.STRONG_GREEN_TEA_STILL.get(), 500));

        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_leaf", "black_tea_leaf"), new FluidStack(FluidsRegistry.WEAK_BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput(FluidsRegistry.WEAK_BLACK_TEA_STILL.get(), "black_tea_leaf", "black_tea_leaf", "black_tea_leaf"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_leaf", "black_tea_leaf", "black_tea_leaf", "black_tea_leaf"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_bag"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_bag", "black_tea_bag"), new FluidStack(FluidsRegistry.STRONG_BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput(FluidsRegistry.BLACK_TEA_STILL.get(), "black_tea_leaf", "black_tea_leaf", "black_tea_leaf", "black_tea_leaf"), new FluidStack(FluidsRegistry.STRONG_BLACK_TEA_STILL.get(), 500));

        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("white_tea_leaf", "white_tea_leaf"), new FluidStack(FluidsRegistry.WEAK_WHITE_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput(FluidsRegistry.WEAK_WHITE_TEA_STILL.get(), "white_tea_leaf", "white_tea_leaf", "white_tea_leaf"), new FluidStack(FluidsRegistry.WHITE_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("white_tea_leaf", "white_tea_leaf", "white_tea_leaf", "white_tea_leaf"), new FluidStack(FluidsRegistry.WHITE_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput(FluidsRegistry.WHITE_TEA_STILL.get(), "white_tea_leaf", "white_tea_leaf", "white_tea_leaf", "white_tea_leaf"), new FluidStack(FluidsRegistry.STRONG_WHITE_TEA_STILL.get(), 500));
    }
}
