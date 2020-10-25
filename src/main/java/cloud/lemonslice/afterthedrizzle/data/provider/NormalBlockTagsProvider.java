package cloud.lemonslice.afterthedrizzle.data.provider;

import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import net.minecraft.block.Block;
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
        getBuilder(BlockTags.WOODEN_FENCES).add(BlocksRegistry.BAMBOO_LATTICE).add(BlocksRegistry.TRELLIS_BLOCKS.toArray(new Block[0]));
        getBuilder(BlockTags.WOODEN_DOORS).add(BlocksRegistry.BAMBOO_DOOR, BlocksRegistry.BAMBOO_GLASS_DOOR);
        getBuilder(BlockTags.SMALL_FLOWERS).add(BlocksRegistry.HYACINTH, BlocksRegistry.CHRYSANTHEMUM, BlocksRegistry.ZINNIA);
    }

    @Override
    public String getName()
    {
        return "After the Drizzle Block Tags";
    }
}
