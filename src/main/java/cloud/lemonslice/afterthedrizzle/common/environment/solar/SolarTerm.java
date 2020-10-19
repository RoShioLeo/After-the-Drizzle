package cloud.lemonslice.afterthedrizzle.common.environment.solar;

import cloud.lemonslice.afterthedrizzle.client.color.season.SolarTermColors;
import cloud.lemonslice.afterthedrizzle.common.config.ServerConfig;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.WeatherType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public enum SolarTerm
{
    // Spring Solar Terms
    BEGINNING_OF_SPRING(-0.25F, 10500, 70, 10, 10, 6, 1, 0, 1),
    RAIN_WATER(-0.15F, 11000, 70, 10, 15, 8, 1, 0, 2),
    INSECTS_AWAKENING(-0.1F, 11500, 70, 9, 16, 9, 2, 1, 3),
    SPRING_EQUINOX(-0.05F, 12000, 70, 8, 17, 10, 1, 2, 3),
    FRESH_GREEN(0.0F, 1250070, 70, 8, 18, 10, 2, 2, 2),
    GRAIN_RAIN(0.05F, 13000, 70, 8, 20, 10, 1, 1, 2),

    // Summer Solar Terms
    BEGINNING_OF_SUMMER(0.1F, 13500, 70, 6, 18, 12, 2, 2, 1),
    LESSER_FULLNESS(0.15F, 14000, 72, 4, 12, 15, 4, 4, 0),
    GRAIN_IN_EAR(0.15F, 14500, 72, 4, 12, 15, 4, 4, 0),
    SUMMER_SOLSTICE(0.2F, 15000, 75, 5, 10, 13, 4, 5, 0),
    LESSER_HEAT(0.2F, 14500, 80, 5, 8, 11, 3, 6, 0),
    GREATER_HEAT(0.25F, 14000, 85, 5, 6, 9, 3, 7, 0),

    // Autumn Solar Terms
    BEGINNING_OF_AUTUMN(0.15F, 13500, 80, 5, 6, 8, 2, 4, 0),
    END_OF_HEAT(0.1F, 13000, 75, 8, 8, 8, 1, 2, 1),
    WHITE_DEW(0.05F, 12500, 75, 10, 10, 8, 1, 1, 2),
    AUTUMNAL_EQUINOX(0.0F, 12000, 72, 10, 10, 6, 2, 0, 3),
    COLD_DEW(-0.1F, 11500, 70, 10, 11, 6, 1, 0, 4),
    FIRST_FROST(-0.2F, 11000, 70, 12, 11, 8, 1, 0, 6),

    // Winter Solar Terms
    BEGINNING_OF_WINTER(-0.3F, 10500, 68, 12, 12, 6, 2, 0, 8),
    LIGHT_SNOW(-0.35F, 10000, 66, 12, 12, 6, 3, 0, 6),
    HEAVY_SNOW(-0.35F, 9500, 66, 14, 11, 8, 4, 0, 4),
    WINTER_SOLSTICE(-0.4F, 9000, 66, 16, 11, 10, 3, 0, 2),
    LESSER_COLD(-0.45F, 9500, 68, 14, 11, 11, 3, 0, 1),
    GREATER_COLD(-0.45F, 10000, 70, 12, 10, 8, 2, 0, 1),

    NONE(0.0F, 12000, 70, 10, 7, 6, 4, 1, 2);

    private final float temperature;
    private final int dayTime;

    private final int sunny;
    private final int overcast;
    private final int light_rain;
    private final int normal_rain;
    private final int heavy_rain;
    private final int storm;
    private final int foggy;

    SolarTerm(float temperature, int dayTime, int clear, int overcast, int light_rain, int normal_rain, int heavy_rain, int storm, int foggy)
    {
        this.temperature = temperature;
        this.dayTime = dayTime;
        this.sunny = clear;
        this.overcast = overcast;
        this.light_rain = light_rain;
        this.normal_rain = normal_rain;
        this.heavy_rain = heavy_rain;
        this.storm = storm;
        this.foggy = foggy;
    }

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public ITextComponent getTranslation()
    {
        return new TranslationTextComponent("info.afterthedrizzle.environment.solar_term." + getName());
    }

    public ITextComponent getAlternationText()
    {
        return new TranslationTextComponent("info.afterthedrizzle.environment.solar_term.alternation." + getName()).applyTextStyle(getSeason().getColor());
    }

    public static SolarTerm get(int index)
    {
        return values()[index];
    }

    @OnlyIn(Dist.CLIENT)
    public SolarTermColors getColorInfo()
    {
        return SolarTermColors.values()[this.ordinal()];
    }

    public float getTemperatureChange()
    {
        return temperature;
    }

    public int getDayTime()
    {
        return dayTime;
    }

    public Season getSeason()
    {
        return Season.values()[this.ordinal() / 6];
    }

    public int getTotalWeight()
    {
        return sunny + overcast + light_rain + normal_rain + heavy_rain + storm + foggy;
    }

    public float getClearChance()
    {
        return 1F * sunny / getTotalWeight();
    }

    public float getOvercastChance()
    {
        return 1F * (sunny + overcast) / getTotalWeight();
    }

    public float getLightRainChance()
    {
        return 1F * (sunny + overcast + light_rain) / getTotalWeight();
    }

    public float getNormalRainChance()
    {
        return 1F * (sunny + overcast + light_rain + normal_rain) / getTotalWeight();
    }

    public float getHeavyRainChance()
    {
        return 1F * (sunny + overcast + light_rain + normal_rain + heavy_rain) / getTotalWeight();
    }

    public float getStormChance()
    {
        return 1F * (sunny + overcast + light_rain + normal_rain + heavy_rain + storm) / getTotalWeight();
    }

    public WeatherType createWeather(Random random, int index)
    {
        float f = random.nextFloat();
        if (f < getClearChance())
        {
            return WeatherType.SUNNY;
        }
        else if (f < getLightRainChance())
        {
            return WeatherType.RAINY_LIGHT;
        }
        else if (f < getNormalRainChance())
        {
            return WeatherType.RAINY_NORMAL;
        }
        else if (f < getHeavyRainChance())
        {
            return WeatherType.RAINY_HEAVY;
        }
        else if (f < getStormChance())
        {
            return WeatherType.STORM;
        }
        else
        {
            if (index < 3)
            {
                if (ServerConfig.Weather.enableFoggy.get())
                    return WeatherType.FOGGY;
            }
            return WeatherType.SUNNY;
        }
    }
}
