package roito.afterthedrizzle.common.capability;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import roito.afterthedrizzle.common.environment.weather.WeatherEvent;

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
            for (WeatherEvent weather : instance.list)
            {
                compound.put("Weather_" + i, weather.writeToNBT());
                i++;
            }
            compound.putInt("Amount", i);
            return compound;
        }

        @Override
        public void readNBT(Capability<CapabilityWorldWeather.Data> capability, CapabilityWorldWeather.Data instance, Direction side, INBT nbt)
        {
            CompoundNBT compound = (CompoundNBT) nbt;
            List<WeatherEvent> list = Lists.newArrayList();
            int index = compound.getInt("Amount");
            for (int i = 0; i < index; i++)
            {
                list.add(WeatherEvent.readFromNBT((CompoundNBT) compound.get("Weather_" + i)));
            }
            instance.list = list;
        }
    }

    public static class Data
    {
        private List<WeatherEvent> list = Lists.newArrayList();

        public WeatherEvent getCurrentWeather()
        {
            return getWeather(0);
        }

        public WeatherEvent getNextWeather()
        {
            return getWeather(1);
        }

        public WeatherEvent getWeather(int index)
        {
            if (list.isEmpty() && index >= list.size()) return WeatherEvent.EMPTY;
            return list.get(index);
        }

        public void addWeather(WeatherEvent weather)
        {
            list.add(weather);
        }

        public void deleteCurrentWeather()
        {
            if (!list.isEmpty()) list.remove(0);
        }

        public void setCurrentWeather(WeatherEvent weather)
        {
            list.clear();
            list.add(weather);
        }

        public int getListSize()
        {
            return list.size();
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
