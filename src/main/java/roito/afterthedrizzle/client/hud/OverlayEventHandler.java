package roito.afterthedrizzle.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import roito.afterthedrizzle.common.config.ClientConfig;
import roito.afterthedrizzle.common.config.CommonConfig;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.environment.temperature.PlayerTemperatureHandler;
import roito.afterthedrizzle.common.handler.AsmHandler;
import roito.afterthedrizzle.common.item.ItemsRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AfterTheDrizzle.MODID)
public final class OverlayEventHandler
{
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");

    private final static ThermometerBarRenderer BAR_0 = new ThermometerBarRenderer(Minecraft.getInstance());
    private final static RainGaugeBarRenderer BAR_1 = new RainGaugeBarRenderer(Minecraft.getInstance());
    private final static HygrometerBarRenderer BAR_2 = new HygrometerBarRenderer(Minecraft.getInstance());
    private final static PlayerTemperatureRenderer BAR_3 = new PlayerTemperatureRenderer(Minecraft.getInstance());
    private final static DebugInfoRenderer BAR_4 = new DebugInfoRenderer(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onEvent(RenderGameOverlayEvent.Pre event)
    {
        ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;
        if (clientPlayer != null)
        {
            if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
            {
                if (!clientPlayer.getHeldItemMainhand().isEmpty())
                {
                    if (clientPlayer.getHeldItemMainhand().getItem().equals(ItemsRegistry.THERMOMETER))
                    {
                        float temp = clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getTemperature(clientPlayer.getPosition());
                        Humidity h = Humidity.getHumid(clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getDownfall(), temp);
                        if (clientPlayer.getEntityWorld().getDimension().getType().hasSkyLight())
                        {
                            temp = PlayerTemperatureHandler.getEnvOriginTemp(clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getTemperature(clientPlayer.getPosition()), h, clientPlayer.getEntityWorld());
                        }
                        BAR_0.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), temp);
                    }
                    else if (clientPlayer.getHeldItemMainhand().getItem().equals(ItemsRegistry.RAIN_GAUGE))
                    {
                        BAR_1.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getDownfall());
                    }
                    else if (clientPlayer.getHeldItemMainhand().getItem().equals(ItemsRegistry.HYGROMETER))
                    {
                        BAR_2.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getTemperature(clientPlayer.getPosition()), clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getDownfall());
                    }
                }
            }
            if (CommonConfig.Temperature.enable.get() && event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
            {
                clientPlayer.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t ->
                        BAR_3.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), t, PlayerTemperatureHandler.getResistancePoint(clientPlayer, "Cold"), PlayerTemperatureHandler.getResistancePoint(clientPlayer, "Heat")));
            }
            if (ClientConfig.GUI.debugInfo.get())
            {
                int solar = clientPlayer.world.getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).orElse(new CapabilitySolarTermTime.Data()).getSolarTermsDay();
                long dayTime = clientPlayer.world.getWorldInfo().getDayTime();
                float temp = clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getTemperature(clientPlayer.getPosition());
                Humidity h = Humidity.getHumid(clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getDownfall(), temp);
                double env = PlayerTemperatureHandler.getEnvOriginTemp(clientPlayer.getEntityWorld().getBiome(clientPlayer.getPosition()).getTemperature(clientPlayer.getPosition()), h, clientPlayer.getEntityWorld());
                int solarTime = AsmHandler.getSolarAngelTime(clientPlayer.getEntityWorld().getDayTime(), 0, clientPlayer.getEntityWorld());
                BAR_4.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), solar, dayTime, env, solarTime);
            }
        }
    }
}
