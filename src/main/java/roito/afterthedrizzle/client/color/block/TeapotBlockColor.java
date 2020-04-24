package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import roito.afterthedrizzle.common.tileentity.TeapotTileEntity;

import javax.annotation.Nullable;

public class TeapotBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader world, @Nullable BlockPos pos, int index)
    {
        if (index == 1 && world != null & pos != null)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TeapotTileEntity)
            {
                int color = ((TeapotTileEntity) tileEntity).getFluid().getAttributes().getColor();
                return color != 0 ? color : -1;
            }
        }
        return -1;
    }
}
