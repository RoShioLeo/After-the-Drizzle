package roito.afterthedrizzle.common.tileentity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public abstract class TileEntitySingleFluidTank extends TileEntity
{
    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    public void syncToTrackingClients()
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

    public abstract FluidTank getFluidTank();

    public int getFluidAmount()
    {
        return getFluidTank().getFluidAmount();
    }

    @Nullable
    public String getFluidName()
    {
        if (getFluidAmount() == 0)
        {
            return null;
        }
        else return FluidRegistry.getFluidName(getFluidTank().getFluid());
    }

    @Nullable
    public String getFluidTranslation()
    {
        if (getFluidTank().getFluid() != null)
        {
            return getFluidTank().getFluid().getLocalizedName();
        }
        else return null;
    }

    public void markDirty()
    {
        super.markDirty();
        this.syncToTrackingClients();
    }
}
