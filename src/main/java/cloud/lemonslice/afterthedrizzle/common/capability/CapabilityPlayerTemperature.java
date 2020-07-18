package cloud.lemonslice.afterthedrizzle.common.capability;

import cloud.lemonslice.afterthedrizzle.common.environment.temperature.ApparentTemperature;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class CapabilityPlayerTemperature
{
    @CapabilityInject(Data.class)
    public static Capability<Data> PLAYER_TEMP;

    public static class Storage implements Capability.IStorage<Data>
    {
        @Override
        public INBT writeNBT(Capability<Data> capability, Data instance, Direction side)
        {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("PlayerTemperature", instance.getTemperature());
            return compound;
        }

        @Override
        public void readNBT(Capability<Data> capability, Data instance, Direction side, INBT nbt)
        {
            instance.setPlayerTemperature(((CompoundNBT) nbt).getInt("PlayerTemperature"));
        }
    }

    public static class Data
    {
        private int temperature = 0;
        private int up = 0;

        public ApparentTemperature getApparentTemperature()
        {
            return ApparentTemperature.getTemperature(temperature);
        }

        public void addPlayerTemperature(int add)
        {
            this.temperature += add;
        }

        public void setPlayerTemperature(int temp)
        {
            this.temperature = temp;
        }

        public void setHotterOrColder(int up)
        {
            this.up = up;
        }

        public int getTemperature()
        {
            return temperature;
        }

        public int getHotterOrColder()
        {
            return up;
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT>
    {
        private final Data playerTemp = new Data();
        private final Capability.IStorage<Data> storage = PLAYER_TEMP.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            if (cap.equals(PLAYER_TEMP))
                return LazyOptional.of(() -> playerTemp).cast();
            else
                return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT()
        {
            return storage.writeNBT(PLAYER_TEMP, playerTemp, null);
        }

        @Override
        public void deserializeNBT(INBT nbt)
        {
            storage.readNBT(PLAYER_TEMP, playerTemp, null, nbt);
        }
    }
}
