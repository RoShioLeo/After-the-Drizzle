package roito.afterthedrizzle.common.tileentity;

import net.minecraft.block.HorizontalBlock;
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
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.common.inventory.DrinkMakerContainer;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class DrinkMakerTileEntity extends NormalContainerTileEntity implements ITickableTileEntity
{
    private final LazyOptional<ItemStackHandler> ingredientsInventory = LazyOptional.of(() -> this.createItemHandler(4));
    private final LazyOptional<ItemStackHandler> residuesInventory = LazyOptional.of(() -> this.createItemHandler(4));
    private final LazyOptional<ItemStackHandler> containerInventory = LazyOptional.of(() -> this.createItemHandler(1));
    private final LazyOptional<ItemStackHandler> inputInventory = LazyOptional.of(() -> this.createItemHandler(1));
    private final LazyOptional<ItemStackHandler> outputInventory = LazyOptional.of(() -> this.createItemHandler(1));

    private int processTicks = 0;
    private static final int totalTicks = 200;

    public DrinkMakerTileEntity()
    {
        super(TileEntityTypesRegistry.DRINK_MAKER);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (!this.removed)
        {
            if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(cap))
            {
                if (side == Direction.DOWN)
                    return residuesInventory.cast();
                else
                    return ingredientsInventory.cast();
            }
            else if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(cap))
            {
                return getFluidHandler().cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT tag)
    {
        super.read(tag);
        this.ingredientsInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Ingredients")));
        this.residuesInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Residues")));
        this.containerInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Container")));
        this.inputInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Input")));
        this.outputInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Output")));
        this.processTicks = tag.getInt("ProcessTicks");
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        ingredientsInventory.ifPresent(h -> tag.put("Ingredients", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        residuesInventory.ifPresent(h -> tag.put("Residues", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        containerInventory.ifPresent(h -> tag.put("Container", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        inputInventory.ifPresent(h -> tag.put("Input", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        outputInventory.ifPresent(h -> tag.put("Output", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        tag.putInt("ProcessTicks", processTicks);
        return super.write(tag);
    }

    @Override
    public void tick()
    {
        if (this.getFluidAmount() != 0)
        {
            this.ingredientsInventory.ifPresent(inv ->
                    getFluidHandler().ifPresent(fluid ->
                    {
                        DrinkRecipeInput inventoryRecipe = DrinkRecipeInput.toRecipe(inv, fluid.getFluidInTank(0).getFluid());
                        FluidStack output = RecipesRegistry.MANAGER_DRINK_MAKER.getOutput(inventoryRecipe);
                        if (!output.isEmpty())
                        {
                            int n = (int) Math.ceil(this.getFluidAmount() * 1.0F / output.getAmount());
                            for (int i = 0; i < 4; i++)
                            {
                                if (!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getCount() < n)
                                {
                                    this.setToZero();
                                    return;
                                }
                            }
                            this.processTicks++;
                            this.markDirty();
                            if (this.processTicks >= totalTicks)
                            {
                                this.residuesInventory.ifPresent(h ->
                                {
                                    for (int i = 0; i < 4; i++)
                                    {
                                        ItemStack residue = inv.getStackInSlot(i).getContainerItem();
                                        inv.extractItem(i, n, false);
                                        if (!residue.isEmpty())
                                        {
                                            residue.setCount(n);
                                            h.insertItem(i, residue, false);
                                        }
                                    }
                                });
                                CompoundNBT nbt = new FluidStack(output.getFluid(), this.getFluidAmount()).writeToNBT(new CompoundNBT());
                                fluid.getContainer().getOrCreateTag().put(FLUID_NBT_KEY, nbt);
                                this.setToZero();
                            }
                        }
                        else
                        {
                            this.setToZero();
                        }
                        inputInventory.ifPresent(in ->
                                outputInventory.ifPresent(out ->
                                {
                                    {
                                        ItemStack inputCup = in.getStackInSlot(0);
                                        ItemStack outputCup = out.getStackInSlot(0);
                                        if (outputCup.isEmpty())
                                        {
                                            FluidActionResult filledSimulated = FluidUtil.tryFillContainer(inputCup, fluid, Integer.MAX_VALUE, null, true);
                                            if (filledSimulated.isSuccess())
                                            {
                                                ItemHandlerHelper.insertItemStacked(out, filledSimulated.getResult(), false);
                                                inputCup.shrink(1);
                                            }
                                        }
                                    }
                                }));
                    }));
        }
        else
        {
            this.setToZero();
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new DrinkMakerContainer(i, playerInventory, pos, world);
    }

    private ItemStackHandler createItemHandler(int size)
    {
        return new ItemStackHandler(size)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                DrinkMakerTileEntity.this.refresh();
                DrinkMakerTileEntity.this.markDirty();
                super.onContentsChanged(slot);
            }
        };
    }

    public void setToZero()
    {
        processTicks = 0;
        this.markDirty();
    }

    public int getProcessTicks()
    {
        return processTicks;
    }

    public int getTotalTicks()
    {
        return totalTicks;
    }

    public int getFluidAmount()
    {
        return getFluidHandler().map(h -> h.getFluidInTank(0).getAmount()).orElse(0);
    }

    @Nullable
    public String getFluidTranslation()
    {
        return getFluidHandler().map(h -> h.getFluidInTank(0).getDisplayName().getFormattedText()).orElse(null);
    }

    public LazyOptional<IFluidHandlerItem> getFluidHandler()
    {
        return this.containerInventory.map(h -> FluidUtil.getFluidHandler(h.getStackInSlot(0))).orElse(LazyOptional.empty());
    }

    public LazyOptional<ItemStackHandler> getContainerInventory()
    {
        return containerInventory;
    }

    public LazyOptional<ItemStackHandler> getInputInventory()
    {
        return inputInventory;
    }

    public LazyOptional<ItemStackHandler> getOutputInventory()
    {
        return outputInventory;
    }

    public List<ItemStack> getContent()
    {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            int index = i;
            ingredientsInventory.ifPresent(h -> list.add(h.getStackInSlot(index)));
        }
        return list;
    }

    public Direction getFacing()
    {
        return this.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
    }
}
