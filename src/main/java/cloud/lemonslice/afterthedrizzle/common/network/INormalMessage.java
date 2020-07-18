package cloud.lemonslice.afterthedrizzle.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface INormalMessage
{
    void toBytes(PacketBuffer buf);

    void process(Supplier<NetworkEvent.Context> context);
}
