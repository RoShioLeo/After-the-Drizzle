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
                return (T) ashInventory;
            }
            else
            {
                return (T) fuelInventory;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        fuelInventory.deserializeNBT(compound.getCompoundTag("FuelInventory"));
        ashInventory.deserializeNBT(compound.getCompoundTag("AshInventory"));
        fuelTicks = compound.getInteger("FuelTicks");
        remainTicks = compound.getInteger("RemainTicks");
        lit = compound.getBoolean("Lit");
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
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void update()
    {
        if (!getWorld().isRemote)
        {
            if (lit)
            {
                addFuel();
            }
            if (remainTicks > 0)
            {
                remainTicks--;
            }
            else
            {
                BlockStove.setState(false, getWorld(), pos, (IBlockStove) getBlockType());
            }
            mark();
        }
    }

    private boolean addFuel()
    {
        if (isBurning())
        {
            lit = true;
            return true;
        }
        else
        {
            ItemStack itemFuel = fuelInventory.extractItem(0, 1, true);
            if (isItemFuel(itemFuel))
            {
                fuelTicks = getItemBurnTime(itemFuel);
                remainTicks = getItemBurnTime(itemFuel);
                Item cItem = fuelInventory.getStackInSlot(0).getItem().getContainerItem();
                if (cItem != null)
                {
                    fuelInventory.extractItem(0, 1, false);
                    fuelInventory.insertItem(0, new ItemStack(cItem, 1), false);
                }
                else
                {
                    fuelInventory.extractItem(0, 1, false);
                }
                ashInventory.insertItem(0, new ItemStack(ItemsRegistry.ASH), false);
                markDirty();
                BlockStove.setState(true, getWorld(), pos, (IBlockStove) getBlockType());
                lit = true;
                return true;
            }
            else
            {
                lit = false;
                return false;
            }
        }
    }

    public boolean isBurning()
    {
        return remainTicks > 0;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }

    public NonNullList<ItemStack> getContents()
    {
        NonNullList<ItemStack> list = NonNullList.create();
        ItemStack con = fuelInventory.getStackInSlot(0).copy();
        con.setCount(1);
        for (int i = fuelInventory.getStackInSlot(0).getCount(); i > 0; i -= 4)
        {
            list.add(con);
        }
        return list;
    }

    private void refresh()
    {
        if (hasWorld() && !world.isRemote)
        {
            IBlockState state = world.getBlockState(pos);
            world.markAndNotifyBlock(pos, null, state, state, 11);
        }
    }

    public int getRemainTicks()
    {
        return remainTicks;
    }

    public int getFuelTicks()
    {
        return fuelTicks;
    }

    public void setToLit()
    {
        lit = true;
    }

    public void mark()
    {
        markDirty();
        refresh();
    }
}
