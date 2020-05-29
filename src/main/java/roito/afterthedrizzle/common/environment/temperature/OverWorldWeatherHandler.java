package roito.afterthedrizzle.common.environment.temperature;

import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class OverWorldWeatherHandler
{
    @SubscribeEvent
    public static void onPlayTick(TickEvent.WorldTickEvent event)
    {
        if (!event.world.isRemote && event.world.getDimension().getType().equals(DimensionType.OVERWORLD) && event.phase.equals(TickEvent.Phase.END))
        {

        }
    }
}
