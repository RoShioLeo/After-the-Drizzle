package roito.afterthedrizzle.common.tileentity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.api.recipe.IStoneMillRecipe;
import roito.afterthedrizzle.api.recipe.StoneMillRecipe;
import roito.afterthedrizzle.common.block.BlockStoneMill;
import roito.afterthedrizzle.registry.RecipesRegistry;

public class TileEntityStoneMill extends TileEntity implements ITickable
{
    protected int angel = 0;
    protected int processTicks = 0;
    protected static int totalTicks = 200;

    protected ItemStackHandler inputInventory = new ItemStackHandler();
    protected ItemStackHandler outputInventory = new ItemStackHandler(3);
    protected FluidTank fluidTank = new FluidTank(2000);

    protected IStoneMillRecipe currentRecipe = new StoneMillRecipe(new FluidStack(FluidRegistry.WATER, 0), ItemStack.EMPTY, NonNullList.create(), new FluidStack(FluidRegistry.WATER, 0));

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
            this.markDirty();
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
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    public void syncToTrackingClients()
    {
        if (!this.world.isRemote)
        {
            SPacketUpdateTileEntity packet = this.getUpdatePacket();
            PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
            if (trackingEntry != null)
            {
                for (EntityPlayerMP player : trackingEntry.getWatchingPlayers())
                {
                    player.connection.sendPacket(packet);
                }
            }
        }
    }

    @Override
    public void update()
    {
        ItemStack input = getInput();
        if (input.isEmpty())
        {
            this.processTicks = 0;
            this.markDirty();
        }

        if (!this.currentRecipe.canWork(fluidTank.getFluid(), input))
        {
            this.currentRecipe = RecipesRegistry.MANAGER_STONE_MILL.getRecipe(fluidTank.getFluid(), input);
        }
        if (!this.currentRecipe.getOutputStacks().isEmpty())
        {
            boolean flag = true;
            for (ItemStack out : this.currentRecipe.getOutputStacks())
            {
                if (!ItemHandlerHelper.insertItem(this.outputInventory, out, true).isEmpty())
                {
                    flag = false;
                }
            }
            if (flag)
            {
                this.angel += 3;
                this.angel %= 360;
                if (++this.processTicks >= totalTicks)
                {
                    for (ItemStack out : this.currentRecipe.getOutputStacks())
                    {
                        ItemHandlerHelper.insertItem(this.outputInventory, out, false);
                    }
                    this.inputInventory.extractItem(0, 1, false);
                    this.fluidTank.drain(currentRecipe.getFluidAmount(), true);
                    if (this.currentRecipe.getOutputFluid() != null && this.currentRecipe.getOutputFluid().amount != 0)
                    {
                        IFluidHandler handler = FluidUtil.getFluidHandler(world, pos.down().offset(((BlockStoneMill) this.getBlockType()).getFacing(this.getBlockMetadata())), EnumFacing.UP);
                        if (handler != null)
                        {
                            handler.fill(this.currentRecipe.getOutputFluid(), true);
                            world.getTileEntity(pos.down().offset(((BlockStoneMill) this.getBlockType()).getFacing(this.getBlockMetadata()))).markDirty();
                        }
                    }
                    this.processTicks = 0;
                }
                this.syncToTrackingClients();
            }
        }
        else
        {
            this.processTicks = 0;
        }
        this.markDirty();
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

    public int getFluidAmount()
    {
        return fluidTank.getFluidAmount();
    }

    public String getFluidName()
    {
        return FluidRegistry.getFluidName(this.fluidTank.getFluid());
    }

    public String getOutputFluidName()
    {
        if (this.currentRecipe.getOutputFluid().amount == 0)
        {
            return null;
        }
        return FluidRegistry.getFluidName(this.currentRecipe.getOutputFluid());
    }

    public String getFluidTranslation()
    {
        return fluidTank.getFluid().getLocalizedName();
    }

    public boolean isCompleted()
    {
        return !this.currentRecipe.canWork(fluidTank.getFluid(), getInput());
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

    public void markDirty()
    {
        super.markDirty();
        this.syncToTrackingClients();
    }
}
