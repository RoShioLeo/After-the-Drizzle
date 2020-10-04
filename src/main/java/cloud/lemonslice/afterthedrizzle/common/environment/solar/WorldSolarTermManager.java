package cloud.lemonslice.afterthedrizzle.common.environment.solar;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import cloud.lemonslice.afterthedrizzle.common.config.ServerConfig;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public class WorldSolarTermManager
{
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.END) && ServerConfig.Temperature.fluctuation.get() && event.world.getServer() != null)
        {
            ServerWorld world = event.world.getServer().getWorld(DimensionType.OVERWORLD);
            world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).ifPresent(data ->
                    data.updateTicks(world));
        }
    }
}
