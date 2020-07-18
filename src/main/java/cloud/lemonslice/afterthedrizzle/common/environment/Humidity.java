package cloud.lemonslice.afterthedrizzle.common.environment;

import cloud.lemonslice.afterthedrizzle.common.config.CommonConfig;
import cloud.lemonslice.afterthedrizzle.common.environment.temperature.Temperature;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum Humidity
{
    ARID(TextFormatting.RED, 0.9F),
    DRY(TextFormatting.GOLD, 0.95F),
    AVERAGE(TextFormatting.WHITE, 1.0F),
    MOIST(TextFormatting.BLUE, 1.1F),
    HUMID(TextFormatting.DARK_GREEN, 1.2F);

    private final TextFormatting color;
    private final float tempCoefficient;

    Humidity(TextFormatting color, float tempCoefficient)
    {
        this.color = color;
        this.tempCoefficient = tempCoefficient;
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

    public float getCoefficient()
    {
        return tempCoefficient;
    }

    public int getOutdoorDryingTicks()
    {
        int basicTicks = CommonConfig.Time.dryingOutdoorsBasicTime.get();
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
        int basicTicks = CommonConfig.Time.fermentationBasicTime.get();
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
        return getHumid(Rainfall.getRainfallLevel(rainfall), Temperature.getTemperatureLevel(temperature));
    }
}
