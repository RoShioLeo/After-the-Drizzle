package roito.afterthedrizzle.common.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.world.feature.FeaturesRegistry;

public class WorldGenManager
{
    public WorldGenManager()
    {
        ForgeRegistries.BIOMES.forEach(biome ->
        {
            float temp = biome.getDefaultTemperature();
            float rainfall = biome.getDownfall();
            Humidity humidity = Humidity.getHumid(rainfall, temp);
            if (humidity.getId() >= 4)
            {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeaturesRegistry.TEA_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)));
            }
        });
    }
}
