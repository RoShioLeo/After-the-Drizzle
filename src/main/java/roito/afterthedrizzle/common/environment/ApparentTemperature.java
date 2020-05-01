package roito.afterthedrizzle.common.environment;

public enum ApparentTemperature
{
    UNDER_FREEZING,
    FREEZING,
    COLD,
    COOL,
    WARM,
    HOT,
    HEAT,
    OVER_HEAT;

    public int getMin()
    {
        return (ordinal() - 4) * 8;
    }

    public int getMax()
    {
        return (ordinal() - 3) * 8 - 1;
    }

    public int getMiddle()
    {
        if (ordinal() <= 3)
        {
            return getMin() + 4;
        }
        else
        {
            return getMax() - 4;
        }
    }

    public boolean isInRange(int temp)
    {
        return getMin() <= temp && temp <= getMax();
    }

    public static ApparentTemperature getTemperature(int temp)
    {
        for (ApparentTemperature t : values())
        {
            if (t.isInRange(temp))
            {
                return t;
            }
        }
        return WARM;
    }

    public static ApparentTemperature getApparentTemp(Temperature env)
    {
        return values()[env.getId()];
    }
}
