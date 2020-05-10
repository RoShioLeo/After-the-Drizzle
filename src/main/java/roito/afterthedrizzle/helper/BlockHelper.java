package roito.afterthedrizzle.helper;


import net.minecraft.util.Direction;

public final class BlockHelper
{
    public static Direction getNextHorizontal(Direction facing)
    {
        int index = (facing.getHorizontalIndex() + 1) % 4;
        return Direction.byHorizontalIndex(index);
    }

    public static Direction getPreviousHorizontal(Direction facing)
    {
        int index = (facing.getHorizontalIndex() + 3) % 4;
        return Direction.byHorizontalIndex(index);
    }
}
