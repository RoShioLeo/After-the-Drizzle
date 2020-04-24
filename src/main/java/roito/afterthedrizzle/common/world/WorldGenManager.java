package roito.afterthedrizzle.common.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.environment.Rainfall;
import roito.afterthedrizzle.common.environment.Temperature;
import roito.afterthedrizzle.common.world.feature.FeaturesRegistry;

public final class WorldGenManager
{
    public WorldGenManager()
    {
        ForgeRegistries.BIOMES.forEach(biome ->
        {
            float temp = biome.getDefaultTemperature();
            float rain = biome.getDownfall();
            Humidity humidity = Humidity.getHumid(rain, temp);
            if (humidity.getId() >= 4)
            {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeaturesRegistry.TEA_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)));
            }

            Temperature temperature = Temperature.getTemperatureLevel(temp);
            Rainfall rainfall = Rainfall.getRainfallLevel(rain);
            if (temperature.getId() <= 4 && rainfall.getId() >= 2 && rainfall.getId() <= 4)
            {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeaturesRegistry.HYBRIDIZABLE_FLOWER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)));
            }
        });
    }
}
