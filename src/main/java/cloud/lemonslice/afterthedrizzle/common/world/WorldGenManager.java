package cloud.lemonslice.afterthedrizzle.common.world;

import cloud.lemonslice.afterthedrizzle.common.world.feature.FeaturesRegistry;
import cloud.lemonslice.silveroak.common.environment.Humidity;
import cloud.lemonslice.silveroak.common.environment.Rainfall;
import cloud.lemonslice.silveroak.common.environment.Temperature;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public final class WorldGenManager
{
    public static void init()
    {
        ForgeRegistries.BIOMES.forEach(biome ->
        {
            float temp = biome.getDefaultTemperature();
            float rain = biome.getDownfall();
            Humidity humidity = Humidity.getHumid(rain, temp);
            if (humidity.getId() >= 4)
            {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeaturesRegistry.TEA_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeaturesRegistry.BAMBOO_DIRT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeaturesRegistry.WILD_GRAPE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
            }

            Temperature temperature = Temperature.getTemperatureLevel(temp);
            Rainfall rainfall = Rainfall.getRainfallLevel(rain);
            if (temperature.getId() <= 4 && rainfall.getId() >= 2 && rainfall.getId() <= 4)
            {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeaturesRegistry.COLD_FLOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
            }
            else if (temperature.getId() >= 3 && temperature.getId() <= 5 && rainfall.getId() >= 2)
            {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeaturesRegistry.WARM_FLOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
            }
        });
    }
}
