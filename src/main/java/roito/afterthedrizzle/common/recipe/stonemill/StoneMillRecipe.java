package roito.afterthedrizzle.common.recipe.stonemill;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import roito.afterthedrizzle.helper.NonNullListHelper;

public class StoneMillRecipe
{
    private final NonNullList<ItemStack> inputs, outputs;
    private final FluidStack inputFluid, outputFluid;

    private StoneMillRecipe(FluidStack inputFluid, NonNullList<ItemStack> inputs, FluidStack outputFluid, NonNullList<ItemStack> outputs)
    {
        this.inputFluid = inputFluid == null ? new FluidStack(FluidRegistry.WATER, 0) : inputFluid;
        this.inputs = inputs;
        this.outputFluid = outputFluid == null ? new FluidStack(FluidRegistry.WATER, 0) : outputFluid;
        this.outputs = outputs;
    }

    public StoneMillRecipe(FluidStack inputFluid, String inputs, FluidStack outputFluid, NonNullList<ItemStack> outputs)
    {
        this(inputFluid, OreDictionary.getOres(inputs), outputFluid, outputs);
    }

    public StoneMillRecipe(FluidStack inputFluid, ItemStack input, FluidStack outputFluid, NonNullList<ItemStack> outputs)
    {
        this(inputFluid, NonNullListHelper.createNonNullList(input), outputFluid, outputs);
    }

    private StoneMillRecipe(NonNullList<ItemStack> inputs, FluidStack outputFluid, NonNullList<ItemStack> outputs)
    {
        this(null, inputs, outputFluid, outputs);
    }

    public StoneMillRecipe(String inputs, FluidStack outputFluid, NonNullList<ItemStack> outputs)
    {
        this(OreDictionary.getOres(inputs), outputFluid, outputs);
    }

    public StoneMillRecipe(ItemStack input, FluidStack outputFluid, NonNullList<ItemStack> outputs)
    {
        this(NonNullListHelper.createNonNullList(input), outputFluid, outputs);
    }

    private StoneMillRecipe(NonNullList<ItemStack> inputs, NonNullList<ItemStack> outputs)
    {
        this(null, inputs, null, outputs);
    }

    public StoneMillRecipe(String inputs, NonNullList<ItemStack> outputs)
    {
        this(OreDictionary.getOres(inputs), outputs);
    }

    public StoneMillRecipe(ItemStack input, NonNullList<ItemStack> outputs)
    {
        this(NonNullListHelper.createNonNullList(input), outputs);
    }

    public boolean isEmpty()
    {
        return outputFluid.amount == 0 && outputs.isEmpty();
    }

    public boolean isTheSameInputs(StoneMillRecipe inventoryRecipeIn)
    {
        if (!this.inputFluid.getFluid().equals(inventoryRecipeIn.inputFluid.getFluid()))
        {
            return false;
        }
        else
        {
            for (ItemStack target : this.inputs)
            {
                if (OreDictionary.itemMatches(target, inventoryRecipeIn.inputs.get(0), false))
                {
                    return true;
                }
            }
            return false;
        }
    }
}
