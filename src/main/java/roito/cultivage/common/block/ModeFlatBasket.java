package roito.cultivage.common.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roito.cultivage.api.block.IBlockStove;

public enum ModeFlatBasket
{
	IN_RAIN,
	OUTDOORS,
	INDOORS,
	BAKE,
	PROCESS;

	public static ModeFlatBasket getMode(World world, BlockPos pos)
	{
		if (isInRain(world, pos))
		{
			return IN_RAIN;
		}
		if (hasHeat(world, pos))
		{
			return BAKE;
		}
		else if (isInSun(world, pos))
		{
			return OUTDOORS;
		}
		else
		{
			return INDOORS;
		}
	}

	public static boolean isInRain(World world, BlockPos pos)
	{
		return world.isRainingAt(pos.up());
	}

	public static boolean hasHeat(World world, BlockPos pos)
	{
		if (world.getBlockState(pos.down()).getBlock() instanceof IBlockStove)
		{
			return ((IBlockStove) world.getBlockState(pos.down()).getBlock()).isBurning();
		}
		return false;
	}

	public static boolean isInSun(World world, BlockPos pos)
	{
		return world.canSeeSky(pos);
	}
}
