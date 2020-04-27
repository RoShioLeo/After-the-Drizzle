package roito.afterthedrizzle.common.environment;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import roito.afterthedrizzle.common.config.NormalConfig;

public enum Humidity
{
    ARID(TextFormatting.RED),
    DRY(TextFormatting.GOLD),
    AVERAGE(TextFormatting.WHITE),
    MOIST(TextFormatting.BLUE),
    HUMID(TextFormatting.DARK_GREEN);

    private final TextFormatting color;

    Humidity(TextFormatting color)
    {
        this.color = color;
    }

    public int getId()
    {
        return this.ordinal() + 1;
    }

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public ITextComponent getTranslation()
    {
        return new TranslationTextComponent("info.afterthedrizzle.environment.humidity." + getName()).applyTextStyle(color);
    }

    public int getOutdoorDryingTicks()
    {
        int basicTicks = NormalConfig.dryingOutdoorsBasicTime.get();
        switch (this)
        {
            case ARID:
                return (int) (basicTicks * 0.5F);
            case DRY:
                return (int) (basicTicks * 0.75F);
            case AVERAGE:
                return basicTicks;
            case MOIST:
                return (int) (basicTicks * 1.25F);
            default:
                return (int) (basicTicks * 1.5F);
        }
    }

    public int getFermentationTicks()
    {
        int basicTicks = NormalConfig.fermentationBasicTime.get();
        switch (this)
        {
            case ARID:
                return (int) (basicTicks * 1.5F);
            case DRY:
                return (int) (basicTicks * 1.25F);
            case AVERAGE:
                return basicTicks;
            case MOIST:
                return (int) (basicTicks * 0.75F);
            default:
                return (int) (basicTicks * 0.5F);
        }
    }

    public static Humidity getHumid(Rainfall rainfall, Temperature temperature)
    {
        int rOrder = rainfall.ordinal();
        int tOrder = temperature.ordinal();
        int level = Math.max(0, rOrder - Math.abs(rOrder - tOrder) / 2);
        return Humidity.values()[level];
    }

    public static Humidity getHumid(float rainfall, float temperature)
    {
        return Humidity.getHumid(Rainfall.getRainfallLevel(rainfall), Temperature.getTemperatureLevel(temperature));
    }
}
