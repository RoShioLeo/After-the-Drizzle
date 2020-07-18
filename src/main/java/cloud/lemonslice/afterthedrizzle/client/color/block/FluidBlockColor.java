package cloud.lemonslice.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class FluidBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable ILightReader reader, @Nullable BlockPos pos, int index)
    {
        if (index == 0)
        {
            return state.getFluidState().getFluid().getAttributes().getColor();
        }
        return -1;
    }
}
