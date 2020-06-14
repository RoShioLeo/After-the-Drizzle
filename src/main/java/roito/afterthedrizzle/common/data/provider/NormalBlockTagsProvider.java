package roito.afterthedrizzle.common.data.provider;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

import static roito.afterthedrizzle.common.block.BlocksRegistry.*;

public class NormalBlockTagsProvider extends BlockTagsProvider
{
    public NormalBlockTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {
        getBuilder(BlockTags.WOODEN_FENCES).add(BAMBOO_LATTICE, ACACIA_TRELLIS, BIRCH_TRELLIS, OAK_TRELLIS, DARK_OAK_TRELLIS, JUNGLE_TRELLIS, SPRUCE_TRELLIS);
    }
}
