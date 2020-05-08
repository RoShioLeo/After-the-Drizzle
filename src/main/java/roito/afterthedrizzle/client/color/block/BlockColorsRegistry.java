package roito.afterthedrizzle.client.color.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;

public final class BlockColorsRegistry
{
    public static final IBlockColor HYBRIDIZABLE_FLOWER_COLOR = new HybridizableFlowerBlockColor();
    public static final IBlockColor TEAPOT_COLOR = new TeapotBlockColor();
    public static final IBlockColor FLUID_COLOR = new FluidBlockColor();
    public static final IBlockColor GRASS_BLOCK_COLOR = new GrassBlockColor();

    public static void init()
    {
        Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.CHRYSANTHEMUM, BlocksRegistry.HYACINTH, BlocksRegistry.ZINNIA);
        FluidsRegistry.BLOCKS.getEntries().forEach(e -> Minecraft.getInstance().getBlockColors().register(FLUID_COLOR, e.get()));
        Minecraft.getInstance().getBlockColors().register(GRASS_BLOCK_COLOR, BlocksRegistry.GRASS_BLOCK_WITH_HOLE);
        //        Minecraft.getInstance().getBlockColors().register(TEAPOT_COLOR, BlocksRegistry.TEAPOT);
    }
}
