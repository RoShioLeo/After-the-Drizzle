package roito.afterthedrizzle.helper;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShape;

public final class VoxelShapeHelper
{
    public static VoxelShape createVoxelShape(double beginX, double beginY, double beginZ, double u, double h, double v)
    {
        return Block.makeCuboidShape(beginX, beginY, beginZ, beginX + u, beginY + h, beginZ + v);
    }
}
