package roito.afterthedrizzle.common.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.common.block.BambooTrayMode;
import roito.afterthedrizzle.common.block.IStoveBlock;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.inventory.BambooTrayContainer;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.recipe.bamboo_tray.BambooTaryRecipe;
import roito.afterthedrizzle.common.recipe.bamboo_tray.IBambooTrayRecipeManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BambooTrayTileEntity extends NormalContainerTileEntity implements ITickableTileEntity
{
    private int processTicks = 0;
    private int totalTicks = 0;

    private int randomSeed = new Random().nextInt(943943);
    private int doubleClickTicks = 0;

    private BambooTrayMode mode = BambooTrayMode.OUTDOORS;

    private LazyOptional<ItemStackHandler> containerInventory = LazyOptional.of(this::createHandler);
    private BambooTaryRecipe currentRecipe = new BambooTaryRecipe(ItemStack.EMPTY, ItemStack.EMPTY);

    public BambooTrayTileEntity()
    {
        super(TileEntityTypeRegistry.BAMBOO_TRAY);
    }

    @Override
    public void read(CompoundNBT tag)
    {
        super.read(tag);
        this.containerInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Inv")));
        this.processTicks = tag.getInt("ProcessTicks");
        this.totalTicks = tag.getInt("TotalTicks");
        this.mode = BambooTrayMode.values()[tag.getInt("Mode")];
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        containerInventory.ifPresent(h -> tag.put("Inv", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        tag.putInt("ProcessTicks", processTicks);
        tag.putInt("TotalTicks", totalTicks);
        tag.putInt("Mode", mode.ordinal());
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (!this.removed && side != null && CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(cap))
        {
            if (side != Direction.DOWN || !this.isWorking())
                return containerInventory.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            if (doubleClickTicks > 0)
            {
                doubleClickTicks--;
            }
            float temp = this.world.getBiome(pos).getTemperature(pos);
            float rainfall = this.world.getBiome(pos).getDownfall();
            switch (BambooTrayMode.getMode(this.world, this.pos))
            {
                case IN_RAIN:
                    this.refreshTotalTicks(0);
                    this.getWet();
                    this.mode = BambooTrayMode.IN_RAIN;
                    return;
                case OUTDOORS:
                    this.refreshTotalTicks(Humidity.getHumid(rainfall, temp).getOutdoorDryingTicks());
                    if (!this.isWorldRaining())
                        this.process(RecipesRegistry.MANAGER_BAMBOO_TRAY_OUTDOORS);
                    this.mode = BambooTrayMode.OUTDOORS;
                    return;
                case INDOORS:
                    this.refreshTotalTicks(Humidity.getHumid(rainfall, temp).getFermentationTicks());
                    this.process(RecipesRegistry.MANAGER_BAMBOO_TRAY_INDOORS);
                    this.mode = BambooTrayMode.INDOORS;
                    return;
                case BAKE:
                    this.refreshTotalTicks(NormalConfig.bakeBasicTime.get());
                    this.process(RecipesRegistry.MANAGER_BAMBOO_TRAY_BAKE);
                    this.mode = BambooTrayMode.BAKE;
                    return;
                case PROCESS:
                    //TODO
                    this.mode = BambooTrayMode.PROCESS;
            }
        }
    }

    private void getWet()
    {
        ItemStack input = this.getInput();
        if (!this.currentRecipe.isTheSameInput(input) || mode != getMode())
        {
            this.currentRecipe = RecipesRegistry.MANAGER_BAMBOO_TRAY_IN_RAIN.getRecipe(input);
        }
        if (!getOutput().isEmpty())
        {
            ItemStack wetOutput = this.getOutput().copy();
            wetOutput.setCount(input.getCount());
            this.containerInventory.ifPresent(inv ->
                    inv.setStackInSlot(0, wetOutput));
        }
        setToZero();
    }

    private boolean process(IBambooTrayRecipeManager recipeManager)
    {
        ItemStack input = getInput();
        if (input.isEmpty())
        {
            setToZero();
            return false;
        }
        if (!this.currentRecipe.isTheSameInput(input) || mode != BambooTrayMode.getMode(this.world, this.pos))
        {
            this.currentRecipe = recipeManager.getRecipe(input);
        }
        if (!this.getOutput().isEmpty())
        {
            if (this.mode == BambooTrayMode.BAKE)
            {
                this.processTicks += ((IStoveBlock) this.getWorld().getBlockState(pos.down()).getBlock()).getFuelPower();
            }
            else
            {
                this.processTicks++;
            }
            if (this.processTicks >= this.totalTicks)
            {
                ItemStack output = this.getOutput();
                output.setCount(input.getCount());
                this.containerInventory.ifPresent(inv ->
                        inv.setStackInSlot(0, output));
                this.processTicks = 0;
            }
            this.markDirty();
            return true;
        }
        setToZero();
        return false;
    }

    public ItemStack getInput()
    {
        return this.containerInventory.orElse(new ItemStackHandler()).getStackInSlot(0).copy();
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

    public BambooTrayMode getMode()
    {
        return this.mode;
    }

    public int getRandomSeed()
    {
        return this.randomSeed;
    }

    public void refreshSeed()
    {
        this.randomSeed = (int) (Math.random() * 10000);
    }

    public boolean isWorldRaining()
    {
        return this.getWorld().isRaining();
    }

    public boolean isWorking()
    {
        return !this.currentRecipe.getOutput().isEmpty();
    }

    public void singleClickStart()
    {
        this.doubleClickTicks = 10;
        this.markDirty();
    }

    public boolean isDoubleClick()
    {
        return doubleClickTicks > 0;
    }

    private void refreshTotalTicks(int basicTicks)
    {
        this.totalTicks = this.getInput().getCount() * basicTicks;
    }

    private void setToZero()
    {
        this.processTicks = 0;
        this.markDirty();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new BambooTrayContainer(i, playerInventory, pos, world);
    }

    public static IBambooTrayRecipeManager getRecipeManager(BambooTrayMode mode)
    {
        switch (mode)
        {
            case OUTDOORS:
                return RecipesRegistry.MANAGER_BAMBOO_TRAY_OUTDOORS;
            case INDOORS:
                return RecipesRegistry.MANAGER_BAMBOO_TRAY_INDOORS;
            case BAKE:
                return RecipesRegistry.MANAGER_BAMBOO_TRAY_BAKE;
            case IN_RAIN:
                return RecipesRegistry.MANAGER_BAMBOO_TRAY_IN_RAIN;
            default:
                return RecipesRegistry.MANAGER_BAMBOO_TRAY_PROCESS;
        }
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler()
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                BambooTrayTileEntity.this.refresh();
                BambooTrayTileEntity.this.markDirty();
                super.onContentsChanged(slot);
            }
        };
    }
}
