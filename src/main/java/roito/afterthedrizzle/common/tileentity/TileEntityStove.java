package roito.afterthedrizzle.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.api.block.IBlockStove;
import roito.afterthedrizzle.common.block.BlockStove;
import roito.afterthedrizzle.registry.ItemsRegistry;

import static net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime;
import static net.minecraft.tileentity.TileEntityFurnace.isItemFuel;

public class TileEntityStove extends TileEntity implements ITickable
{
    private int remainTicks = 0;
    private int fuelTicks = 0;
    private boolean lit = false;

    private ItemStackHandler fuelInventory = new ItemStackHandler();
    private ItemStackHandler ashInventory = new ItemStackHandler();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
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
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.ashInventory);
            }
            else
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.fuelInventory);
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fuelInventory.deserializeNBT(compound.getCompoundTag("FuelInventory"));
        this.ashInventory.deserializeNBT(compound.getCompoundTag("AshInventory"));
        this.fuelTicks = compound.getInteger("FuelTicks");
        this.remainTicks = compound.getInteger("RemainTicks");
        this.lit = compound.getBoolean("Lit");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("FuelInventory", fuelInventory.serializeNBT());
        compound.setTag("AshInventory", ashInventory.serializeNBT());
        compound.setInteger("FuelTicks", fuelTicks);
        compound.setInteger("RemainTicks", remainTicks);
        compound.setBoolean("Lit", lit);
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
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void update()
    {
        if (!this.world.isRemote)
        {
            if (this.lit)
            {
                this.addFuel();
            }
            if (this.remainTicks > 0)
            {
                this.remainTicks--;
            }
            else
            {
                BlockStove.setState(false, this.world, this.pos, (IBlockStove) this.getBlockType());
            }
            this.mark();
        }
    }

    private boolean addFuel()
    {
        if (this.isBurning())
        {
            this.lit = true;
            return true;
        }
        else
        {
            ItemStack itemFuel = this.fuelInventory.extractItem(0, 1, true);
            if (isItemFuel(itemFuel))
            {
                this.fuelTicks = getItemBurnTime(itemFuel);
                this.remainTicks = getItemBurnTime(itemFuel);
                Item cItem = this.fuelInventory.getStackInSlot(0).getItem().getContainerItem();
                if (cItem != null)
                {
                    this.fuelInventory.extractItem(0, 1, false);
                    this.fuelInventory.insertItem(0, new ItemStack(cItem, 1), false);
                }
                else
                {
                    this.fuelInventory.extractItem(0, 1, false);
                }
                this.ashInventory.insertItem(0, new ItemStack(ItemsRegistry.ASH), false);
                this.markDirty();
                BlockStove.setState(true, getWorld(), pos, (IBlockStove) getBlockType());
                this.lit = true;
                return true;
            }
            else
            {
                this.lit = false;
                return false;
            }
        }
    }

    public boolean isBurning()
    {
        return this.remainTicks > 0;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }

    public NonNullList<ItemStack> getContents()
    {
        NonNullList<ItemStack> list = NonNullList.create();
        ItemStack con = this.fuelInventory.getStackInSlot(0).copy();
        con.setCount(1);
        for (int i = this.fuelInventory.getStackInSlot(0).getCount(); i > 0; i -= 4)
        {
            list.add(con);
        }
        return list;
    }

    private void refresh()
    {
        if (this.hasWorld() && !this.world.isRemote)
        {
            IBlockState state = this.world.getBlockState(pos);
            this.world.markAndNotifyBlock(pos, null, state, state, 11);
        }
    }

    public int getRemainTicks()
    {
        return this.remainTicks;
    }

    public int getFuelTicks()
    {
        return this.fuelTicks;
    }

    public void setToLit()
    {
        this.lit = true;
    }

    public void mark()
    {
        this.markDirty();
        this.refresh();
    }
}
