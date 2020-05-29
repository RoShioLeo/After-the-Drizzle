package roito.afterthedrizzle.common.environment.solar;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import roito.afterthedrizzle.helper.ColorHelper;

public enum SolarTerms
{
    // Spring Solar Terms
    BEGINNING_OF_SPRING(0x0, 0.0F, -0.25F),
    RAIN_WATER(0x1d953f, 0.1F, -0.15F),
    INSECTS_AWAKENING(0x1d953f, 0.22F, -0.1F),
    SPRING_EQUINOX(0x1d953f, 0.35F, -0.05F),
    FRESH_GREEN(0x1d953f, 0.47F, 0.0F),
    GRAIN_RAIN(0x1d953f, 0.6F, 0.05F),

    // Summer Solar Terms
    BEGINNING_OF_SUMMER(0x1d953f, 0.7F, 0.1F),
    LESSER_FULLNESS(ColorHelper.simplyMixColor(0x1d953f, 0.9F, 0x8c531b, 0.1F), 0.8F, 0.15F),
    GRAIN_IN_EAR(ColorHelper.simplyMixColor(0x1d953f, 0.8F, 0x8c531b, 0.2F), 0.8F, 0.15F),
    SUMMER_SOLSTICE(ColorHelper.simplyMixColor(0x1d953f, 0.7F, 0x8c531b, 0.3F), 0.8F, 0.2F),
    LESSER_HEAT(ColorHelper.simplyMixColor(0x1d953f, 0.6F, 0x8c531b, 0.4F), 0.8F, 0.2F),
    GREATER_HEAT(ColorHelper.simplyMixColor(0x1d953f, 0.5F, 0x8c531b, 0.5F), 0.8F, 0.25F),

    // Autumn Solar Terms
    BEGINNING_OF_AUTUMN(ColorHelper.simplyMixColor(0x1d953f, 0.38F, 0x8c531b, 0.62F), 0.75F, 0.15F),
    END_OF_HEAT(ColorHelper.simplyMixColor(0x1d953f, 0.25F, 0x8c531b, 0.75F), 0.7F, 0.1F),
    WHITE_DEW(ColorHelper.simplyMixColor(0x1d953f, 0.12F, 0x8c531b, 0.87F), 0.7F, 0.05F),
    AUTUMNAL_EQUINOX(0x8c531b, 0.6F, 0.0F),
    COLD_DEW(0x8c531b, 0.48F, -0.1F),
    FIRST_FROST(0x8c531b, 0.35F, -0.2F),

    // Winter Solar Terms
    BEGINNING_OF_WINTER(0x8c531b, 0.22F, -0.3F),
    LIGHT_SNOW(0x8c531b, 0.1F, -0.35F),
    HEAVY_SNOW(0x0, 0.0F, -0.35F),
    WINTER_SOLSTICE(0x0, 0.0F, -0.4F),
    LESSER_COLD(0x0, 0.0F, -0.45F),
    GREATER_COLD(0x0, 0.0F, -0.4F);

    private final int mixColorIn;
    private final float alpha;
    private final float temperature;

    SolarTerms(int mixColorIn, float alpha, float temperature)
    {
        this.mixColorIn = mixColorIn;
        this.alpha = alpha;
        this.temperature = temperature;
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
}
