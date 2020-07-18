package cloud.lemonslice.afterthedrizzle.helper;

import cloud.lemonslice.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.Season;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.SolarTerm;
import net.minecraft.world.World;

public final class SeasonHelper
{
    public static Season getSeason(World world)
    {
        return world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data -> data.getSolarTerm().getSeason()).orElse(Season.NONE);
    }

    public static SolarTerm getSolarTerm(World world)
    {
        return world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(CapabilitySolarTermTime.Data::getSolarTerm).orElse(SolarTerm.NONE);
    }
}
