package roito.afterthedrizzle.helper;

import net.minecraft.util.EnumFacing;

public final class BlocksHelper
{
    public static EnumFacing getNextHorizontal(EnumFacing facing)
    {
        int index = (facing.getHorizontalIndex() + 1) % 4;
        return EnumFacing.byHorizontalIndex(index);
    }

    public static EnumFacing getPreviousHorizontal(EnumFacing facing)
    {
        int index = (facing.getHorizontalIndex() + 3) % 4;
        return EnumFacing.byHorizontalIndex(index);
    }
}
