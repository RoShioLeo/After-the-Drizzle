package roito.cultivage.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.LoaderState;
import roito.cultivage.api.recipe.BasketDryingManager;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;
import roito.silveroakoutpost.recipe.SingleInRecipe;
import roito.silveroakoutpost.register.annotation.Load;

public final class RecipesRegistry
{
	public final static ISingleInRecipeManager managerBasketDrying = new BasketDryingManager();

	@Load(value = LoaderState.INITIALIZATION)
	private static void registerRecipes()
	{
		addBasketDryingRecipes();
	}

	private static void addBasketDryingRecipes()
	{
		managerBasketDrying.add(new SingleInRecipe("ingotIron", new ItemStack(ItemsRegistry.RAIN_GAUGE)));
	}
}
