package cloud.lemonslice.afterthedrizzle.common.world.feature;

import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.common.block.HybridizableFlowerBlock;
import cloud.lemonslice.afterthedrizzle.common.environment.flower.FlowerColor;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class ColdWarmFlowersFeature extends Feature<NoFeatureConfig>
{
    public ColdWarmFlowersFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
        this.setRegistryName("cold_warm_flower");
    }

    public BlockState getRandomFlower(Random random, BlockPos pos)
    {
        double d = MathHelper.clamp((1.0D + Biome.INFO_NOISE.noiseAt((double) pos.getX() / 48.0D, (double) pos.getZ() / 48.0D, false)) / 2.0D, 0.0D, 0.9999D);
        int i = random.nextInt(2);
        switch (i)
        {
            case 0:
                return BlocksRegistry.CHRYSANTHEMUM.getDefaultState().with(HybridizableFlowerBlock.FLOWER_COLOR, FlowerColor.values()[(int) (d * 4)]);
            default:
                return BlocksRegistry.HYACINTH.getDefaultState().with(HybridizableFlowerBlock.FLOWER_COLOR, FlowerColor.values()[(int) (d * 4)]);
        }
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        BlockState blockstate = this.getRandomFlower(rand, pos);
        int i = 0;
        for (int j = 0; j < 32; ++j)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 255 && blockstate.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, blockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
