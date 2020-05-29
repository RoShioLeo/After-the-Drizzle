package roito.afterthedrizzle.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Function;

import static roito.afterthedrizzle.AfterTheDrizzle.MODID;
import static roito.afterthedrizzle.AfterTheDrizzle.NETWORK_VERSION;

public final class SimpleNetworkHandler
{
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "main")).networkProtocolVersion(() -> NETWORK_VERSION).serverAcceptedVersions(NETWORK_VERSION::equals).clientAcceptedVersions(NETWORK_VERSION::equals).simpleChannel();

    public static void init()
    {
        int id = 0;
        registerMessage(id++, PlayerTemperatureMessage.class, PlayerTemperatureMessage::new);
        registerMessage(id++, SolarTermsMessage.class, SolarTermsMessage::new);
    }

    private static <T extends INormalMessage> void registerMessage(int index, Class<T> messageType, Function<PacketBuffer, T> decoder)
    {
        CHANNEL.registerMessage(index, messageType, INormalMessage::toBytes, decoder, (message, context) ->
        {
            message.process(context);
            context.get().setPacketHandled(true);
        });
    }
}
