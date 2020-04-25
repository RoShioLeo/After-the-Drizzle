package roito.afterthedrizzle.common.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.environment.FlowerColor;

import java.util.Random;
import java.util.function.Function;

import static roito.afterthedrizzle.common.block.HybridizableFlowerBlock.FLOWER_COLOR;

public class WarmFlowersFeature extends FlowersFeature
{
    public WarmFlowersFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
        this.setRegistryName("warm_flower");
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos)
    {
        double d = MathHelper.clamp((1.0D + Biome.INFO_NOISE.getValue((double) pos.getX() / 48.0D, (double) pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
        return BlocksRegistry.ZINNIA.getDefaultState().with(FLOWER_COLOR, FlowerColor.values()[(int) (d * 3)]);
    }
}
