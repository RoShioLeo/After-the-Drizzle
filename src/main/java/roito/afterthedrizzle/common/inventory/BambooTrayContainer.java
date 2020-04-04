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
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.tileentity.BambooTrayTileEntity;

import static roito.afterthedrizzle.common.inventory.ContainerTypeRegistry.BAMBOO_TRAY_CONTAINER;

public class BambooTrayContainer extends NormalContainer
{
    private BambooTrayTileEntity tileEntity;

    public BambooTrayContainer(int windowId, PlayerInventory inv, BlockPos pos, World world)
    {
        super(BAMBOO_TRAY_CONTAINER, windowId);
        this.tileEntity = (BambooTrayTileEntity) world.getTileEntity(pos);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(h ->
                addSlot(new SlotItemHandler(h, 0, 107, 31)));
        addPlayerInventory(inv);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlocksRegistry.BAMBOO_TRAY);
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

        if (index >= 0 && index < 1)
        {
            isMerged = mergeItemStack(newStack, 1, 37, true);
        }
        else if (index >= 1 && index < 28)
        {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 29, 37, false);
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

    public BambooTrayTileEntity getTileEntity()
    {
        return tileEntity;
    }
}
