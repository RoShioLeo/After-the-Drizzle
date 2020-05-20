package roito.afterthedrizzle.common.environment.temperature;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum ApparentTemperature
{
    UNDER_FREEZING(TextFormatting.DARK_BLUE),
    FREEZING(TextFormatting.DARK_BLUE),
    COLD(TextFormatting.BLUE),
    COOL(TextFormatting.AQUA),
    WARM(TextFormatting.GOLD),
    HOT(TextFormatting.RED),
    HEAT(TextFormatting.DARK_RED),
    OVER_HEAT(TextFormatting.DARK_RED);

    private final TextFormatting color;

    ApparentTemperature(TextFormatting color)
    {
        this.color = color;
    }

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

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public ITextComponent getTranslation()
    {
        return new TranslationTextComponent("info.afterthedrizzle.environment.temperature." + getName()).applyTextStyle(color);
    }

    public TextFormatting getColor()
    {
        return color;
    }

    public boolean isInRange(int temp)
    {
        return getMin() <= temp && temp <= getMax();
    }

    public int getIndex()
    {
        if (ordinal() <= 1)
        {
            return 1;
        }
        else if (ordinal() >= 6)
        {
            return 6;
        }
        else
            return ordinal();
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
