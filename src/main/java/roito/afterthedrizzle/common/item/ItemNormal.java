package roito.afterthedrizzle.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import roito.afterthedrizzle.common.block.inter.INormalRegister;

public class ItemNormal extends Item implements INormalRegister
{
    private final String name;

    public ItemNormal(String name, int max, CreativeTabs creativeTab)
    {
        this.setMaxStackSize(max);
        this.setCreativeTab(creativeTab);
        this.name = name;
    }

    @Override
    public String getRegisterInfo()
    {
        return name;
    }
}
