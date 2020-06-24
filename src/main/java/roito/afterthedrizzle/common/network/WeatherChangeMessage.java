package roito.afterthedrizzle.common.network;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.client.ClientEventHandler;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.environment.weather.BiomeWeatherManager;
import roito.afterthedrizzle.common.environment.weather.WeatherType;

import java.util.function.Supplier;

import static roito.afterthedrizzle.client.ClientProxy.*;

public class WeatherChangeMessage implements INormalMessage
{
    int weatherType;

    public WeatherChangeMessage(WeatherType type)
    {
        this.weatherType = type.ordinal();
    }

    public WeatherChangeMessage(PacketBuffer buf)
    {
        weatherType = buf.readInt();
    }

    @Override
    public void toBytes(PacketBuffer buf)
    {
        buf.writeInt(weatherType);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            if (AfterTheDrizzle.proxy.getClientWorld() != null)
            {
                AfterTheDrizzle.proxy.getClientWorld().getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data ->
                {
                    World world = AfterTheDrizzle.proxy.getClientWorld();
                    WeatherType type = WeatherType.values()[weatherType];
                    switch (type)
                    {
                        case OVERCAST:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(false);
                            BiomeWeatherManager.setToOvercast();
                            ClientEventHandler.current = WeatherType.OVERCAST;
                            break;
                        case SUNNY:
                            world.getWorldInfo().setRaining(false);
                            ClientEventHandler.current = WeatherType.SUNNY;
                            break;
                        case FOGGY:
                            world.getWorldInfo().setRaining(false);
                            ClientEventHandler.current = WeatherType.FOGGY;
                            break;
                        case RAINY_LIGHT:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(false);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = LIGHT_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = LIGHT_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.RAINY_LIGHT;
                            break;
                        case RAINY_NORMAL:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(false);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = NORMAL_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = NORMAL_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.RAINY_NORMAL;
                            break;
                        case RAINY_HEAVY:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(false);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = HEAVY_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = HEAVY_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.RAINY_HEAVY;
                            break;
                        case STORM:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(true);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = HEAVY_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = HEAVY_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.STORM;
                    }
                });
            }
        });
    }
}
