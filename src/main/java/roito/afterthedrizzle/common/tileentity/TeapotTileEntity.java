package roito.afterthedrizzle.common.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import roito.afterthedrizzle.common.config.NormalConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static roito.afterthedrizzle.common.tileentity.TileEntityTypesRegistry.TEAPOT;

public class TeapotTileEntity extends TileEntity
{
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createFluidHandler);

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
        return new FluidTank(NormalConfig.drinkMakerCapacity.get())
        {
            @Override
            protected void onContentsChanged()
            {
                TeapotTileEntity.this.refresh();
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
        this.refresh();
    }

    public void refresh()
    {
        if (this.hasWorld() && !this.world.isRemote)
        {
            BlockState state = this.world.getBlockState(pos);
            this.world.markAndNotifyBlock(pos, null, state, state, 11);

            SUpdateTileEntityPacket packet = this.getUpdatePacket();
            Stream<ServerPlayerEntity> playerEntity = ((ServerWorld) this.world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(this.pos.getX() >> 4, this.pos.getZ() >> 4), false);
            for (ServerPlayerEntity player : playerEntity.collect(Collectors.toList()))
            {
                player.connection.sendPacket(packet);
            }
        }
    }
}
