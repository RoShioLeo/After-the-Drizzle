package roito.afterthedrizzle.api.calendar;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum SolarTerms
{
    BEGINNING_OF_SPRING,
    RAIN_WATER,
    WAKING_OF_INSECTS,
    VERNAL_EQUINOX,
    PURE_BRIGHTNESS,
    GRAIN_BUDDING,
    GRAIN_IN_EAR,
    SUMMER_SOLSTICE,
    SLIGHT_HEAT,
    GREAT_HEAT,
    BEGINNING_OF_AUTUMN,
    LIMIT_OF_HEAT,
    WHITE_DEW,
    AUTUMNAL_EQUINOX,
    COLD_DEW,
    FROST_DESCENT,
    BEGINNING_OF_WINTER,
    SLIGHT_SNOW,
    GREAT_SNOW,
    WINTER_SOLSTICE,
    SLIGHT_COLD,
    GREAT_COLD;

    @SideOnly(Side.CLIENT)
    public String getTranslationKey()
    {
        return I18n.format("afterthedrizzle.solar_terms." + this.toString().toLowerCase() + ".name");
    }
}
