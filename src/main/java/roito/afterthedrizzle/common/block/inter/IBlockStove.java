package roito.afterthedrizzle.common.block.inter;

import net.minecraft.block.Block;

public interface IBlockStove
{
    boolean isBurning();

    int getFuelPower();

    Block getLit();

    Block getUnlit();
}
