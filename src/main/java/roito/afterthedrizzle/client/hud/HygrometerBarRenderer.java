package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.environment.Humidity;

public class HygrometerBarRenderer extends AbstractGui
{
    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/env.png");

    private final static int WIDTH = 31;
    private final static int HEIGHT = 5;

    private float humidity = 0;

    private Minecraft mc;

    public HygrometerBarRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, float temperature, float rainfall)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlphaTest();

        int level = Humidity.getHumid(rainfall, temperature).getId();

        if (this.humidity == -1.0F)
        {
            this.humidity = level;
        }
        else
        {
            if (level > this.humidity)
            {
                this.humidity += 0.005F;
            }
            else if (level < this.humidity)
            {
                this.humidity -= 0.005F;
            }
        }

        mc.getTextureManager().bindTexture(OVERLAY_BAR);
        int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

        int width = (int) (this.humidity * 6);

        blit(offsetX + 1, offsetY + 1, 1, 20, width, HEIGHT - 2);
        blit(offsetX, offsetY, 0, 24, WIDTH, HEIGHT);
        GlStateManager.disableAlphaTest();
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }
}
