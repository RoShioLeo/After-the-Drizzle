package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

import javax.annotation.Nullable;

public class FluidBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader world, @Nullable BlockPos pos, int index)
    {
        if (index == 0)
        {
            return state.getFluidState().getFluid().getAttributes().getColor();
        }
        return -1;
    }
}
