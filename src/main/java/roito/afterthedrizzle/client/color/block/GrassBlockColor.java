package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IEnviromentBlockReader;

import javax.annotation.Nullable;

public class GrassBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader world, @Nullable BlockPos pos, int index)
    {
        if (world != null & pos != null)
        {
            return world.getBiome(pos).getGrassColor(pos);
        }
        return GrassColors.get(0.5D, 1.0D);
    }
}
