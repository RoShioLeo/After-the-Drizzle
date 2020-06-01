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
import roito.afterthedrizzle.common.environment.temperature.Temperature;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AfterTheDrizzle.MODID)
public class ThermometerBarRenderer extends AbstractGui
{
    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/env.png");

    private final static int WIDTH = 31;
    private final static int HEIGHT = 5;

    private static float temp = 0;
    private static float level = 0;

    private final Minecraft mc;

    public ThermometerBarRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, float temp)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        level = temp;

        mc.getTextureManager().bindTexture(OVERLAY_BAR);
        int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

        int width = getWidth(ThermometerBarRenderer.temp);

        blit(offsetX + 1, offsetY + 1, 1, 10, width, HEIGHT - 2);
        blit(offsetX, offsetY, 0, 14, WIDTH, HEIGHT);

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }

    public int getWidth(float temp)
    {
        Temperature t = Temperature.getTemperatureLevel(temp);
        if (temp <= 0)
        {
            return 0;
        }
        else if (temp <= Temperature.FREEZING.getMax())
        {
            return (int) (5 * temp);
        }
        else if (temp > Temperature.HEAT.getMin())
        {
            temp -= t.getMin();
            int id = t.getId() - 1;
            int width = id * 5;
            width += 5 * temp / 0.35F;
            return width;
        }
        else
        {
            temp -= t.getMin();
            int id = t.getId() - 1;
            int width = id * 5;
            width += 5 * temp / t.getWidth();
            return width;
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (level > temp)
        {
            temp += 0.01F;
        }
        else if (level < temp)
        {
            temp -= 0.01F;
        }
    }
}
