package roito.cultivage.common.tileentity;

import net.minecraft.block.state.IBlockState;
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
import roito.cultivage.api.block.IBlockStove;
import roito.cultivage.api.environment.Humidity;
import roito.cultivage.common.block.ModeFlatBasket;
import roito.cultivage.common.config.ConfigMain;
import roito.cultivage.registry.RecipesRegistry;
import roito.silveroakoutpost.helper.NonNullListHelper;
import roito.silveroakoutpost.recipe.ISingleInRecipe;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;
import roito.silveroakoutpost.recipe.SingleInRecipe;

public class TileEntityFlatBasket extends TileEntity implements ITickable
{
	protected ItemStackHandler containerInventory = new ItemStackHandler();

	protected int processTicks = 0;
	protected int totalTicks = 0;

	protected int randomSeed = 0;

	protected ModeFlatBasket mode = ModeFlatBasket.OUTDOORS;

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
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		containerInventory.deserializeNBT(compound.getCompoundTag("ContainerInventory"));
		processTicks = compound.getInteger("ProcessTicks");
		totalTicks = compound.getInteger("TotalTicks");
		mode = ModeFlatBasket.values()[compound.getInteger("Mode")];
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("ContainerInventory", containerInventory.serializeNBT());
		compound.setInteger("ProcessTick", processTicks);
		compound.setInteger("TotalTick", totalTicks);
		compound.setInteger("Mode", mode.ordinal());
		return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound data)
	{
		readFromNBT(data);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		refreshSeed();
		return new SPacketUpdateTileEntity(pos, 1, writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			float temp = world.getBiome(pos).getTemperature(pos);
			float rainfall = world.getBiome(pos).getRainfall();
			switch (ModeFlatBasket.getMode(world, pos))
			{
				case IN_RAIN:
					refreshTotalTicks(0);
					getWet();
					return;
				case OUTDOORS:
					refreshTotalTicks(Humidity.getHumid(rainfall, temp).getOutdoorDryingTicks());
					process(RecipesRegistry.MANAGER_BASKET_OUTDOORS);
					return;
				case INDOORS:
					refreshTotalTicks(Humidity.getHumid(rainfall, temp).getIndoorDryingTicks());
					process(RecipesRegistry.MANAGER_BASKET_INDOORS);
					return;
				case BAKE:
					refreshTotalTicks(ConfigMain.time.BakeBasicTime);
					process(RecipesRegistry.MANAGER_BASKET_BAKE);
			}
		}
	}

	private void getWet()
	{
		ItemStack input = getInput();
		processTicks = 0;
		if (!currentRecipe.isTheSameInput(input) || mode != ModeFlatBasket.getMode(world, pos))
		{
			currentRecipe = RecipesRegistry.MANAGER_BASKET_IN_RAIN.getRecipe(input);
			mode = ModeFlatBasket.getMode(world, pos);
		}
		if (!getOutput().isEmpty())
		{
			ItemStack wetOutput = getOutput().copy();
			wetOutput.setCount(input.getCount());
			containerInventory.setStackInSlot(0, wetOutput);
			refresh();
		}
		markDirty();
	}

	private boolean process(ISingleInRecipeManager recipeManager)
	{
		ItemStack input = getInput();
		if (input.isEmpty())
		{
			processTicks = 0;
			markDirty();
			return false;
		}
		if (!currentRecipe.isTheSameInput(input) || mode != ModeFlatBasket.getMode(world, pos))
		{
			currentRecipe = recipeManager.getRecipe(input);
			mode = ModeFlatBasket.getMode(world, pos);
		}
		if (!getOutput().isEmpty())
		{
			if (mode == ModeFlatBasket.BAKE)
			{
				processTicks += ((IBlockStove) getWorld().getBlockState(pos.down()).getBlock()).getFuelPower();
			}
			else
			{
				processTicks++;
			}
			if (processTicks >= totalTicks)
			{
				ItemStack output = getOutput();
				output.setCount(input.getCount());
				containerInventory.setStackInSlot(0, output);
				refresh();
				processTicks = 0;
			}
			markDirty();
			return true;
		}
		processTicks = 0;
		markDirty();
		return false;
	}

	private void refreshTotalTicks(int basicTicks)
	{
		totalTicks = getInput().getCount() * basicTicks;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}

	private void refresh()
	{
		if (hasWorld() && !world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);
			world.markAndNotifyBlock(pos, null, state, state, 11);
		}
	}

	public ItemStack getInput()
	{
		return containerInventory.getStackInSlot(0).copy();
	}

	public ItemStack getOutput()
	{
		return currentRecipe.getOutput().copy();
	}

	public int getTotalTicks()
	{
		return totalTicks;
	}

	public int getProcessTicks()
	{
		return processTicks;
	}

	public ModeFlatBasket getMode()
	{
		return mode;
	}

	public NonNullList<ItemStack> getContents()
	{
		NonNullList<ItemStack> list = NonNullList.create();
		ItemStack con = containerInventory.getStackInSlot(0).copy();
		con.setCount(1);
		for (int i = getInput().getCount(); i > 0; i -= 16)
		{
			list.add(con);
		}
		return list;
	}

	public int getRandomSeed()
	{
		return randomSeed;
	}

	public void refreshSeed()
	{
		randomSeed = (int) (Math.random() * 10000);
	}

	public boolean isEmpty()
	{
		return getInput().isEmpty();
	}

	public boolean isCompleted()
	{
		return getOutput().isEmpty();
	}

	public void mark()
	{
		markDirty();
		refresh();
	}
}
