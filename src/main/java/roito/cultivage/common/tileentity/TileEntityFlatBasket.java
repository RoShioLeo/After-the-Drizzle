package roito.cultivage.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.silveroakoutpost.helper.NonNullListHelper;
import roito.silveroakoutpost.recipe.ISingleInRecipe;
import roito.silveroakoutpost.recipe.SingleInRecipe;

public class TileEntityFlatBasket extends TileEntity implements ITickable
{
	protected ItemStackHandler containerInventory = new ItemStackHandler();

	protected ISingleInRecipe currentRecipe = new SingleInRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY);

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
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(containerInventory);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void update()
	{

	}
}
