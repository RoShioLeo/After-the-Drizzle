package roito.afterthedrizzle.registry;

import net.minecraft.item.Item;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.ItemAsh;
import roito.afterthedrizzle.common.item.ItemCAFood;
import roito.afterthedrizzle.common.item.ItemDrinkMaker;
import roito.afterthedrizzle.common.item.ItemNormal;

public final class ItemsRegistry extends RegistryModule
{

    public static final Item THERMOMETER = new ItemNormal("thermometer", 64, AfterTheDrizzle.TAB_ENV);
    public static final Item RAIN_GAUGE = new ItemNormal("rain_gauge", 64, AfterTheDrizzle.TAB_ENV);

    public static final Item BEEF_JERKY = new ItemCAFood("beef_jerky", 10, 1.6F, true)
    {
        @Override
        public String[] getOreDicts()
        {
            return new String[]{"foodJerky"};
        }
    };
    public static final Item PORK_JERKY = new ItemCAFood("pork_jerky", 10, 1.6F, true)
    {
        @Override
        public String[] getOreDicts()
        {
            return new String[]{"foodJerky"};
        }
    };
    public static final Item CHICKEN_JERKY = new ItemCAFood("chicken_jerky", 8, 1.2F, true)
    {
        @Override
        public String[] getOreDicts()
        {
            return new String[]{"foodJerky"};
        }
    };
    public static final Item RABBIT_JERKY = new ItemCAFood("rabbit_jerky", 7, 1.2F, true)
    {
        @Override
        public String[] getOreDicts()
        {
            return new String[]{"foodJerky"};
        }
    };
    public static final Item MUTTON_JERKY = new ItemCAFood("mutton_jerky", 8, 1.6F, true)
    {
        @Override
        public String[] getOreDicts()
        {
            return new String[]{"foodJerky"};
        }
    };
    public static final Item DRIED_CARROT = new ItemCAFood("dried_carrot", 5, 1.2F, false);
    public static final Item DRIED_BEETROOT = new ItemCAFood("dried_beetroot", 3, 1.2F, false);

    public static final Item ASH = new ItemAsh();

    public static final Item DRINK_MAKER = new ItemDrinkMaker();

    public ItemsRegistry()
    {
        super(true);
    }
}
