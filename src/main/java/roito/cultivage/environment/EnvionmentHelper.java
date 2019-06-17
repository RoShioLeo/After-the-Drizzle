package roito.cultivage.environment;

public final class EnvionmentHelper
{
	public static Temperature getTemperatureLevel(float temp)
	{
		if (temp < 0.15F)
		{
			return Temperature.FREEZING;
		}
		else if (temp < 0.3F)
		{
			return Temperature.COLD;
		}
		else if (temp < 0.6F)
		{
			return Temperature.COOL;
		}
		else if (temp < 0.8F)
		{
			return Temperature.WARM;
		}
		else if (temp < 1.5F)
		{
			return Temperature.HOT;
		}
		else
		{
			return Temperature.HEAT;
		}
	}

	public static Rainfall getRainfallLevel(float rainfall)
	{
		if (rainfall < 0.1F)
		{
			return Rainfall.RARE;
		}
		else if (rainfall < 0.3F)
		{
			return Rainfall.SCARCE;
		}
		else if (rainfall < 0.6F)
		{
			return Rainfall.MODERATE;
		}
		else if (rainfall < 0.8F)
		{
			return Rainfall.ADEQUATE;
		}
		else
		{
			return Rainfall.ABUNDANT;
		}
	}


}
