package roito.afterthedrizzle.registry;

import net.minecraft.item.Item;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.ItemAsh;
import roito.afterthedrizzle.common.item.ItemCAFood;
import roito.silveroakoutpost.item.SilveroakItem;
import roito.silveroakoutpost.register.RegisterType;
import roito.silveroakoutpost.register.annotation.AutoRegistry;
import roito.silveroakoutpost.register.annotation.OreDictVariant;
import roito.silveroakoutpost.register.annotation.RegisterInfo;

@AutoRegistry(modid = AfterTheDrizzle.MODID, registerType = RegisterType.ITEM)
public final class ItemsRegistry
{
    @RegisterInfo("thermometer")
    public static final Item THERMOMETER = new SilveroakItem(64, AfterTheDrizzle.TAB_ENV);
    @RegisterInfo("rain_gauge")
    public static final Item RAIN_GAUGE = new SilveroakItem(64, AfterTheDrizzle.TAB_ENV);

    @OreDictVariant("foodJerky")
    @RegisterInfo("beef_jerky")
    public static final Item BEEF_JERKY = new ItemCAFood(10, 1.6F, true);
    @OreDictVariant("foodJerky")
    @RegisterInfo("pork_jerky")
    public static final Item PORK_JERKY = new ItemCAFood(10, 1.6F, true);
    @OreDictVariant("foodJerky")
    @RegisterInfo("chicken_jerky")
    public static final Item CHICKEN_JERKY = new ItemCAFood(8, 1.2F, true);
    @OreDictVariant("foodJerky")
    @RegisterInfo("rabbit_jerky")
    public static final Item RABBIT_JERKY = new ItemCAFood(7, 1.2F, true);
    @OreDictVariant("foodJerky")
    @RegisterInfo("mutton_jerky")
    public static final Item MUTTON_JERKY = new ItemCAFood(8, 1.6F, true);
    @RegisterInfo("dried_carrot")
    public static final Item DRIED_CARROT = new ItemCAFood(5, 1.2F, false);
    @RegisterInfo("dried_beetroot")
    public static final Item DRIED_BEETROOT = new ItemCAFood(3, 1.2F, false);

    @RegisterInfo("ash")
    public static final Item ASH = new ItemAsh();
}
