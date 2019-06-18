package roito.cultivage.registry;

import net.minecraft.item.Item;
import roito.cultivage.Cultivage;
import roito.silveroakoutpost.item.SilveroakItem;
import roito.silveroakoutpost.register.RegisterType;
import roito.silveroakoutpost.register.annotation.AutoRegistry;
import roito.silveroakoutpost.register.annotation.RegisterInfo;

@AutoRegistry(modid = Cultivage.MODID, registerType = RegisterType.ITEM)
public final class ItemsRegistry
{
	@RegisterInfo("thermometer")
	public static final Item ITEM_THERMOMETER = new SilveroakItem(64, Cultivage.TAB_ENV);

	@RegisterInfo("rain_gauge")
	public static final Item ITEM_RAIN_GAUGE = new SilveroakItem(64, Cultivage.TAB_ENV);
}
