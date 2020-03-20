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
import roito.afterthedrizzle.common.tileentity.TileEntityDrinkMaker;

public class ContainerDrinkMaker extends Container
{
    private TileEntityDrinkMaker tileEntity;
    private IItemHandler ingredientItems;
    private IItemHandler residueItems;

    private int processTicks = 0;
    private int totalTicks = 0;

    public ContainerDrinkMaker(EntityPlayer player, TileEntity tileEntity)
    {
        super();
        this.ingredientItems = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.residueItems = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.addSlotToContainer(new SlotItemHandler(this.ingredientItems, 0, 26, 24));
        this.addSlotToContainer(new SlotItemHandler(this.ingredientItems, 1, 44, 24));
        this.addSlotToContainer(new SlotItemHandler(this.ingredientItems, 2, 62, 24));
        this.addSlotToContainer(new SlotItemHandler(this.ingredientItems, 3, 80, 24));
        this.addSlotToContainer(new SlotItemHandler(this.residueItems, 0, 26, 51)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });
        this.addSlotToContainer(new SlotItemHandler(this.residueItems, 1, 44, 51)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });
        this.addSlotToContainer(new SlotItemHandler(this.residueItems, 2, 62, 51)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });
        this.addSlotToContainer(new SlotItemHandler(this.residueItems, 3, 80, 51)
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
        this.tileEntity = (TileEntityDrinkMaker) tileEntity;
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

        if (index >= 0 && index < 4)
        {
            isMerged = mergeItemStack(newStack, 8, 44, true);
        }
        else if (index >= 4 && index < 8)
        {
            isMerged = mergeItemStack(newStack, 8, 44, true);
        }
        else if (index >= 8 && index < 35)
        {
            isMerged = mergeItemStack(newStack, 0, 4, false)
                    || mergeItemStack(newStack, 35, 44, false);
        }
        else if (index >= 35 && index < 44)
        {
            isMerged = mergeItemStack(newStack, 0, 4, false)
                    || mergeItemStack(newStack, 8, 35, false);
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

    public TileEntityDrinkMaker getTileEntity()
    {
        return this.tileEntity;
    }
}
