package roito.afterthedrizzle.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;

import java.util.function.Supplier;

public class PlayerTemperatureMessage implements INormalMessage
{
    int playerTemperature;
    int up;

    public PlayerTemperatureMessage(int temp, int up)
    {
        this.playerTemperature = temp;
        this.up = up;
    }

    public PlayerTemperatureMessage(PacketBuffer buf)
    {
        playerTemperature = buf.readInt();
        up = buf.readInt();
    }

    @Override
    public void toBytes(PacketBuffer buf)
    {
        buf.writeInt(playerTemperature);
        buf.writeInt(up);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t ->
        {
            t.setPlayerTemperature(playerTemperature);
            t.setHotterOrColder(up);
        }));
    }
}
