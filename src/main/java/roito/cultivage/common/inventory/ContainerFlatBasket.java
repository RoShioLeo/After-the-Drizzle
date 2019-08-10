package roito.cultivage.common.inventory;

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
import roito.cultivage.common.block.ModeFlatBasket;
import roito.cultivage.common.tileentity.TileEntityFlatBasket;

public class ContainerFlatBasket extends Container
{
    public TileEntityFlatBasket tileEntity;
    private IItemHandler containerItem;
    protected int processTicks = 0;
    protected int totalTicks = 0;
    protected ModeFlatBasket mode = ModeFlatBasket.OUTDOORS;

    public ContainerFlatBasket(EntityPlayer player, TileEntity tileEntity)
    {
        super();
        containerItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        addSlotToContainer(new SlotItemHandler(containerItem, 0, 107, 31));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18 + 33));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        this.tileEntity = (TileEntityFlatBasket) tileEntity;
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
            isMerged = mergeItemStack(newStack, 1, 37, true);
        }
        else if (index >= 1 && index < 28)
        {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 29, 38, false);
        }
        else if (index >= 28 && index < 37)
        {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 1, 28, false);
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
        this.mode = tileEntity.getMode();

        for (IContainerListener i : this.listeners)
        {
            i.sendWindowProperty(this, 0, processTicks);
            i.sendWindowProperty(this, 1, totalTicks);
            i.sendWindowProperty(this, 5, mode.ordinal());
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
                break;
            case 5:
                this.mode = ModeFlatBasket.values()[data];
                break;
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

    public ModeFlatBasket getMode()
    {
        return this.mode;
    }
}
