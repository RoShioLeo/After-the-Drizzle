package roito.afterthedrizzle.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import roito.afterthedrizzle.common.config.ConfigMain;

public class TileEntityWoodenBarrel extends TileEntitySingleFluidTank
{
    private FluidTank fluidTank = new FluidTank(ConfigMain.blocks.woodenBarrelCapacity)
    {
        @Override
        protected void onContentsChanged()
        {
            TileEntityWoodenBarrel.this.markDirty();
            TileEntityWoodenBarrel.this.updateHeight();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid)
        {
            return !fluid.getFluid().isLighterThanAir() && fluid.getFluid().getTemperature() < 500;
        }
    };
    private int heightAmount = 0;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability))
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidTank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fluidTank.readFromNBT(compound.getCompoundTag("FluidTank"));
        this.heightAmount = compound.getInteger("HeightAmount");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("FluidTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
        compound.setInteger("HeightAmount", this.heightAmount);
        return super.writeToNBT(compound);
    }

    @Override
    public FluidTank getFluidTank()
    {
        return fluidTank;
    }

    private void updateHeight()
    {
        if (this.world.isRemote)
        {
            if (this.getFluidAmount() != 0)
            {
                int viscosity = this.getFluidTank().getFluid().getFluid().getViscosity() / 50;
                if (heightAmount > this.getFluidAmount())
                {
                    heightAmount -= Math.max(1, (heightAmount - this.getFluidAmount()) / viscosity);
                }
                else if (heightAmount < this.getFluidAmount())
                {
                    heightAmount += Math.max(1, (this.getFluidAmount() - heightAmount) / viscosity);
                }
            }
        }
        else
        {
            heightAmount = this.getFluidAmount();
        }
    }

    public double getHeight()
    {
        updateHeight();
        return 0.0625 + 0.875 * this.heightAmount / ConfigMain.blocks.woodenBarrelCapacity;
    }
}
