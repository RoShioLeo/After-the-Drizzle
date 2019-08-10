package roito.afterthedrizzle.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.registry.ItemsRegistry;

public class TabEnv extends CreativeTabs
{
    public TabEnv()
    {
        super(AfterTheDrizzle.MODID + ".environment");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemsRegistry.RAIN_GAUGE);
    }
}
