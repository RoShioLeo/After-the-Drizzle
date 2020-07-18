package cloud.lemonslice.afterthedrizzle.common.capability;

import cloud.lemonslice.afterthedrizzle.common.config.CommonConfig;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.BiomeTemperatureManager;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.SolarTerm;
import cloud.lemonslice.afterthedrizzle.common.network.SimpleNetworkHandler;
import cloud.lemonslice.afterthedrizzle.common.network.SolarTermsMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilitySolarTermTime
{
    @CapabilityInject(Data.class)
    public static Capability<Data> WORLD_SOLAR_TIME;

    public static class Storage implements Capability.IStorage<Data>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<Data> capability, Data instance, Direction side)
        {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("SolarTermsDay", instance.getSolarTermsDay());
            compound.putInt("SolarTermsTicks", instance.getSolarTermsTicks());
            return compound;
        }

        @Override
        public void readNBT(Capability<Data> capability, Data instance, Direction side, INBT nbt)
        {
            instance.setSolarTermsDay(((CompoundNBT) nbt).getInt("SolarTermsDay"));
            instance.setSolarTermsTicks(((CompoundNBT) nbt).getInt("SolarTermsTicks"));
        }
    }

    public static class Data
    {
        private int solarTermsDay = 0;
        private int solarTermsTicks = 0;

        public void updateTicks(ServerWorld world)
        {
            solarTermsTicks++;
            int dayTime = Math.toIntExact(world.getDayTime() % 24000);
            if (solarTermsTicks > dayTime + 100)
            {
                solarTermsDay++;
                solarTermsDay %= 24 * CommonConfig.Season.lastingDaysOfEachTerm.get();
                ForgeRegistries.BIOMES.forEach(biome ->
                        biome.temperature = BiomeTemperatureManager.getDefaultTemperature(biome) + SolarTerm.get(getSolarTermIndex()).getTemperatureChange());
                sendUpdateMessage(world);
            }
            solarTermsTicks = dayTime;
        }

        public int getSolarTermIndex()
        {
            return solarTermsDay / CommonConfig.Season.lastingDaysOfEachTerm.get();
        }

        public SolarTerm getSolarTerm()
        {
            return SolarTerm.get(this.getSolarTermIndex());
        }

        public int getSolarTermsDay()
        {
            return solarTermsDay;
        }

        public int getSolarTermsTicks()
        {
            return solarTermsTicks;
        }

        public void setSolarTermsDay(int solarTermsDay)
        {
            this.solarTermsDay = Math.max(solarTermsDay, 0) % (24 * CommonConfig.Season.lastingDaysOfEachTerm.get());
        }

        public void setSolarTermsTicks(int solarTermsTicks)
        {
            this.solarTermsTicks = solarTermsTicks;
        }

        public void sendUpdateMessage(ServerWorld world)
        {
            for (ServerPlayerEntity player : world.getServer().getPlayerList().getPlayers())
            {
                SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SolarTermsMessage(solarTermsDay));
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT>
    {
        private final Data worldSolarTime = new Data();
        private final Capability.IStorage<Data> storage = WORLD_SOLAR_TIME.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            if (cap.equals(WORLD_SOLAR_TIME))
                return LazyOptional.of(() -> worldSolarTime).cast();
            else
                return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT()
        {
            return storage.writeNBT(WORLD_SOLAR_TIME, worldSolarTime, null);
        }

        @Override
        public void deserializeNBT(INBT nbt)
        {
            storage.readNBT(WORLD_SOLAR_TIME, worldSolarTime, null, nbt);
        }
    }
}
