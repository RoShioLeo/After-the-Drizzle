package roito.cultivage.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.LoaderState;
import roito.cultivage.api.recipe.BasketBakeManager;
import roito.cultivage.api.recipe.BasketIndoorsManager;
import roito.cultivage.api.recipe.BasketOutdoorsManager;
import roito.cultivage.api.recipe.BasketWetManager;
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
	}

	private static void addBasketDryingRecipes()
	{
		MANAGER_BASKET_OUTDOORS.add(new SingleInRecipe("ingotIron", new ItemStack(ItemsRegistry.RAIN_GAUGE)));
	}
}
