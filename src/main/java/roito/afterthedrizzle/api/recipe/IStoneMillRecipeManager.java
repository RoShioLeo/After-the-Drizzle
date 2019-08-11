package roito.afterthedrizzle.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;

public interface IStoneMillRecipeManager
{
    boolean equal(IStoneMillRecipe recipe1, IStoneMillRecipe recipe2);

    void add(IStoneMillRecipe recipe);

    void remove(IStoneMillRecipe recipe);

    void remove(FluidStack inputFluid, NonNullList<ItemStack> inputStacks);

    void removeAll();

    Collection<IStoneMillRecipe> getRecipes();

    IStoneMillRecipe getRecipe(FluidStack inputFluid, ItemStack inputStack);

    IStoneMillRecipe getRecipe(FluidStack inputFluid, NonNullList<ItemStack> inputStacks);
}
