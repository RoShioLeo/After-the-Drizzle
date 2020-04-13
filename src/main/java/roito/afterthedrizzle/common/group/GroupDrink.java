package roito.afterthedrizzle.common.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.ItemsRegistry;

public class GroupDrink extends ItemGroup
{
    public GroupDrink()
    {
        super(AfterTheDrizzle.MODID + ".drink");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemsRegistry.TEA_LEAVES);
    }
}
