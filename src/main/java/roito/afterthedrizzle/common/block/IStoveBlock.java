package roito.afterthedrizzle.common.block;

import net.minecraft.block.BlockState;

public interface IStoveBlock
{
    boolean isBurning(BlockState blockState);

    int getFuelPower();
}
