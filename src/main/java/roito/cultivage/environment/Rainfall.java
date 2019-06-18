package roito.cultivage.environment;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.cultivage.Cultivage;

public enum Rainfall
{
	RARE(Float.NEGATIVE_INFINITY, 0.1F),
	SCARCE(0.1F, 0.3F),
	MODERATE(0.3F, 0.6F),
	ADEQUATE(0.6F, 0.8F),
	ABUNDANT(0.8F, Float.POSITIVE_INFINITY);

	private float min;
	private float max;

	Rainfall(float min, float max)
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

	public boolean isInRainfall(float rainfall)
	{
		return min <= rainfall && rainfall < max;
	}

	@SideOnly(Side.CLIENT)
	public String getTranslation()
	{
		return I18n.format(Cultivage.MODID + ".environment.rainfall" + getName());
	}
}
