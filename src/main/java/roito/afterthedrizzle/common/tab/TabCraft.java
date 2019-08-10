package roito.afterthedrizzle.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.registry.BlocksRegistry;

public class TabCraft extends CreativeTabs
{
    public TabCraft()
    {
        super(AfterTheDrizzle.MODID + ".craft");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(BlocksRegistry.FLAT_BASKET);
    }
}
