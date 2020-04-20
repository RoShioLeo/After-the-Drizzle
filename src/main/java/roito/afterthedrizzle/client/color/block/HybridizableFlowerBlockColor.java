package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import roito.afterthedrizzle.common.block.HybridizableFlowerBlock;
import roito.afterthedrizzle.common.environment.FlowerColor;

import javax.annotation.Nullable;

public class HybridizableFlowerBlockColor implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader world, @Nullable BlockPos pos, int index)
    {
        if (index == 0){
            return state.has(HybridizableFlowerBlock.FLOWER_COLOR) ? FlowerColor.getColorFromName(state.get(HybridizableFlowerBlock.FLOWER_COLOR).getName()).getColorValue() : -1;
        }
        return -1;
    }
}
