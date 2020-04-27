package roito.afterthedrizzle.client.color.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import roito.afterthedrizzle.common.block.BlocksRegistry;

import static roito.afterthedrizzle.common.fluid.FluidsRegistry.BLOCKS;

public final class BlockColorsRegistry
{
    public static final IBlockColor HYBRIDIZABLE_FLOWER_COLOR = new HybridizableFlowerBlockColor();
    public static final IBlockColor TEAPOT_COLOR = new TeapotBlockColor();
    public static final IBlockColor FLUID_COLOR = new FluidBlockColor();

    public static void init()
    {
        Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.CHRYSANTHEMUM, BlocksRegistry.HYACINTH, BlocksRegistry.ZINNIA);
        BLOCKS.getEntries().forEach(e -> Minecraft.getInstance().getBlockColors().register(FLUID_COLOR, e.get()));
        //        Minecraft.getInstance().getBlockColors().register(TEAPOT_COLOR, BlocksRegistry.TEAPOT);
    }
}
