package roito.afterthedrizzle.common.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import roito.afterthedrizzle.common.block.BlocksRegistry;

import java.util.Random;
import java.util.function.Function;

public class BambooDirtFeature extends Feature<NoFeatureConfig>
{
    public BambooDirtFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
        this.setRegistryName("bamboo_dirt");
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        BlockState blockstate = BlocksRegistry.GRASS_BLOCK_WITH_HOLE.getDefaultState();
        int i = 0;
        for (int j = 0; j < 4; ++j)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (blockpos.getY() < 255 && worldIn.getBlockState(blockpos).getBlock().equals(Blocks.GRASS_BLOCK))
            {
                worldIn.setBlockState(blockpos, blockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
