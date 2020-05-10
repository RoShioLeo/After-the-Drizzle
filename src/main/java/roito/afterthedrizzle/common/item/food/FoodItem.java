package roito.afterthedrizzle.common.item.food;

import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import roito.afterthedrizzle.common.item.NormalItem;

public class FoodItem extends NormalItem
{
    public FoodItem(String name, Properties properties)
    {
        super(name, properties.group(ItemGroup.FOOD));
    }

    public FoodItem(String name, Food food)
    {
        this(name, new Properties().food(food));
    }
}
