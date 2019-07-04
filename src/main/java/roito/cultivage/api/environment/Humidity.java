package roito.cultivage.api.environment;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.cultivage.Cultivage;
import roito.cultivage.common.config.ConfigMain;

public enum Humidity
{
	ARID,
	DRY,
	AVERAGE,
	MOIST,
	HUMID;

	public int getId()
	{
		return this.ordinal() + 1;
	}

	public String getName()
	{
		return this.toString().toLowerCase();
	}

	@SideOnly(Side.CLIENT)
	public String getTranslation()
	{
		return I18n.format(Cultivage.MODID + ".environment.humidity." + getName());
	}

	public int getDryingTicks()
	{
		switch (this)
		{
			case ARID:
				return (int) (ConfigMain.general.dryingBasicTime * 0.5F);
			case DRY:
				return (int) (ConfigMain.general.dryingBasicTime * 0.75F);
			case AVERAGE:
				return ConfigMain.general.dryingBasicTime;
			case MOIST:
				return (int) (ConfigMain.general.dryingBasicTime * 1.25F);
			default:
				return (int) (ConfigMain.general.dryingBasicTime * 1.5F);
		}
	}

	public int getFermentationTicks()
	{
		switch (this)
		{
			case ARID:
				return (int) (ConfigMain.general.fermentationBasicTime * 0.5F);
			case DRY:
				return (int) (ConfigMain.general.fermentationBasicTime * 0.75F);
			case AVERAGE:
				return ConfigMain.general.fermentationBasicTime;
			case MOIST:
				return (int) (ConfigMain.general.fermentationBasicTime * 1.25F);
			default:
				return (int) (ConfigMain.general.fermentationBasicTime * 1.5F);
		}
	}

	public static Humidity getHumid(Rainfall r, Temperature t)
	{
		int rOrder = r.ordinal();
		int tOrder = t.ordinal();
		int level = Math.max(0, rOrder - Math.abs(rOrder - tOrder) / 2);
		return Humidity.values()[level];
	}

	public static Humidity getHumid(float r, float t)
	{
		return Humidity.getHumid(Rainfall.getRainfallLevel(r), Temperature.getTemperatureLevel(t));
	}
}
