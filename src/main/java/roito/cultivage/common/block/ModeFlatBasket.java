package roito.cultivage.common.block;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

    @Override
    public String toString()
    {
        return super.toString().toLowerCase();
    }

    @SideOnly(Side.CLIENT)
    public String getTranslationKey()
    {
        return I18n.format("cultivage.mode.flat_basket." + this.toString());
    }
}
