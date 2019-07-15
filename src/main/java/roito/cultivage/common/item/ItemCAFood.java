package roito.cultivage.common.item;

import net.minecraft.item.ItemFood;
import roito.cultivage.Cultivage;

public class ItemCAFood extends ItemFood
{

	public ItemCAFood(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
		this.setCreativeTab(Cultivage.TAB_FOOD);
	}

	public ItemCAFood(int amount, boolean isWolfFood)
	{
		super(amount, isWolfFood);
		this.setCreativeTab(Cultivage.TAB_FOOD);
	}
}
