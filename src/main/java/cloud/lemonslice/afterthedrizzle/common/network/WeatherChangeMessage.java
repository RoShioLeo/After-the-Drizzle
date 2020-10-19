package cloud.lemonslice.afterthedrizzle.common.network;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.client.ClientEventHandler;
import cloud.lemonslice.afterthedrizzle.client.ClientProxy;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilityWorldWeather;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.BiomeWeatherManager;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.WeatherType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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
            if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            {
                AfterTheDrizzle.proxy.getClientWorld().getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data ->
                {
                    World world = AfterTheDrizzle.proxy.getClientWorld();
                    WeatherType type = WeatherType.values()[weatherType];
                    switch (type)
                    {
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
                            WorldRenderer.RAIN_TEXTURES = ClientProxy.LIGHT_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = ClientProxy.LIGHT_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.RAINY_LIGHT;
                            break;
                        case RAINY_NORMAL:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(false);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = ClientProxy.NORMAL_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = ClientProxy.NORMAL_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.RAINY_NORMAL;
                            break;
                        case RAINY_HEAVY:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(false);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = ClientProxy.HEAVY_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = ClientProxy.HEAVY_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.RAINY_HEAVY;
                            break;
                        case STORM:
                            world.getWorldInfo().setRaining(true);
                            world.getWorldInfo().setThundering(true);
                            BiomeWeatherManager.setToNormal();
                            WorldRenderer.RAIN_TEXTURES = ClientProxy.HEAVY_RAIN_TEXTURES;
                            WorldRenderer.SNOW_TEXTURES = ClientProxy.HEAVY_SNOW_TEXTURES;
                            ClientEventHandler.current = WeatherType.STORM;
                    }
                });
            }
        });
    }
}
