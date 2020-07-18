package cloud.lemonslice.afterthedrizzle.common.recipe;

import cloud.lemonslice.afterthedrizzle.common.drink.DrinkEffectAttribute;
import cloud.lemonslice.afterthedrizzle.common.drink.DrinkEffectsManager;
import cloud.lemonslice.afterthedrizzle.common.drink.DrinkIngredientsManager;
import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import cloud.lemonslice.afterthedrizzle.common.item.ItemsRegistry;
import cloud.lemonslice.afterthedrizzle.common.potion.EffectsRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.*;
import cloud.lemonslice.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import cloud.lemonslice.afterthedrizzle.common.recipe.drink.DrinkRecipeManager;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraftforge.fluids.FluidStack;

public final class RecipesRegistry
{
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_OUTDOORS = new BambooTrayOutdoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_INDOORS = new BambooTrayIndoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_IN_RAIN = new BambooTrayWetManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_BAKE = new BambooTrayBakeManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_PROCESS = new BambooTrayProcessManager();
    public final static DrinkRecipeManager MANAGER_DRINK_MAKER = new DrinkRecipeManager();

    public static void init()
    {
        addBasketOutdoorsRecipes();
        addBasketIndoorsRecipes();
        addBasketBakeRecipes();
        addBasketWetRecipes();
        registerDrinkIngredients();
        registerDrinkEffects();
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
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe("forge:crops/tea_leaf", ItemsRegistry.GREEN_TEA_LEAVES));
    }

    private static void addBasketIndoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe("forge:food/meat", Items.ROTTEN_FLESH));
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE));
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe("forge:crops/tea_leaf", ItemsRegistry.BLACK_TEA_LEAVES));
    }

    private static void addBasketBakeRecipes()
    {
        MANAGER_BAMBOO_TRAY_BAKE.add(new BambooTaryRecipe("forge:crops/tea_leaf", ItemsRegistry.WHITE_TEA_LEAVES));
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
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe("forge:crops/tea_leaf", ItemsRegistry.TEA_RESIDUES));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe("forge:crops/black_tea_leaf", ItemsRegistry.TEA_RESIDUES));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe("forge:crops/green_tea_leaf", ItemsRegistry.TEA_RESIDUES));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe("forge:crops/white_tea_leaf", ItemsRegistry.TEA_RESIDUES));
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
