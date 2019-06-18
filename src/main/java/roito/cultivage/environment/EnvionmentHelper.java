package roito.cultivage.environment;

public final class EnvionmentHelper
{
	public static Temperature getTemperatureLevel(float temp)
	{
		for (Temperature t : Temperature.values())
		{
			if (t.isInTemperature(temp))
			{
				return t;
			}
		}
		return Temperature.FREEZING;
	}

	public static Rainfall getRainfallLevel(float rainfall)
	{
		for (Rainfall r : Rainfall.values())
		{
			if (r.isInRainfall(rainfall))
			{
				return r;
			}
		}
		return Rainfall.RARE;
	}
}
