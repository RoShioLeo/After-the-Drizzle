package roito.afterthedrizzle.common.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.common.item.ItemsRegistry;

import static roito.afterthedrizzle.AfterTheDrizzle.MODID;

public class GroupCore extends ItemGroup
{
    public GroupCore()
    {
        super(MODID + ".core");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemsRegistry.TEA_LEAVES);
    }
}
