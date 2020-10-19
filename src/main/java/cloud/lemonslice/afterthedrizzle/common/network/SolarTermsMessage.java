package cloud.lemonslice.afterthedrizzle.common.network;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import cloud.lemonslice.afterthedrizzle.common.config.ServerConfig;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.BiomeTemperatureManager;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.SolarTerm;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SolarTermsMessage implements INormalMessage
{
    int solarDay;

    public SolarTermsMessage(int solarDay)
    {
        this.solarDay = solarDay;
    }

    public SolarTermsMessage(PacketBuffer buf)
    {
        solarDay = buf.readInt();
    }

    @Override
    public void toBytes(PacketBuffer buf)
    {
        buf.writeInt(solarDay);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            {
                AfterTheDrizzle.proxy.getClientWorld().getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).ifPresent(data ->
                {
                    data.setSolarTermsDay(solarDay);
                    ForgeRegistries.BIOMES.forEach(biome ->
                            biome.temperature = BiomeTemperatureManager.getDefaultTemperature(biome) + SolarTerm.get(data.getSolarTermIndex()).getTemperatureChange());
                    if (solarDay % ServerConfig.Season.lastingDaysOfEachTerm.get() == 0 && Minecraft.getInstance().player != null)
                    {
                        Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("info.afterthedrizzle.environment.solar_term.message", SolarTerm.get(data.getSolarTermIndex()).getAlternationText()), false);
                    }
                });
            }
        });
    }
}
