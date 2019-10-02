package roito.afterthedrizzle.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.api.block.IBlockStove;
import roito.afterthedrizzle.api.environment.Humidity;
import roito.afterthedrizzle.common.block.ModeFlatBasket;
import roito.afterthedrizzle.common.config.ConfigMain;
import roito.afterthedrizzle.registry.RecipesRegistry;
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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.containerInventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.containerInventory.deserializeNBT(compound.getCompoundTag("ContainerInventory"));
        this.processTicks = compound.getInteger("ProcessTicks");
        this.totalTicks = compound.getInteger("TotalTicks");
        this.mode = ModeFlatBasket.values()[compound.getInteger("Mode")];
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
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound data)
    {
        this.readFromNBT(data);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        this.refreshSeed();
        return new SPacketUpdateTileEntity(this.pos, 1, this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void update()
    {
        float temp = this.world.getBiome(pos).getTemperature(pos);
        float rainfall = this.world.getBiome(pos).getRainfall();
        mode = ModeFlatBasket.getMode(this.world, this.pos);
        switch (mode)
        {
            case IN_RAIN:
                this.refreshTotalTicks(0);
                this.getWet();
                return;
            case OUTDOORS:
                this.refreshTotalTicks(Humidity.getHumid(rainfall, temp).getOutdoorDryingTicks());
                this.process(RecipesRegistry.MANAGER_BASKET_OUTDOORS);
                return;
            case INDOORS:
                this.refreshTotalTicks(Humidity.getHumid(rainfall, temp).getIndoorDryingTicks());
                this.process(RecipesRegistry.MANAGER_BASKET_INDOORS);
                return;
            case BAKE:
                this.refreshTotalTicks(ConfigMain.time.bakeBasicTime);
                this.process(RecipesRegistry.MANAGER_BASKET_BAKE);
        }
    }

    private void getWet()
    {
        ItemStack input = this.getInput();
        this.processTicks = 0;
        if (!this.currentRecipe.isTheSameInput(input))
        {
            this.currentRecipe = RecipesRegistry.MANAGER_BASKET_IN_RAIN.getRecipe(input);
        }
        if (!getOutput().isEmpty())
        {
            ItemStack wetOutput = this.getOutput().copy();
            wetOutput.setCount(input.getCount());
            this.containerInventory.setStackInSlot(0, wetOutput);
            this.syncToTrackingClients();
        }
        this.markDirty();
    }

    private boolean process(ISingleInRecipeManager recipeManager)
    {
        ItemStack input = getInput();
        if (input.isEmpty())
        {
            this.processTicks = 0;
            this.markDirty();
            return false;
        }
        if (!this.currentRecipe.isTheSameInput(input))
        {
            this.currentRecipe = recipeManager.getRecipe(input);
        }
        if (!this.getOutput().isEmpty())
        {
            if (this.mode == ModeFlatBasket.BAKE)
            {
                this.processTicks += ((IBlockStove) this.getWorld().getBlockState(pos.down()).getBlock()).getFuelPower();
            }
            else
            {
                this.processTicks++;
            }
            if (this.processTicks >= this.totalTicks)
            {
                ItemStack output = this.getOutput();
                output.setCount(input.getCount());
                this.containerInventory.setStackInSlot(0, output);
                this.syncToTrackingClients();
                this.processTicks = 0;
            }
            this.markDirty();
            return true;
        }
        this.processTicks = 0;
        this.markDirty();
        return false;
    }

    private void refreshTotalTicks(int basicTicks)
    {
        this.totalTicks = this.getInput().getCount() * basicTicks;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }

    private void syncToTrackingClients()
    {
        if (!this.world.isRemote)
        {
            SPacketUpdateTileEntity packet = this.getUpdatePacket();
            PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
            if (trackingEntry != null)
            {
                for (EntityPlayerMP player : trackingEntry.getWatchingPlayers())
                {
                    player.connection.sendPacket(packet);
                }
            }
        }
    }

    public ItemStack getInput()
    {
        return this.containerInventory.getStackInSlot(0).copy();
    }

    public ItemStack getOutput()
    {
        return this.currentRecipe.getOutput().copy();
    }

    public int getTotalTicks()
    {
        return this.totalTicks;
    }

    public int getProcessTicks()
    {
        return this.processTicks;
    }

    public ModeFlatBasket getMode()
    {
        return this.mode;
    }

    public NonNullList<ItemStack> getContents()
    {
        NonNullList<ItemStack> list = NonNullList.create();
        ItemStack con = this.containerInventory.getStackInSlot(0).copy();
        con.setCount(1);
        for (int i = this.getInput().getCount(); i > 0; i -= 16)
        {
            list.add(con);
        }
        return list;
    }

    public int getRandomSeed()
    {
        return this.randomSeed;
    }

    public void refreshSeed()
    {
        this.randomSeed = (int) (Math.random() * 10000);
    }

    public boolean isEmpty()
    {
        return this.getInput().isEmpty();
    }

    public boolean isCompleted()
    {
        return this.getOutput().isEmpty();
    }

    public void mark()
    {
        this.markDirty();
        this.syncToTrackingClients();
    }
}
