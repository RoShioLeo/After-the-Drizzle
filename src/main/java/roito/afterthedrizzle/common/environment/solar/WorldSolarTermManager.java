package roito.afterthedrizzle.common.environment.solar;

import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import roito.afterthedrizzle.common.config.CommonConfig;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public class WorldSolarTermManager
{
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.END) && CommonConfig.Temperature.fluctuation.get() && event.world.getServer() != null)
        {
            ServerWorld world = event.world.getServer().getWorld(DimensionType.OVERWORLD);
            world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).ifPresent(data ->
                    data.updateTicks(world));
        }
    }
}
