package roito.afterthedrizzle.helper;

import roito.afterthedrizzle.common.environment.ApparentTemperature;
import roito.afterthedrizzle.common.environment.Humidity;

public final class EnvHelper
{
    public static int getFluidApparentTemp(int k)
    {
        if (k < 273)
        {
            return ApparentTemperature.FREEZING.getMiddle();
        }
        else if (k < 288)
        {
            return ApparentTemperature.COLD.getMiddle();
        }
        else if (k < 303)
        {
            return ApparentTemperature.COOL.getMiddle();
        }
        else if (k < 318)
        {
            return ApparentTemperature.WARM.getMiddle();
        }
        else if (k < 333)
        {
            return ApparentTemperature.HOT.getMiddle();
        }
        else
        {
            return ApparentTemperature.HEAT.getMiddle();
        }
    }

    public static float getEnvDailyTemp(float biomeTemp, Humidity humidity, long ticks, boolean isRaining)
    {
        int t = Math.toIntExact(ticks / 100 % 240);
        int id = humidity.getId();
        double sd = (6 - id) * (6 - id) * 0.02D * Math.abs((double) biomeTemp) * (isRaining ? 0.5D : 1);
        double standard = (double) biomeTemp - 0.8D * sd;
        double actual = 8.3993798184E-13 * t * t * t * t * t * t - 7.0737390945E-10 * t * t * t * t * t + 2.2888659134E-07 * t * t * t * t - 3.4405044644E-05 * t * t * t + 2.1378684197E-03 * t * t - 1.7525679385E-02 * t - 9.5209121237E-01;
        return (float) (sd * actual + standard);
    }

}
