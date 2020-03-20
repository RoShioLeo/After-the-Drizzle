package roito.afterthedrizzle.common.recipe.normal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import roito.afterthedrizzle.helper.MatchHelper;
import roito.afterthedrizzle.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public class SingleInRecipe
{
    private final NonNullList<ItemStack> inputs;
    private final ItemStack output;

    public SingleInRecipe(NonNullList<ItemStack> inputs, ItemStack output)
    {
        this.inputs = inputs;
        this.output = output;
    }

    public SingleInRecipe(String oreDicName, ItemStack output)
    {
        this.inputs = OreDictionary.getOres(oreDicName);
        this.output = output;
    }

    public SingleInRecipe(ItemStack input, ItemStack output)
    {
        this.inputs = NonNullListHelper.createNonNullList(input);
        this.output = output;
    }

    public NonNullList<ItemStack> getInputs()
    {
        return inputs;
    }

    public ItemStack getOutput()
    {
        return output.copy();
    }

    public boolean isTheSameInput(@Nonnull ItemStack input)
    {
        return !this.output.isEmpty() && MatchHelper.containsMatch(false, inputs, input);
    }
}
