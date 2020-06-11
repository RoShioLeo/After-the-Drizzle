package roito.afterthedrizzle.common.data.provider;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import roito.afterthedrizzle.common.block.BlocksRegistry;

public class NormalBlockTagsProvider extends BlockTagsProvider
{
    public NormalBlockTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {
        getBuilder(BlockTags.WOODEN_FENCES).add(BlocksRegistry.BAMBOO_LATTICE);
    }
}
