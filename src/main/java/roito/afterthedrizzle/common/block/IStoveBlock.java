package roito.afterthedrizzle.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import static roito.afterthedrizzle.common.block.StoveBlock.LIT;

public interface IStoveBlock
{
    static boolean isBurning(IWorldReader world, BlockPos pos)
    {
        return isBurning(world.getBlockState(pos));
    }

    static boolean isBurning(BlockState state)
    {
        if (state.getBlock() instanceof IStoveBlock)
        {
            return state.get(LIT);
        }
        return false;
    }

    int getFuelPower();
}
