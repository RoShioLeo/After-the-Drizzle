package cloud.lemonslice.afterthedrizzle.common.world.feature;

import cloud.lemonslice.afterthedrizzle.registry.RegistryModule;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public final class FeaturesRegistry extends RegistryModule
{
    public static final Feature<NoFeatureConfig> TEA_PLANT = new TeaPlantsFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> COLD_FLOWER = new ColdWarmFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> WARM_FLOWER = new WarmFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> BAMBOO_DIRT = new BambooDirtFeature(NoFeatureConfig::deserialize);
}
