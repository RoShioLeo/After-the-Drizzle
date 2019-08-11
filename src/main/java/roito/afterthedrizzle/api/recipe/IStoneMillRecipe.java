package roito.afterthedrizzle.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public interface IStoneMillRecipe
{
    NonNullList<ItemStack> getInputStacks();

    FluidStack getInputFluid();

    NonNullList<ItemStack> getOutputStacks();

    FluidStack getOutputFluid();

    boolean canWork(FluidStack fluidStack, ItemStack itemStack);

    int getFluidAmount();

    boolean isTheSameInputItem(@Nonnull ItemStack input);

    boolean isTheSameInputFluid(@Nonnull FluidStack input);
}
