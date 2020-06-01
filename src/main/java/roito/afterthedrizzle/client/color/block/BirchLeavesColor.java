package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.ILightReader;
import roito.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import roito.afterthedrizzle.common.environment.solar.SolarTerms;

import javax.annotation.Nullable;

public class BirchLeavesColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable ILightReader reader, @Nullable BlockPos pos, int index)
    {
        if (Minecraft.getInstance().world != null)
        {
            return Minecraft.getInstance().world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data ->
                    SolarTerms.get(data.getSolarTermIndex()).getBirchColor()).orElse(FoliageColors.getBirch());
        }
        return FoliageColors.getBirch();
    }
}
