package roito.afterthedrizzle.common.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.environment.FlowerColor;

import java.util.Random;
import java.util.function.Function;

import static roito.afterthedrizzle.common.block.HybridizableFlowerBlock.FLOWER_COLOR;

public class HybridizableFlowersFeature extends FlowersFeature
{
    public HybridizableFlowersFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
        this.setRegistryName("hybridizable_flower");
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos)
    {
        int i = random.nextInt(3);
        return BlocksRegistry.CHRYSANTHEMUM.getDefaultState().with(FLOWER_COLOR, FlowerColor.values()[i]);
    }
}
