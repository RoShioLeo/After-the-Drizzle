package roito.cultivage.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.cultivage.Cultivage;
import roito.cultivage.registry.BlocksRegistry;

public class TabCraft extends CreativeTabs
{
    public TabCraft()
    {
        super(Cultivage.MODID + ".craft");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(BlocksRegistry.FLAT_BASKET);
    }
}
