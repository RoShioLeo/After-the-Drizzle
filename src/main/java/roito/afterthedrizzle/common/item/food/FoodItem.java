package roito.afterthedrizzle.common.item.food;

import net.minecraft.item.Food;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.NormalItem;

public class FoodItem extends NormalItem
{
    public FoodItem(String name, Properties properties)
    {
        super(name, properties.group(AfterTheDrizzle.GROUP_FOOD));
    }

    public FoodItem(String name, Food food)
    {
        this(name, new Properties().food(food));
    }
}
