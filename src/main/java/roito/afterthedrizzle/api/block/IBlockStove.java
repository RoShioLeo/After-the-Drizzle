package roito.afterthedrizzle.api.block;

import net.minecraft.block.Block;

public interface IBlockStove
{
    boolean isBurning();

    int getFuelPower();

    Block getLit();

    Block getUnlit();
}
