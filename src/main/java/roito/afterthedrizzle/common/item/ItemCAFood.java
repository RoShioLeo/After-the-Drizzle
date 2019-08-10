package roito.afterthedrizzle.common.item;

import net.minecraft.item.ItemFood;
import roito.afterthedrizzle.AfterTheDrizzle;

public class ItemCAFood extends ItemFood
{

    public ItemCAFood(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(AfterTheDrizzle.TAB_FOOD);
    }

    public ItemCAFood(int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
        this.setCreativeTab(AfterTheDrizzle.TAB_FOOD);
    }
}
