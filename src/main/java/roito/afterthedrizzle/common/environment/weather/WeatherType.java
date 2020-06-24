package roito.afterthedrizzle.common.environment.weather;

import net.minecraft.util.text.TranslationTextComponent;

public enum WeatherType
{
    NONE(false),
    SUNNY(false),
    OVERCAST(false),
    RAINY_LIGHT(true),
    RAINY_NORMAL(true),
    RAINY_HEAVY(true),
    STORM(true),
    FOGGY(false);

    private final boolean isRainy;

    WeatherType(boolean isRainy)
    {
        this.isRainy = isRainy;
    }

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public TranslationTextComponent getTranslation()
    {
        return new TranslationTextComponent("info.afterthedrizzle.environment.weather." + getName());
    }

    public boolean isRainy()
    {
        return isRainy;
    }
}
