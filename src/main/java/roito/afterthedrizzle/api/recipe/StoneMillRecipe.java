package roito.afterthedrizzle.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import roito.silveroakoutpost.helper.MatchHelper;
import roito.silveroakoutpost.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public class StoneMillRecipe implements IStoneMillRecipe
{
    private final NonNullList<ItemStack> inputs;
    private final FluidStack inputFluid;
    private final NonNullList<ItemStack> outputs;
    private final FluidStack outputFluid;

    public StoneMillRecipe(FluidStack inputFluid, NonNullList<ItemStack> inputs, NonNullList<ItemStack> outputs, FluidStack outputFluid)
    {
        this.inputFluid = inputFluid;
        this.inputs = inputs;
        this.outputs = outputs;
        this.outputFluid = outputFluid;
    }

    public StoneMillRecipe(FluidStack inputFluid, String oreDicName, NonNullList<ItemStack> outputs, FluidStack outputFluid)
    {
        this(inputFluid, OreDictionary.getOres(oreDicName), outputs, outputFluid);
    }

    public StoneMillRecipe(FluidStack inputFluid, ItemStack input, NonNullList<ItemStack> outputs, FluidStack outputFluid)
    {
        this(inputFluid, NonNullListHelper.createNonNullList(input), outputs, outputFluid);
    }

    public StoneMillRecipe(NonNullList<ItemStack> inputs, NonNullList<ItemStack> outputs)
    {
        this(new FluidStack(FluidRegistry.WATER, 0), inputs, outputs, new FluidStack(FluidRegistry.WATER, 0));
    }

    public StoneMillRecipe(String oreDicName, NonNullList<ItemStack> outputs)
    {
        this(OreDictionary.getOres(oreDicName), outputs);
    }

    public StoneMillRecipe(ItemStack input, NonNullList<ItemStack> outputs)
    {
        this(NonNullListHelper.createNonNullList(input), outputs);
    }

    @Override
    public NonNullList<ItemStack> getInputStacks()
    {
        NonNullList<ItemStack> list = NonNullList.create();
        for (ItemStack itemStack : inputs)
        {
            list.add(itemStack.copy());
        }
        return list;
    }

    @Override
    public FluidStack getInputFluid()
    {
        return inputFluid.copy();
    }

    @Override
    public NonNullList<ItemStack> getOutputStacks()
    {
        NonNullList<ItemStack> list = NonNullList.create();
        for (ItemStack itemStack : outputs)
        {
            list.add(itemStack.copy());
        }
        return list;
    }

    @Override
    public FluidStack getOutputFluid()
    {
        return outputFluid.copy();
    }

    @Override
    public boolean canWork(FluidStack fluidStack, ItemStack itemStack)
    {
        if (this.getFluidAmount() == 0)
        {
            return isTheSameInputItem(itemStack);
        }
        else if (this.isTheSameInputFluid(fluidStack))
        {
            return isTheSameInputItem(itemStack);
        }
        else return false;
    }

    @Override
    public int getFluidAmount()
    {
        if (this.inputFluid != null)
        {
            return this.inputFluid.amount;
        }
        return 0;
    }

    @Override
    public boolean isTheSameInputItem(@Nonnull ItemStack input)
    {
        return !this.outputs.isEmpty() && MatchHelper.containsMatch(false, this.inputs, input);
    }

    @Override
    public boolean isTheSameInputFluid(@Nonnull FluidStack input)
    {
        return this.inputFluid.equals(input) && this.inputFluid.amount <= input.amount;
    }
}
