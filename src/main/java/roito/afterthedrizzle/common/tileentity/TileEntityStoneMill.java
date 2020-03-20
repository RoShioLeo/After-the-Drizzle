package roito.afterthedrizzle.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityStoneMill extends TileEntitySingleFluidTank implements ITickable
{
    private int angel = 0;
    private int processTicks = 0;
    private static int totalTicks = 200;

    private ItemStackHandler inputInventory = new ItemStackHandler();
    private ItemStackHandler outputInventory = new ItemStackHandler(3);
    private FluidTank fluidTank = new FluidTank(2000)
    {
        @Override
        protected void onContentsChanged()
        {
            TileEntityStoneMill.this.markDirty();
        }
    };
    private NonNullList<ItemStack> outputs = NonNullList.create();
    private FluidStack outputFluid = null;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability) || CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            if (EnumFacing.DOWN.equals(facing))
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.outputInventory);
            }
            else
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inputInventory);
            }
        }
        else if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability))
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidTank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inputInventory.deserializeNBT(compound.getCompoundTag("InputInventory"));
        this.outputInventory.deserializeNBT(compound.getCompoundTag("OutputInventory"));
        this.fluidTank.readFromNBT(compound.getCompoundTag("FluidTank"));
        this.processTicks = compound.getInteger("ProcessTicks");
        this.angel = compound.getInteger("Angel");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("InputInventory", this.inputInventory.serializeNBT());
        compound.setTag("OutputInventory", this.outputInventory.serializeNBT());
        compound.setTag("FluidTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
        compound.setInteger("ProcessTicks", this.processTicks);
        compound.setInteger("Angel", this.angel);
        return super.writeToNBT(compound);
    }

    @Override
    public void update()
    {

    }

    public float getAngel()
    {
        return (float) angel;
    }

    public int getProcessTicks()
    {
        return processTicks;
    }

    public int getTotalTicks()
    {
        return totalTicks;
    }

    public String getOutputFluidName()
    {
        if (this.outputFluid == null)
        {
            return null;
        }
        return FluidRegistry.getFluidName(this.outputFluid);
    }

    public boolean isCompleted()
    {
        return this.outputFluid != null || !this.outputs.isEmpty();
    }

    public ItemStack getInput()
    {
        return this.inputInventory.getStackInSlot(0).copy();
    }

    public NonNullList<ItemStack> getOutputs()
    {
        NonNullList<ItemStack> list = NonNullList.create();
        list.add(this.outputInventory.getStackInSlot(0).copy());
        list.add(this.outputInventory.getStackInSlot(1).copy());
        list.add(this.outputInventory.getStackInSlot(2).copy());
        return list;
    }

    public boolean isEmpty()
    {
        return this.getInput().isEmpty();
    }

    @Override
    public FluidTank getFluidTank()
    {
        return fluidTank;
    }
}
