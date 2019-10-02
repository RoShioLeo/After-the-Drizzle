package roito.afterthedrizzle.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.api.recipe.IStoneMillRecipe;
import roito.afterthedrizzle.client.gui.GuiContainerFlatBasket;
import roito.afterthedrizzle.client.gui.GuiContainerStoneMill;
import roito.afterthedrizzle.plugin.jei.category.CategoryFlatBasket;
import roito.afterthedrizzle.plugin.jei.category.CategoryStoneMill;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeFlatBasket;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeStoneMill;
import roito.afterthedrizzle.plugin.jei.wrapper.RecipeWrapperFlatBasket;
import roito.afterthedrizzle.plugin.jei.wrapper.RecipeWrapperStoneMill;
import roito.afterthedrizzle.registry.BlocksRegistry;
import roito.afterthedrizzle.registry.RecipesRegistry;
import roito.silveroakoutpost.recipe.ISingleInRecipe;

@JEIPlugin
public class JEICompat implements IModPlugin
{
    private static final String FLAT_BASKET_RAIN = "afterthedrizzle.jei.flat_basket.in_rain";
    private static final String FLAT_BASKET_SUN = "afterthedrizzle.jei.flat_basket.sun";
    private static final String FLAT_BASKET_INDOOR = "afterthedrizzle.jei.flat_basket.indoor";
    private static final String FLAT_BASKET_BAKE = "afterthedrizzle.jei.flat_basket.bake";
    private static final String FLAT_BASKET_PROCESS = "afterthedrizzle.jei.flat_basket.process";
    private static final String STONE_MILL = "afterthedrizzle.jei.stone_mill";

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(
                new CategoryFlatBasket(registry.getJeiHelpers().getGuiHelper(), FLAT_BASKET_RAIN, 0),
                new CategoryFlatBasket(registry.getJeiHelpers().getGuiHelper(), FLAT_BASKET_SUN, 1),
                new CategoryFlatBasket(registry.getJeiHelpers().getGuiHelper(), FLAT_BASKET_INDOOR, 2),
                new CategoryFlatBasket(registry.getJeiHelpers().getGuiHelper(), FLAT_BASKET_BAKE, 3),
                new CategoryFlatBasket(registry.getJeiHelpers().getGuiHelper(), FLAT_BASKET_PROCESS, 4),
                new CategoryStoneMill(registry.getJeiHelpers().getGuiHelper(), STONE_MILL)
        );
    }

    @Override
    public void register(IModRegistry registry)
    {
        registry.handleRecipes(ISingleInRecipe.class, new RecipeWrapperFlatBasket(), FLAT_BASKET_RAIN);
        registry.handleRecipes(ISingleInRecipe.class, new RecipeWrapperFlatBasket(), FLAT_BASKET_SUN);
        registry.handleRecipes(ISingleInRecipe.class, new RecipeWrapperFlatBasket(), FLAT_BASKET_INDOOR);
        registry.handleRecipes(ISingleInRecipe.class, new RecipeWrapperFlatBasket(), FLAT_BASKET_BAKE);
        registry.handleRecipes(ISingleInRecipe.class, new RecipeWrapperFlatBasket(), FLAT_BASKET_PROCESS);
        registry.handleRecipes(IStoneMillRecipe.class, new RecipeWrapperStoneMill(), STONE_MILL);

        registry.addRecipeCatalyst(new ItemStack(BlocksRegistry.FLAT_BASKET), FLAT_BASKET_RAIN, FLAT_BASKET_SUN, FLAT_BASKET_INDOOR, FLAT_BASKET_BAKE, FLAT_BASKET_PROCESS);
        registry.addRecipeCatalyst(new ItemStack(BlocksRegistry.STONE_MILL), STONE_MILL);

        registry.addRecipes(RecipeFlatBasket.getWrappedRecipeList(RecipesRegistry.MANAGER_BASKET_IN_RAIN), FLAT_BASKET_RAIN);
        registry.addRecipes(RecipeFlatBasket.getWrappedRecipeList(RecipesRegistry.MANAGER_BASKET_OUTDOORS), FLAT_BASKET_SUN);
        registry.addRecipes(RecipeFlatBasket.getWrappedRecipeList(RecipesRegistry.MANAGER_BASKET_INDOORS), FLAT_BASKET_INDOOR);
        registry.addRecipes(RecipeFlatBasket.getWrappedRecipeList(RecipesRegistry.MANAGER_BASKET_BAKE), FLAT_BASKET_BAKE);
        registry.addRecipes(RecipeStoneMill.getWrappedRecipeList(), STONE_MILL);

        registry.addRecipeClickArea(GuiContainerFlatBasket.class, 77, 31, 22, 16, FLAT_BASKET_RAIN, FLAT_BASKET_SUN, FLAT_BASKET_INDOOR, FLAT_BASKET_BAKE);
        registry.addRecipeClickArea(GuiContainerStoneMill.class, 95, 37, 22, 16, STONE_MILL);

        addIngredientToBlacklist(registry);
    }

    private final static void addIngredientToBlacklist(IModRegistry registry)
    {
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksRegistry.STONE_MILL_TOP));
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksRegistry.LIT_STOVE_DIRT));
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksRegistry.LIT_STOVE_STONE));
    }
}
