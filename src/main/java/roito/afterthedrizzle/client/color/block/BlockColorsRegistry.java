package roito.afterthedrizzle.client.color.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import roito.afterthedrizzle.common.block.BlocksRegistry;

public final class BlockColorsRegistry {
    public static final IBlockColor HYBRIDIZABLE_FLOWER_COLOR = new HybridizableFlowerBlockColor();
    public BlockColorsRegistry(){
        Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.CHRYSANTHEMUM);
    }
}
