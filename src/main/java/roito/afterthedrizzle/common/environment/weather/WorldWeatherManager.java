package roito.afterthedrizzle.common.environment.weather;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.config.CommonConfig;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.network.WeatherChangeMessage;

import static roito.afterthedrizzle.common.environment.weather.WeatherType.*;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class WorldWeatherManager
{
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (CommonConfig.Weather.enable.get() && event.phase == TickEvent.Phase.START)
        {
            if (!event.world.isRemote)
            {
                event.world.getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data ->
                {
                    while (data.getDayAmount() < 3)
                    {
                        data.addWeatherData(DailyWeatherData.create((ServerWorld) event.world));
                    }

                    data.tick((ServerWorld) event.world);
                    WeatherType current = data.getCurrentWeather();

                    switch (current)
                    {
                        case OVERCAST:
                            if (data.currentType != OVERCAST || !event.world.getWorldInfo().isRaining())
                            {
                                BiomeWeatherManager.setToOvercast();
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(true);
                                event.world.getWorldInfo().setThundering(false);
                            }
                            break;
                        case SUNNY:
                            if (data.currentType != SUNNY || event.world.getWorldInfo().isRaining())
                            {
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(false);
                            }
                            break;
                        case FOGGY:
                            if (data.currentType != FOGGY || event.world.getWorldInfo().isRaining())
                            {
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(false);
                            }
                            break;
                        case RAINY_LIGHT:
                            if (data.currentType != RAINY_LIGHT || !event.world.getWorldInfo().isRaining())
                            {
                                BiomeWeatherManager.setToNormal();
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(true);
                                event.world.getWorldInfo().setThundering(false);
                            }
                            break;
                        case RAINY_NORMAL:
                            if (data.currentType != RAINY_NORMAL || !event.world.getWorldInfo().isRaining())
                            {
                                BiomeWeatherManager.setToNormal();
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(true);
                                event.world.getWorldInfo().setThundering(false);
                            }
                            break;
                        case RAINY_HEAVY:
                            if (data.currentType != RAINY_HEAVY || !event.world.getWorldInfo().isRaining())
                            {
                                BiomeWeatherManager.setToNormal();
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(true);
                                event.world.getWorldInfo().setThundering(false);
                            }
                            break;
                        case STORM:
                            if (data.currentType != STORM || !event.world.getWorldInfo().isRaining())
                            {
                                BiomeWeatherManager.setToNormal();
                                sendUpdateMessage((ServerWorld) event.world, current, data);
                                event.world.getWorldInfo().setRaining(true);
                                event.world.getWorldInfo().setThundering(true);
                            }
                    }
                });
            }
        }
    }

    public static void sendUpdateMessage(ServerWorld world, WeatherType current, CapabilityWorldWeather.Data data)
    {
        for (ServerPlayerEntity player : world.getServer().getPlayerList().getPlayers())
        {
            SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new WeatherChangeMessage(current));
        }
        data.currentType = current;
    }
}
