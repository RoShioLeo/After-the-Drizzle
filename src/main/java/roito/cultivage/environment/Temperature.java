package roito.cultivage.environment;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.cultivage.Cultivage;

public enum Temperature
{
	FREEZING(Float.NEGATIVE_INFINITY, 0.15F),
	COLD(0.15F, 0.3F),
	COOL(0.3F, 0.6F),
	WARM(0.6F, 0.8F),
	HOT(0.8F, 1.5F),
	HEAT(1.5F, Float.POSITIVE_INFINITY);

	private float min;
	private float max;

	Temperature(float min, float max)
	{
		this.min = min;
		this.max = max;
	}

	public int getId()
	{
		return this.ordinal() + 1;
	}

	public String getName()
	{
		return this.toString().toLowerCase();
	}

	public boolean isInTemperature(float temp)
	{
		return min <= temp && temp < max;
	}

	@SideOnly(Side.CLIENT)
	public String getTranslation()
	{
		return I18n.format(Cultivage.MODID + ".environment.temperature" + getName());
	}
}
