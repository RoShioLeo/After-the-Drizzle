package roito.afterthedrizzle.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import roito.afterthedrizzle.common.tileentity.StoveTileEntity;

import static roito.afterthedrizzle.common.inventory.ContainerTypeRegistry.STOVE_CONTAINER;

public class StoveContainer extends NormalContainer
{
    private StoveTileEntity tileEntity;

    public StoveContainer(int windowId, PlayerInventory inv, BlockPos pos, World world)
    {
        super(STOVE_CONTAINER, windowId);
        this.tileEntity = (StoveTileEntity) world.getTileEntity(pos);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 80, 33));
        });
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 80, 61)
            {
                @Override
                public boolean isItemValid(ItemStack stack)
                {
                    return false;
                }
            });
        });
        addPlayerInventory(inv);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, tileEntity.getBlockState().getBlock());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        Slot slot = this.inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
        {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        boolean isMerged = false;

        if (index >= 0 && index < 2)
        {
            isMerged = mergeItemStack(newStack, 2, 38, true);
        }
        else if (index >= 2 && index < 29)
        {
            isMerged = mergeItemStack(newStack, 0, 2, false)
                    || mergeItemStack(newStack, 29, 38, false);
        }
        else if (index >= 29 && index < 38)
        {
            isMerged = mergeItemStack(newStack, 0, 2, false)
                    || mergeItemStack(newStack, 2, 29, false);
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

    public StoveTileEntity getTileEntity()
    {
        return tileEntity;
    }
}
