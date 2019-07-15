package roito.cultivage.common.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import roito.cultivage.Cultivage;
import roito.cultivage.registry.ItemsRegistry;

public class TabFood extends CreativeTabs
{
	public TabFood()
	{
		super(Cultivage.MODID + ".food");
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(ItemsRegistry.BEEF_JERKY);
	}
}
