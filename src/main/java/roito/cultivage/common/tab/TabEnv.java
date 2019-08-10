package roito.cultivage.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.cultivage.Cultivage;
import roito.cultivage.registry.ItemsRegistry;

public class TabEnv extends CreativeTabs
{
    public TabEnv()
    {
        super(Cultivage.MODID + ".environment");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemsRegistry.RAIN_GAUGE);
    }
}
