package roito.afterthedrizzle.client.color.season;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.level.ColorResolver;
import roito.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import roito.afterthedrizzle.common.environment.solar.SolarTerms;
import roito.afterthedrizzle.helper.ColorHelper;

public class BiomeColorsHandler
{
    public static final ColorResolver GRASS_COLOR = (biome, posX, posZ) ->
    {
        int originColor = biome.getGrassColor(posX, posZ);
        if (Minecraft.getInstance().world != null)
        {
            return Minecraft.getInstance().world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data ->
            {
                SolarTerms solar = SolarTerms.get(data.getSolarTermIndex());
                return ColorHelper.simplyMixColor(solar.getColorInfo().getColor(), solar.getColorInfo().getAlpha(), originColor, 1.0F - solar.getColorInfo().getAlpha());
            }).orElse(originColor);
        }
        else return -1;
    };

    public static final ColorResolver FOLIAGE_COLOR = (biome, posX, posZ) ->
    {
        double temperature = MathHelper.clamp(biome.getDefaultTemperature(), 0.0F, 1.0F);
        double humidity = MathHelper.clamp(biome.getDownfall(), 0.0F, 1.0F);
        int originColor = FoliageColors.get(temperature, humidity);
        if (Minecraft.getInstance().world != null)
        {
            return Minecraft.getInstance().world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data ->
            {
                SolarTerms solar = SolarTerms.get(data.getSolarTermIndex());
                if (solar.getColorInfo().getAlpha() == 0.0F)
                {
                    return originColor;
                }
                else
                    return ColorHelper.simplyMixColor(solar.getColorInfo().getColor(), solar.getColorInfo().getAlpha(), originColor, 1.0F - solar.getColorInfo().getAlpha());
            }).orElse(originColor);
        }
        else return -1;
    };
}
