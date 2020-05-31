package roito.afterthedrizzle.common.environment.solar;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import roito.afterthedrizzle.helper.ColorHelper;

public enum SolarTerms
{
    // Spring Solar Terms
    BEGINNING_OF_SPRING(0x0, 0.0F, -0.25F, 10500),
    RAIN_WATER(0x1d953f, 0.1F, -0.15F, 11000),
    INSECTS_AWAKENING(0x1d953f, 0.22F, -0.1F, 11500),
    SPRING_EQUINOX(0x1d953f, 0.35F, -0.05F, 12000),
    FRESH_GREEN(0x1d953f, 0.47F, 0.0F, 12500),
    GRAIN_RAIN(0x1d953f, 0.6F, 0.05F, 13000),

    // Summer Solar Terms
    BEGINNING_OF_SUMMER(0x1d953f, 0.7F, 0.1F, 13500),
    LESSER_FULLNESS(ColorHelper.simplyMixColor(0x1d953f, 0.9F, 0x8c531b, 0.1F), 0.8F, 0.15F, 14000),
    GRAIN_IN_EAR(ColorHelper.simplyMixColor(0x1d953f, 0.8F, 0x8c531b, 0.2F), 0.8F, 0.15F, 14500),
    SUMMER_SOLSTICE(ColorHelper.simplyMixColor(0x1d953f, 0.7F, 0x8c531b, 0.3F), 0.8F, 0.2F, 15000),
    LESSER_HEAT(ColorHelper.simplyMixColor(0x1d953f, 0.6F, 0x8c531b, 0.4F), 0.8F, 0.2F, 14500),
    GREATER_HEAT(ColorHelper.simplyMixColor(0x1d953f, 0.5F, 0x8c531b, 0.5F), 0.8F, 0.25F, 14000),

    // Autumn Solar Terms
    BEGINNING_OF_AUTUMN(ColorHelper.simplyMixColor(0x1d953f, 0.38F, 0x8c531b, 0.62F), 0.75F, 0.15F, 13500),
    END_OF_HEAT(ColorHelper.simplyMixColor(0x1d953f, 0.25F, 0x8c531b, 0.75F), 0.7F, 0.1F, 13000),
    WHITE_DEW(ColorHelper.simplyMixColor(0x1d953f, 0.12F, 0x8c531b, 0.87F), 0.7F, 0.05F, 12500),
    AUTUMNAL_EQUINOX(0x8c531b, 0.6F, 0.0F, 12000),
    COLD_DEW(0x8c531b, 0.48F, -0.1F, 11500),
    FIRST_FROST(0x8c531b, 0.35F, -0.2F, 11000),

    // Winter Solar Terms
    BEGINNING_OF_WINTER(0x8c531b, 0.22F, -0.3F, 10500),
    LIGHT_SNOW(0x8c531b, 0.1F, -0.35F, 10000),
    HEAVY_SNOW(0x0, 0.0F, -0.35F, 9500),
    WINTER_SOLSTICE(0x0, 0.0F, -0.4F, 9000),
    LESSER_COLD(0x0, 0.0F, -0.45F, 9500),
    GREATER_COLD(0x0, 0.0F, -0.4F, 10000);

    private final int mixColorIn;
    private final float alpha;
    private final float temperature;
    private final int dayTime;

    SolarTerms(int mixColorIn, float alpha, float temperature, int dayTime)
    {
        this.mixColorIn = mixColorIn;
        this.alpha = alpha;
        this.temperature = temperature;
        this.dayTime = dayTime;
    }

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public ITextComponent getTranslation()
    {
        return new TranslationTextComponent("info.afterthedrizzle.environment.solar_term." + getName());
    }

    public int getColor()
    {
        return mixColorIn;
    }

    public float getAlpha()
    {
        return alpha;
    }

    public static SolarTerms get(int index)
    {
        return values()[index];
    }

    public float getTemperatureChange()
    {
        return temperature;
    }

    public int getDayTime()
    {
        return dayTime;
    }
}
