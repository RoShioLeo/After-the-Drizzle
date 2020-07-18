package cloud.lemonslice.afterthedrizzle.client.color.block;

import cloud.lemonslice.afterthedrizzle.common.block.HybridizableFlowerBlock;
import cloud.lemonslice.afterthedrizzle.common.environment.flower.FlowerColor;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

import javax.annotation.Nullable;

public class HybridizableFlowerBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable ILightReader reader, @Nullable BlockPos pos, int index)
    {
        if (index == 1)
        {
            return FlowerColor.getFlowerColor(state.get(HybridizableFlowerBlock.FLOWER_COLOR).getName()).getColorValue();
        }
        return -1;
    }
}
