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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.inventory.DrinkMakerContainer;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DrinkMakerTileEntity extends NormalContainerTileEntity implements ITickableTileEntity
{
    private final LazyOptional<ItemStackHandler> ingredientsInventory = LazyOptional.of(this::createItemHandler);
    private final LazyOptional<ItemStackHandler> residuesInventory = LazyOptional.of(this::createItemHandler);
    private final LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createFluidHandler);

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
                return fluidTank.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT tag)
    {
        super.read(tag);
        this.fluidTank.ifPresent(f -> f.readFromNBT(tag.getCompound("FluidTank")));
        this.ingredientsInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Ingredients")));
        this.residuesInventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("Residues")));
        this.processTicks = tag.getInt("ProcessTicks");
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        fluidTank.ifPresent(f -> tag.put("FluidTank", f.writeToNBT(new CompoundNBT())));
        ingredientsInventory.ifPresent(h -> tag.put("Ingredients", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        residuesInventory.ifPresent(h -> tag.put("Residues", ((INBTSerializable<CompoundNBT>) h).serializeNBT()));
        tag.putInt("ProcessTicks", processTicks);
        return super.write(tag);
    }

    @Override
    public void tick()
    {
        if (this.getFluidAmount() != 0)
        {
            this.ingredientsInventory.ifPresent(inv ->
                    this.fluidTank.ifPresent(fluid ->
                    {
                        DrinkRecipeInput inventoryRecipe = DrinkRecipeInput.toRecipe(inv, this.getFluidTank().getFluid().getFluid());
                        FluidStack output = RecipesRegistry.MANAGER_DRINK_MAKER.getOutput(inventoryRecipe);
                        if (!output.isEmpty())
                        {
                            int n = (int) Math.ceil(this.getFluidAmount() / output.getAmount());
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
                                fluid.setFluid(new FluidStack(output.getFluid(), this.getFluidAmount()));
                                this.setToZero();
                            }
                        }
                        else
                        {
                            this.setToZero();
                        }
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

    private ItemStackHandler createItemHandler()
    {
        return new ItemStackHandler(4)
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

    private FluidTank createFluidHandler()
    {
        return new FluidTank(NormalConfig.drinkMakerCapacity.get())
        {
            @Override
            protected void onContentsChanged()
            {
                DrinkMakerTileEntity.this.refresh();
                DrinkMakerTileEntity.this.markDirty();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack)
            {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
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
        return getFluidTank().getFluidAmount();
    }

    @Nullable
    public String getFluidTranslation()
    {
        return getFluidTank().getFluid().getDisplayName().getFormattedText();
    }

    public FluidTank getFluidTank()
    {
        return this.fluidTank.orElse(new FluidTank(0));
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
