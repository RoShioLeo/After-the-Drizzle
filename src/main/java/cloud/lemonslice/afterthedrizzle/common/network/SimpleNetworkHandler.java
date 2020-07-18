package cloud.lemonslice.afterthedrizzle.common.network;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Function;

public final class SimpleNetworkHandler
{
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(AfterTheDrizzle.MODID, "main")).networkProtocolVersion(() -> AfterTheDrizzle.NETWORK_VERSION).serverAcceptedVersions(AfterTheDrizzle.NETWORK_VERSION::equals).clientAcceptedVersions(AfterTheDrizzle.NETWORK_VERSION::equals).simpleChannel();

    public static void init()
    {
        int id = 0;
        registerMessage(id++, PlayerTemperatureMessage.class, PlayerTemperatureMessage::new);
        registerMessage(id++, SolarTermsMessage.class, SolarTermsMessage::new);
        registerMessage(id++, WeatherChangeMessage.class, WeatherChangeMessage::new);
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
