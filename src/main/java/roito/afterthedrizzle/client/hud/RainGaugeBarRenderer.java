package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import roito.afterthedrizzle.AfterTheDrizzle;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AfterTheDrizzle.MODID)
public class RainGaugeBarRenderer extends AbstractGui
{
    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/env.png");

    private final static int WIDTH = 31;
    private final static int HEIGHT = 5;

    private static float rainfall = 0;
    private static float level = 0;

    private final Minecraft mc;

    public RainGaugeBarRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, float rainfall)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        level = rainfall;

        mc.getTextureManager().bindTexture(OVERLAY_BAR);
        int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

        int width = getWidth(RainGaugeBarRenderer.rainfall);

        blit(offsetX + 1, offsetY + 1, 1, 0, width, HEIGHT - 2);
        blit(offsetX, offsetY, 0, 4, WIDTH, HEIGHT);

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }

    public int getWidth(float rainfall)
    {
        int width = 0;

        if (rainfall <= 0)
        {
            return width;
        }
        if (rainfall <= 1.0F)
        {
            return width + (int) ((WIDTH - 2) * rainfall);
        }
        else
        {
            return WIDTH - 2;
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.START))
        {
            if (level > rainfall)
            {
                rainfall += 0.02F;
            }
            else if (level < rainfall)
            {
                rainfall -= 0.02F;
            }
        }
    }
}
