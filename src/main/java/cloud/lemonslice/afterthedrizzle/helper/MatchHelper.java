package cloud.lemonslice.afterthedrizzle.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class MatchHelper
{
    public static boolean containsMatch(ItemStack input, @Nonnull ItemStack target)
    {
        if (input.isEmpty() && !target.isEmpty() || !input.isEmpty() && target.isEmpty())
        {
            return false;
        }
        return target.getItem() == input.getItem();
    }

    public static boolean containsMatch(ItemStack input, @Nonnull ResourceLocation tagId)
    {
        if (input.isEmpty())
        {
            return false;
        }
        Item inputItem = input.getItem();
        return ItemTags.getCollection().getOrCreate(tagId).contains(inputItem);
    }
}
