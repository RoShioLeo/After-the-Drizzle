package roito.afterthedrizzle.common.tileentity;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static roito.afterthedrizzle.common.tileentity.TileEntityTypesRegistry.TEAPOT;

public class TeapotTileEntity extends TileEntity
{
    private final LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createFluidHandler);

    public TeapotTileEntity()
    {
        super(TEAPOT);
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.read(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbtTag = new CompoundNBT();
        this.write(nbtTag);
        return new SUpdateTileEntityPacket(getPos(), 1, nbtTag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (!this.removed)
        {
            if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(cap))
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
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        fluidTank.ifPresent(f -> tag.put("FluidTank", f.writeToNBT(new CompoundNBT())));
        return super.write(tag);
    }

    private FluidTank createFluidHandler()
    {
        return new FluidTank(1000)
        {
            @Override
            protected void onContentsChanged()
            {
                TeapotTileEntity.this.markDirty();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack)
            {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }

    public FluidTank getFluidTank()
    {
        return this.fluidTank.orElse(new FluidTank(0));
    }

    public Fluid getFluid()
    {
        return this.fluidTank.map(f -> f.getFluid().getFluid()).orElse(Fluids.EMPTY);
    }

    public int getFluidAmount()
    {
        return getFluidTank().getFluidAmount();
    }

    public void setFluidTank(FluidStack stack)
    {
        this.fluidTank.ifPresent(f -> f.setFluid(stack));
    }

    public void setFluid(Fluid fluid)
    {
        this.fluidTank.ifPresent(f -> f.setFluid(new FluidStack(fluid, getFluidAmount())));
    }
}
