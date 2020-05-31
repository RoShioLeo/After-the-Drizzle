package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.config.ClientConfig;
import roito.afterthedrizzle.common.environment.temperature.ApparentTemperature;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AfterTheDrizzle.MODID)
public class PlayerTemperatureRenderer extends AbstractGui
{

    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/temperature.png");

    private static int index = -1;
    private static int up = 0;

    private final Minecraft mc;

    public PlayerTemperatureRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, CapabilityPlayerTemperature.Data t, int cold, int heat)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getTextureManager().bindTexture(OVERLAY_BAR);

        if (up == 0)
        {
            up = t.getHotterOrColder();
            if (up != 0)
            {
                index = 0;
            }
            else index = -1;
        }
        if (up != 0 && index >= 0)
        {
            blit((ClientConfig.GUI.playerTemperatureX.get()) + 26, screenHeight - ClientConfig.GUI.playerTemperatureY.get() + 7, index / 4 * 7, 48 + (up - 1) * 16, 7, 16);
            index %= 60;
            if (index == 0)
            {
                up = t.getHotterOrColder();
            }
        }

        ApparentTemperature temperature = ApparentTemperature.getTemperature(t.getTemperature());
        blit((ClientConfig.GUI.playerTemperatureX.get()), screenHeight - ClientConfig.GUI.playerTemperatureY.get(), (temperature.getIndex() - 1) * 30, 0, 30, 30);
        blit((ClientConfig.GUI.playerTemperatureX.get() + 32), screenHeight - ClientConfig.GUI.playerTemperatureY.get(), cold * 9, 30, 9, 9);
        blit((ClientConfig.GUI.playerTemperatureX.get() + 42), screenHeight - ClientConfig.GUI.playerTemperatureY.get(), heat * 9, 39, 9, 9);
        this.drawString(mc.fontRenderer, temperature.getTranslation().getFormattedText(), ClientConfig.GUI.playerTemperatureX.get() + 34, screenHeight - ClientConfig.GUI.playerTemperatureY.get() + 22, temperature.getColor().getColor());

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.START))
        {
            if (index >= 0)
            {
                index++;
            }
        }
        else if (Minecraft.getInstance().world != null && Minecraft.getInstance().world.getDayTime() % 60 == 0)
        {
            PlayerEntity player = Minecraft.getInstance().player;
            for (int x = player.chunkCoordX - 2; x <= player.chunkCoordX + 2; x++)
            {
                for (int z = player.chunkCoordZ - 2; z <= player.chunkCoordZ + 2; z++)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        Minecraft.getInstance().world.markSurroundingsForRerender(x, k, z);
                    }
                }
            }
        }
    }
}
