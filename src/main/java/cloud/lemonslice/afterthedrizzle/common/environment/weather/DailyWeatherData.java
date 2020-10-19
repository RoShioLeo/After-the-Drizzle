package cloud.lemonslice.afterthedrizzle.common.environment.weather;

import cloud.lemonslice.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.SolarTerm;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DailyWeatherData
{
    private List<WeatherType> weatherList = Lists.newArrayList();

    public static final DailyWeatherData NONE = new DailyWeatherData(Collections.emptyList());

    private DailyWeatherData(List<WeatherType> list)
    {
        this.weatherList = list;
    }

    private DailyWeatherData(ServerWorld world)
    {
        Random rand = world.rand;
        SolarTerm solarTerm = world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data -> data.getSolarTerm()).orElse(SolarTerm.NONE);
        int lasting = rand.nextInt(3) + 3;
        while (weatherList.size() < 24)
        {
            WeatherType weatherType = solarTerm.createWeather(rand, weatherList.size());
            if (weatherType == WeatherType.FOGGY)
            {
                weatherList.addAll(create(weatherType, 6 - weatherList.size()));
            }
            else weatherList.addAll(create(weatherType, lasting));
        }
    }

    public WeatherType getCurrentWeather(int ticks)
    {
        return weatherList.get(ticks / 1000);
    }

    public List<WeatherType> getWeatherList()
    {
        return weatherList;
    }

    public void setWeatherList(List<WeatherType> weatherList)
    {
        this.weatherList = weatherList;
    }

    public CompoundNBT writeToNBT()
    {
        CompoundNBT compound = new CompoundNBT();
        for (int i = 0; i < 24; i++)
        {
            compound.putInt("Weather_" + i, weatherList.get(i).ordinal());
        }
        return compound;
    }

    public void readFromNBT(CompoundNBT nbt)
    {
        List<WeatherType> list = Lists.newArrayList();
        for (int i = 0; i < 24; i++)
        {
            list.add(WeatherType.values()[nbt.getInt("Weather_" + i)]);
        }
        weatherList = list;
    }

    public static WeatherType getMainWeather(List<WeatherType> list, int startIndex)
    {
        HashMap<WeatherType, Integer> count = new HashMap<>();
        WeatherType main = WeatherType.NONE;
        int k = 0;
        for (int i = startIndex; i < startIndex + 6; i++)
        {
            if (i >= list.size())
            {
                break;
            }
            WeatherType type = list.get(i);
            int j = count.getOrDefault(type, 0) + 1;
            if (j > k)
            {
                k = j;
                main = type;
            }
            count.put(type, j);
        }
        return main;
    }

    public static DailyWeatherData fromNBTToData(CompoundNBT nbt)
    {
        List<WeatherType> list = Lists.newArrayList();
        for (int i = 0; i < 24; i++)
        {
            list.add(WeatherType.values()[nbt.getInt("Weather_" + i)]);
        }
        return new DailyWeatherData(list);
    }

    public static List<WeatherType> create(WeatherType type, int amount)
    {
        List<WeatherType> list = Lists.newArrayList();
        for (int i = 0; i < amount; i++)
        {
            list.add(type);
        }
        return list;
    }

    public static DailyWeatherData create(ServerWorld world)
    {
        return new DailyWeatherData(world);
    }

    public static DailyWeatherData create(List<WeatherType> list)
    {
        return new DailyWeatherData(list);
    }
}
