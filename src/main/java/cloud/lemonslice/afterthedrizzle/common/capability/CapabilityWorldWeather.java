package cloud.lemonslice.afterthedrizzle.common.capability;

import cloud.lemonslice.afterthedrizzle.common.environment.weather.DailyWeatherData;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.WeatherType;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CapabilityWorldWeather
{
    @CapabilityInject(Data.class)
    public static Capability<Data> WORLD_WEATHER;

    public static class Storage implements Capability.IStorage<CapabilityWorldWeather.Data>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<CapabilityWorldWeather.Data> capability, CapabilityWorldWeather.Data instance, Direction side)
        {
            CompoundNBT compound = new CompoundNBT();
            int i = 0;
            for (DailyWeatherData weather : instance.list)
            {
                compound.put("Day_" + i, weather.writeToNBT());
                i++;
            }
            compound.putInt("Amount", i);
            compound.putInt("WeatherTicks", instance.getWeatherTicks());
            return compound;
        }

        @Override
        public void readNBT(Capability<CapabilityWorldWeather.Data> capability, CapabilityWorldWeather.Data instance, Direction side, INBT nbt)
        {
            CompoundNBT compound = (CompoundNBT) nbt;
            List<DailyWeatherData> list = Lists.newArrayList();
            int index = compound.getInt("Amount");
            for (int i = 0; i < index; i++)
            {
                list.add(DailyWeatherData.fromNBTToData((CompoundNBT) compound.get("Day_" + i)));
            }
            instance.list = list;
            instance.setWeatherTicks(((CompoundNBT) nbt).getInt("WeatherTicks"));
        }
    }

    public static class Data
    {
        private List<DailyWeatherData> list = Lists.newArrayList();
        private int weatherTicks = 0;
        public WeatherType currentType = WeatherType.NONE;

        public void tick(ServerWorld world)
        {
            weatherTicks++;
            int dayTime = Math.toIntExact(world.getDayTime() % 24000);
            if (weatherTicks > dayTime + 100)
            {
                deleteCurrentDay();
            }
            weatherTicks = dayTime;
        }

        public DailyWeatherData getCurrentDay()
        {
            return getWeatherData(0);
        }

        public WeatherType getCurrentWeather()
        {
            DailyWeatherData data = getCurrentDay();
            if (!data.getWeatherList().isEmpty())
            {
                return data.getWeatherList().get(weatherTicks / 1000);
            }
            else return WeatherType.NONE;
        }

        public void setCurrentWeather(WeatherType type)
        {
            DailyWeatherData data = getCurrentDay();
            if (!data.getWeatherList().isEmpty())
                data.getWeatherList().set(weatherTicks / 1000, type);
        }

        public DailyWeatherData getCertainDay(int i)
        {
            return getWeatherData(i);
        }

        public DailyWeatherData getWeatherData(int index)
        {
            if (list.isEmpty() && index >= list.size()) return DailyWeatherData.NONE;
            return list.get(index);
        }

        public void addWeatherData(DailyWeatherData weather)
        {
            list.add(weather);
        }

        public void deleteCurrentDay()
        {
            if (!list.isEmpty()) list.remove(0);
        }

        public void setWeatherData(DailyWeatherData weather)
        {
            list.clear();
            list.add(weather);
        }

        public int getDayAmount()
        {
            return list.size();
        }

        public int getWeatherTicks()
        {
            return weatherTicks;
        }

        public void setWeatherTicks(int weatherTicks)
        {
            this.weatherTicks = weatherTicks;
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT>
    {
        private final Data worldWeather = new Data();
        private final Capability.IStorage<Data> storage = WORLD_WEATHER.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            if (cap.equals(WORLD_WEATHER))
                return LazyOptional.of(() -> worldWeather).cast();
            else
                return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT()
        {
            return storage.writeNBT(WORLD_WEATHER, worldWeather, null);
        }

        @Override
        public void deserializeNBT(INBT nbt)
        {
            storage.readNBT(WORLD_WEATHER, worldWeather, null, nbt);
        }
    }
}
