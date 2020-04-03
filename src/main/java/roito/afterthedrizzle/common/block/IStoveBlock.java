package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;

public interface IStoveBlock
{
    boolean isBurning();

    int getFuelPower();

    Block getLit();

    Block getUnlit();
}
