package roito.cultivage.registry;

import net.minecraft.item.Item;
import roito.cultivage.Cultivage;
import roito.cultivage.common.item.ItemRainGauge;
import roito.cultivage.common.item.ItemThermometer;
import roito.silveroakoutpost.register.RegisterType;
import roito.silveroakoutpost.register.annotation.AutoRegistry;
import roito.silveroakoutpost.register.annotation.RegisterInfo;

@AutoRegistry(modid = Cultivage.MODID, registerType = RegisterType.ITEM)
public final class ItemsRegistry
{
	@RegisterInfo("thermometer")
	public static final Item ITEM_THERMOMETER = new ItemThermometer();

	@RegisterInfo("rain_gauge")
	public static final Item ITEM_RAIN_GAUGE = new ItemRainGauge();
}
