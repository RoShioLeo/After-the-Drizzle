package cloud.lemonslice.afterthedrizzle.data.provider;

import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

public class NormalBlockTagsProvider extends BlockTagsProvider
{
    public NormalBlockTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {
        getBuilder(BlockTags.WOODEN_FENCES).add(BlocksRegistry.BAMBOO_LATTICE, BlocksRegistry.ACACIA_TRELLIS, BlocksRegistry.BIRCH_TRELLIS, BlocksRegistry.OAK_TRELLIS, BlocksRegistry.DARK_OAK_TRELLIS, BlocksRegistry.JUNGLE_TRELLIS, BlocksRegistry.SPRUCE_TRELLIS);
    }
}
