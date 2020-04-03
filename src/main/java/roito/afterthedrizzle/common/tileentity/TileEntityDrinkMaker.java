package roito.afterthedrizzle.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.common.config.ConfigMain;
import roito.afterthedrizzle.common.drink.DrinkIngredientsRegistry;
import roito.afterthedrizzle.common.recipe.drink.IDrinkRecipe;
import roito.afterthedrizzle.helper.RecipesHelper;
import roito.afterthedrizzle.registry.RecipesRegistry;

public class TileEntityDrinkMaker extends TileEntitySingleFluidTank implements ITickable
{
    private ItemStackHandler ingredientsInventory = new ItemStackHandler(4);
    private ItemStackHandler residuesInventory = new ItemStackHandler(4);
    private FluidTank fluidTank = new FluidTank(ConfigMain.blocks.drinkMakerCapacity)
    {
        @Override
        protected void onContentsChanged()
        {
            TileEntityDrinkMaker.this.markDirty();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid)
        {
            return !fluid.getFluid().isLighterThanAir() && fluid.getFluid().getTemperature() < 500;
        }
    };
    private int processTicks = 0;
    private static int totalTicks = 200;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability))
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
            if (facing == EnumFacing.DOWN)
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.residuesInventory);
            }
            else
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.ingredientsInventory);
            }
        }
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
        this.ingredientsInventory.deserializeNBT(compound.getCompoundTag("IngredientsInventory"));
        this.residuesInventory.deserializeNBT(compound.getCompoundTag("ResiduesInventory"));
        this.processTicks = compound.getInteger("ProcessTicks");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("FluidTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("IngredientsInventory", this.ingredientsInventory.serializeNBT());
        compound.setTag("ResiduesInventory", this.residuesInventory.serializeNBT());
        compound.setInteger("ProcessTicks", this.processTicks);
        return super.writeToNBT(compound);
    }

    @Override
    public void update()
    {
        if (this.getFluidAmount() != 0)
        {
            IDrinkRecipe inventoryRecipe = RecipesHelper.inventoryToDrinkRecipe(this.ingredientsInventory, this.getFluidTank().getFluid().getFluid());
            FluidStack output = RecipesRegistry.MANAGER_DRINK_MAKER.getOutput(inventoryRecipe);
            if (output != null)
            {
                int n = (int) Math.ceil(this.getFluidAmount() / output.amount);
                for (int i = 0; i < 4; i++)
                {
                    if (!this.ingredientsInventory.getStackInSlot(i).isEmpty() && this.ingredientsInventory.getStackInSlot(i).getCount() < n)
                    {
                        this.setToZero();
                        return;
                    }
                }
                this.processTicks++;
                this.markDirty();
                if (this.processTicks >= totalTicks)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        ItemStack residue = DrinkIngredientsRegistry.getResidue(this.ingredientsInventory.extractItem(i, n, false));
                        if (!residue.isEmpty())
                        {
                            residue.setCount(n);
                            this.residuesInventory.insertItem(i, residue, false);
                        }
                    }
                    this.fluidTank.setFluid(new FluidStack(output.getFluid(), this.getFluidAmount()));
                    this.setToZero();
                }
            }
            else
            {
                this.setToZero();
            }
        }
        else
        {
            this.setToZero();
        }
    }

    public void setToZero()
    {
        processTicks = 0;
        this.markDirty();
    }

    public int getProcessTicks()
    {
        return processTicks;
    }

    public int getTotalTicks()
    {
        return totalTicks;
    }

    @Override
    public FluidTank getFluidTank()
    {
        return fluidTank;
    }
}
