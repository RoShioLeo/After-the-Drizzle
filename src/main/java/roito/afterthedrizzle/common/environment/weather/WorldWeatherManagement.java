package roito.afterthedrizzle.common.environment.weather;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.network.WeatherChangeMessage;

import static roito.afterthedrizzle.common.environment.weather.WeatherType.OVERCAST;
import static roito.afterthedrizzle.common.environment.weather.WeatherType.SUNNY;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class WorldWeatherManagement
{
    private static WeatherType currentType = WeatherType.NONE;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
            if (!event.world.isRemote)
            {
                event.world.getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data ->
                {
                    while (data.getListSize() < 10)
                    {
                        data.addWeather(new WeatherEvent(SUNNY, 100));
                    }

                    data.getCurrentWeather().tick();
                    if (!data.getCurrentWeather().isValid())
                    {
                        data.deleteCurrentWeather();
                    }
                    else
                    {
                        switch (data.getCurrentWeather().getType())
                        {
                            case OVERCAST:
                                if (currentType != OVERCAST || !event.world.getWorldInfo().isRaining())
                                {
                                    BiomeWeatherManager.setToOvercast();
                                    for (ServerPlayerEntity player : event.world.getServer().getPlayerList().getPlayers())
                                    {
                                        SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new WeatherChangeMessage(data.getCurrentWeather().getType()));
                                    }
                                    event.world.getWorldInfo().setRaining(true);
                                    currentType = OVERCAST;
                                }
                                break;
                            default:
                                if (currentType != data.getCurrentWeather().getType())
                                {
                                    BiomeWeatherManager.setToNormal();
                                    for (ServerPlayerEntity player : event.world.getServer().getPlayerList().getPlayers())
                                    {
                                        SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new WeatherChangeMessage(data.getCurrentWeather().getType()));
                                    }
                                    currentType = data.getCurrentWeather().getType();
                                }
                        }
                    }
                });
            }
    }
}
