package roito.afterthedrizzle.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.List;

public final class NonNullListHelper
{
    public static NonNullList<ItemStack> createNonNullList(List<ItemStack> stackIn)
    {
        NonNullList<ItemStack> list = NonNullList.create();
        list.addAll(stackIn);
        return list;
    }

    public static NonNullList<ItemStack> createNonNullList(ItemStack... stacks)
    {
        return createNonNullList(Arrays.asList(stacks));
    }
}
