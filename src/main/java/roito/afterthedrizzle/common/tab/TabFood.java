package roito.afterthedrizzle.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.registry.ItemsRegistry;

public class TabFood extends CreativeTabs
{
    public TabFood()
    {
        super(AfterTheDrizzle.MODID + ".food");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemsRegistry.BEEF_JERKY);
    }
}
