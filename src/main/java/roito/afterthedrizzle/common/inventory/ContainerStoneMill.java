package roito.afterthedrizzle.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import roito.afterthedrizzle.common.tileentity.TileEntityStoneMill;

public class ContainerStoneMill extends Container
{
    private TileEntityStoneMill tileEntity;
    private IItemHandler inputItem;
    private IItemHandler outputItem;

    private int processTicks = 0;
    private int totalTicks = 0;

    public ContainerStoneMill(EntityPlayer player, TileEntity tileEntity)
    {
        super();
        this.inputItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.outputItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.addSlotToContainer(new SlotItemHandler(this.inputItem, 0, 73, 38));
        this.addSlotToContainer(new SlotItemHandler(this.outputItem, 0, 123, 20)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });
        this.addSlotToContainer(new SlotItemHandler(this.outputItem, 1, 123, 38)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });
        this.addSlotToContainer(new SlotItemHandler(this.outputItem, 2, 123, 56)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18 + 33));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        this.tileEntity = (TileEntityStoneMill) tileEntity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.getDistanceSq(this.tileEntity.getPos()) <= 64;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
        {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        boolean isMerged = false;

        if (index >= 0 && index < 1)
        {
            isMerged = mergeItemStack(newStack, 4, 40, true);
        }
        else if (index >= 1 && index < 4)
        {
            isMerged = mergeItemStack(newStack, 4, 40, true);
        }
        else if (index >= 4 && index < 31)
        {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 31, 40, false);
        }
        else if (index >= 31 && index < 40)
        {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 4, 31, false);
        }

        if (!isMerged)
        {
            return ItemStack.EMPTY;
        }

        if (newStack.getCount() == 0)
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else
        {
            slot.onSlotChanged();
        }

        slot.onTake(playerIn, newStack);

        return oldStack;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        this.processTicks = tileEntity.getProcessTicks();
        this.totalTicks = tileEntity.getTotalTicks();

        for (IContainerListener i : this.listeners)
        {
            i.sendWindowProperty(this, 0, this.processTicks);
            i.sendWindowProperty(this, 1, this.totalTicks);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        super.updateProgressBar(id, data);

        switch (id)
        {
            case 0:
                this.processTicks = data;
                break;
            case 1:
                this.totalTicks = data;
        }
    }

    public int getProcessTicks()
    {
        return this.processTicks;
    }

    public int getTotalTicks()
    {
        return this.totalTicks;
    }

    public TileEntityStoneMill getTileEntity()
    {
        return this.tileEntity;
    }
}
