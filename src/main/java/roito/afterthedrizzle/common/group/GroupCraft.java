package roito.afterthedrizzle.common.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.BlocksRegistry;

public class GroupCraft extends ItemGroup
{
    public GroupCraft()
    {
        super(AfterTheDrizzle.MODID + ".craft");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(BlocksRegistry.BAMBOO_TRAY_ITEM);
    }
}
