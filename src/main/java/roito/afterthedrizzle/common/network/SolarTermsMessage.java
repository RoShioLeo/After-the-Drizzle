package roito.afterthedrizzle.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import roito.afterthedrizzle.common.config.CommonConfig;
import roito.afterthedrizzle.common.environment.solar.BiomeTemperatureManager;
import roito.afterthedrizzle.common.environment.solar.SolarTerms;

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
            if (Minecraft.getInstance().world != null)
            {
                Minecraft.getInstance().world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).ifPresent(data ->
                {
                    data.setSolarTermsDay(solarDay);
                    ForgeRegistries.BIOMES.forEach(biome ->
                            biome.temperature = BiomeTemperatureManager.getDefaultTemperature(biome) + SolarTerms.get(data.getSolarTermIndex()).getTemperatureChange());
                    if (solarDay % CommonConfig.Season.lastingDaysOfEachTerm.get() == 0 && Minecraft.getInstance().player != null)
                    {
                        Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.solar_term.message", SolarTerms.get(data.getSolarTermIndex()).getTranslation()));
                    }
                });
            }
        });
    }
}
