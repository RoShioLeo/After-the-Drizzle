package roito.afterthedrizzle.client.color.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import roito.afterthedrizzle.common.block.BlocksRegistry;

public final class BlockColorsRegistry
{
    public static final IBlockColor HYBRIDIZABLE_FLOWER_COLOR = new HybridizableFlowerBlockColor();
    public static final IBlockColor TEAPOT_COLOR = new TeapotBlockColor();

    public BlockColorsRegistry()
    {
        Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.CHRYSANTHEMUM);
        Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.HYACINTH);
        Minecraft.getInstance().getBlockColors().register(TEAPOT_COLOR, BlocksRegistry.TEAPOT);
    }
}
