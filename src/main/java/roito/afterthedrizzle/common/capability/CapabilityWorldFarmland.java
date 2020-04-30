package roito.afterthedrizzle.common.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityWorldFarmland
{
    public static class Storage implements Capability.IStorage<IWorldFarmland>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IWorldFarmland> capability, IWorldFarmland instance, Direction side)
        {
            return null;
        }

        @Override
        public void readNBT(Capability<IWorldFarmland> capability, IWorldFarmland instance, Direction side, INBT nbt)
        {

        }
    }

    public static class Implementation implements IWorldFarmland
    {

    }

    public static class Provider implements ICapabilitySerializable<INBT>
    {

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            return null;
        }

        @Override
        public INBT serializeNBT()
        {
            return null;
        }

        @Override
        public void deserializeNBT(INBT nbt)
        {

        }
    }

    interface IWorldFarmland
    {

    }
}
