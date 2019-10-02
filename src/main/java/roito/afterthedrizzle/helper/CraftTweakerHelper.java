package roito.afterthedrizzle.helper;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Optional;

import java.util.List;

public final class CraftTweakerHelper
{
    @Optional.Method(modid = "crafttweaker")
    public final static NonNullList<ItemStack> getItemStacks(IIngredient input)
    {
        if (input == null)
        {
            return NonNullList.create();
        }
        else
        {
            List<IItemStack> list = input.getItems();
            NonNullList<ItemStack> result = NonNullList.create();
            for (IItemStack in : list)
            {
                result.add(CraftTweakerMC.getItemStack(in));
            }
            return result;
        }
    }
}
