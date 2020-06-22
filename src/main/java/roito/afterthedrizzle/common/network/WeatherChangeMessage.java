package roito.afterthedrizzle.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.environment.weather.BiomeWeatherManager;
import roito.afterthedrizzle.common.environment.weather.WeatherEvent;
import roito.afterthedrizzle.common.environment.weather.WeatherType;

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
            if (Minecraft.getInstance().world != null)
            {
                Minecraft.getInstance().world.getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data ->
                {
                    WeatherType type = WeatherType.values()[weatherType];
                    data.setCurrentWeather(new WeatherEvent(type, 100));
                    switch (type)
                    {
                        case OVERCAST:
                            BiomeWeatherManager.setToOvercast();
                            Minecraft.getInstance().world.getWorldInfo().setRaining(true);
                            break;
                        default:
                            BiomeWeatherManager.setToNormal();
                    }
                });
            }
        });
    }
}
