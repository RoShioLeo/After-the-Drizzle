package roito.afterthedrizzle.common.item;

import net.minecraft.item.Item;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.food.FoodItem;
import roito.afterthedrizzle.common.item.food.NormalFoods;
import roito.afterthedrizzle.registry.RegistryModule;

public final class ItemsRegistry extends RegistryModule
{
    public static final Item ASH = new AshItem();

    public static final Item DRIED_BEETROOT = new FoodItem("dried_beetroot", NormalFoods.DRIED_BEETROOT);
    public static final Item DRIED_CARROT = new FoodItem("dried_carrot", NormalFoods.DRIED_CARROT);
    public static final Item BEEF_JERKY = new FoodItem("beef_jerky", NormalFoods.BEEF_JERKY);
    public static final Item PORK_JERKY = new FoodItem("pork_jerky", NormalFoods.PORK_JERKY);
    public static final Item CHICKEN_JERKY = new FoodItem("chicken_jerky", NormalFoods.CHICKEN_JERKY);
    public static final Item RABBIT_JERKY = new FoodItem("rabbit_jerky", NormalFoods.RABBIT_JERKY);
    public static final Item MUTTON_JERKY = new FoodItem("mutton_jerky", NormalFoods.MUTTON_JERKY);
    public static final Item BAMBOO_PLANK = new NormalItem("bamboo_plank",new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));
}
