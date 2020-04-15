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
import roito.afterthedrizzle.common.tileentity.DrinkMakerTileEntity;

import static roito.afterthedrizzle.common.inventory.ContainerTypesRegistry.DRINK_MAKER_CONTAINER;

public class DrinkMakerContainer extends NormalContainer
{
    private DrinkMakerTileEntity tileEntity;

    public DrinkMakerContainer(int windowId, PlayerInventory inv, BlockPos pos, World world)
    {
        super(DRINK_MAKER_CONTAINER, windowId);
        this.tileEntity = (DrinkMakerTileEntity) world.getTileEntity(pos);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 26, 24));
            addSlot(new SlotItemHandler(h, 1, 44, 24));
            addSlot(new SlotItemHandler(h, 2, 62, 24));
            addSlot(new SlotItemHandler(h, 3, 80, 24));
        });
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 26, 51));
            addSlot(new SlotItemHandler(h, 1, 44, 51));
            addSlot(new SlotItemHandler(h, 2, 62, 51));
            addSlot(new SlotItemHandler(h, 3, 80, 51));
        });
        addPlayerInventory(inv);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlocksRegistry.DRINK_MAKER);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
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

    public DrinkMakerTileEntity getTileEntity()
    {
        return tileEntity;
    }
}
