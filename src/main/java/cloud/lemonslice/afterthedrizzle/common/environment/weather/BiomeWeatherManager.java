package cloud.lemonslice.afterthedrizzle.common.environment.weather;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;

public final class BiomeWeatherManager
{
    private final static HashSet<Biome> BIOME_RAIN_SET = new HashSet<>();

    public static void init()
    {
        ForgeRegistries.BIOMES.forEach(biome ->
        {
            if (!biome.getPrecipitation().equals(Biome.RainType.NONE))
            {
                BIOME_RAIN_SET.add(biome);
            }
        });
    }

    public static void setToOvercast()
    {
        BIOME_RAIN_SET.forEach(biome -> biome.precipitation = Biome.RainType.NONE);
    }

    public static void setToNormal()
    {
        BIOME_RAIN_SET.forEach(biome ->
        {
            if (biome.getDefaultTemperature() <= 0.15F)
            {
                biome.precipitation = Biome.RainType.SNOW;
            }
            else
            {
                biome.precipitation = Biome.RainType.RAIN;
            }
        });
    }
}
