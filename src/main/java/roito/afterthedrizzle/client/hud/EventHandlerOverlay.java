package roito.afterthedrizzle.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.ItemsRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AfterTheDrizzle.MODID)
public final class EventHandlerOverlay
{
    public final static ThermometerBarRenderer bar0 = new ThermometerBarRenderer(Minecraft.getInstance());
    public final static RainGaugeBarRenderer bar1 = new RainGaugeBarRenderer(Minecraft.getInstance());
    public final static HygrometerBarRenderer bar2 = new HygrometerBarRenderer(Minecraft.getInstance());


    @SubscribeEvent(receiveCanceled = true)
    public static void onEvent(RenderGameOverlayEvent.Pre event)
    {
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
        if (playerEntity == null)
        {
            return;
        }
        else if (!playerEntity.getHeldItemMainhand().isEmpty())
        {
            if (playerEntity.getHeldItemMainhand().getItem().equals(ItemsRegistry.THERMOMETER))
            {
                bar0.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition()));
            }
            else if (playerEntity.getHeldItemMainhand().getItem().equals(ItemsRegistry.RAIN_GAUGE))
            {
                bar1.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getDownfall());
            }
            else if (playerEntity.getHeldItemMainhand().getItem().equals(ItemsRegistry.HYGROMETER))
            {
                bar2.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition()), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getDownfall());
            }
        }
    }
}
