package roito.cultivage.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.cultivage.Cultivage;

public class TabEnv extends CreativeTabs
{
	public TabEnv()
	{
		super(Cultivage.MODID + ".environment");
	}

	@Override
	public ItemStack createIcon()
	{
		return ItemStack.EMPTY;
	}
}
