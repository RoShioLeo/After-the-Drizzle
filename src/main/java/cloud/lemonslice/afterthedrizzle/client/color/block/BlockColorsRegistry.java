package cloud.lemonslice.afterthedrizzle.client.color.block;

import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.common.block.TrellisWithVineBlock;
import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;

public final class BlockColorsRegistry
{
    public static final IBlockColor HYBRIDIZABLE_FLOWER_COLOR = new HybridizableFlowerBlockColor();
    public static final IBlockColor FLUID_COLOR = new FluidBlockColor();
    public static final IBlockColor GRASS_BLOCK_COLOR = new GrassBlockColor();
    public static final IBlockColor BIRCH_LEAVES_COLOR = new BirchLeavesColor();
    public static final IBlockColor TEA_CUP_COLOR = new TeaCupBlockColor();

    public static void init()
    {
        Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.CHRYSANTHEMUM, BlocksRegistry.HYACINTH, BlocksRegistry.ZINNIA);
        FluidsRegistry.BLOCKS.getEntries().forEach(e -> Minecraft.getInstance().getBlockColors().register(FLUID_COLOR, e.get()));
        Minecraft.getInstance().getBlockColors().register(GRASS_BLOCK_COLOR, BlocksRegistry.GRASS_BLOCK_WITH_HOLE);
        BlocksRegistry.TRELLIS_BLOCKS.stream().filter(block -> block instanceof TrellisWithVineBlock).forEach(block -> Minecraft.getInstance().getBlockColors().register(GRASS_BLOCK_COLOR, block));
        Minecraft.getInstance().getBlockColors().register(BIRCH_LEAVES_COLOR, Blocks.BIRCH_LEAVES);
        Minecraft.getInstance().getBlockColors().register(TEA_CUP_COLOR, BlocksRegistry.WOODEN_TRAY);
    }
}
