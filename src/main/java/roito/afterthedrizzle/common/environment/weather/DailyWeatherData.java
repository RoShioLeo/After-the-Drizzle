package roito.afterthedrizzle.common.environment.weather;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import roito.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import roito.afterthedrizzle.common.config.CommonConfig;
import roito.afterthedrizzle.common.environment.solar.Season;

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
        Season season = world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data -> data.getSolarTerm().getSeason()).orElse(Season.NONE);
        while (weatherList.size() < 24)
        {
            weatherList.addAll(create(season, rand, weatherList.size()));
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

    public static List<WeatherType> create(Season season, Random random, int index)
    {
        int lasting = random.nextInt(3) + 3;
        float f = random.nextFloat();
        switch (season)
        {
            case SPRING:
            {
                if (f < 0.4F)
                {
                    return create(WeatherType.SUNNY, lasting);
                }
                else if (f < 0.7F)
                {
                    if (index < 3 && random.nextInt(100) < 60)
                    {
                        if (CommonConfig.Weather.enableFoggy.get())
                            return create(WeatherType.FOGGY, 6 - index);
                    }
                    if (CommonConfig.Weather.enableOvercast.get())
                        return create(WeatherType.OVERCAST, lasting);
                    else
                        return create(WeatherType.SUNNY, lasting);
                }
                else
                {
                    float rain = random.nextInt(100);
                    if (rain < 40)
                    {
                        return create(WeatherType.RAINY_LIGHT, lasting);
                    }
                    else if (rain < 70)
                    {
                        return create(WeatherType.RAINY_NORMAL, lasting);
                    }
                    else if (rain < 90)
                    {
                        return create(WeatherType.RAINY_HEAVY, lasting);
                    }
                    else
                    {
                        return create(WeatherType.STORM, lasting);
                    }
                }
            }
            case SUMMER:
            {
                if (f < 0.6F)
                {
                    return create(WeatherType.SUNNY, lasting);
                }
                else if (f < 0.75F)
                {
                    if (CommonConfig.Weather.enableOvercast.get())
                        return create(WeatherType.OVERCAST, lasting);
                    else
                        return create(WeatherType.SUNNY, lasting);
                }
                else
                {
                    float rain = random.nextInt(100);
                    if (rain < 20F)
                    {
                        return create(WeatherType.RAINY_LIGHT, lasting);
                    }
                    else if (rain < 60F)
                    {
                        return create(WeatherType.RAINY_NORMAL, lasting);
                    }
                    else
                    {
                        if (index >= 6 && index < 12 && random.nextBoolean())
                        {
                            return create(WeatherType.STORM, random.nextInt(3) + 1);
                        }
                        else return create(WeatherType.STORM, lasting);
                    }
                }
            }
            case WINTER:
            {
                if (f < 0.45F)
                {
                    return create(WeatherType.SUNNY, lasting);
                }
                else if (f < 0.75F)
                {
                    if (CommonConfig.Weather.enableOvercast.get())
                        return create(WeatherType.OVERCAST, lasting);
                    else
                        return create(WeatherType.SUNNY, lasting);
                }
                else
                {
                    switch (random.nextInt(3))
                    {
                        case 2:
                            return create(WeatherType.RAINY_HEAVY, lasting);
                        case 1:
                            return create(WeatherType.RAINY_NORMAL, lasting);
                        default:
                            return create(WeatherType.RAINY_LIGHT, lasting);
                    }
                }
            }
            default:
            {
                if (f < 0.6F)
                {
                    return create(WeatherType.SUNNY, lasting);
                }
                else if (f < 0.8F)
                {

                    if (index < 3 && random.nextInt(100) < 45)
                    {
                        if (CommonConfig.Weather.enableFoggy.get())
                            return create(WeatherType.FOGGY, 6 - index);
                    }
                    if (CommonConfig.Weather.enableOvercast.get())
                        return create(WeatherType.OVERCAST, lasting);
                    else
                        return create(WeatherType.SUNNY, lasting);
                }
                else
                {
                    switch (random.nextInt(3))
                    {
                        case 2:
                            return create(WeatherType.RAINY_HEAVY, lasting);
                        case 1:
                            return create(WeatherType.RAINY_NORMAL, lasting);
                        default:
                            return create(WeatherType.RAINY_LIGHT, lasting);
                    }
                }
            }
        }
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
