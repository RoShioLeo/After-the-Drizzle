package roito.afterthedrizzle.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public final class MatchHelper
{
    public static boolean containsMatch(boolean strict, NonNullList<ItemStack> inputs, @Nonnull ItemStack target)
    {
        for (ItemStack input : inputs)
        {
            if (OreDictionary.itemMatches(input, target, strict))
            {
                return true;
            }
        }
        return false;
    }
}
