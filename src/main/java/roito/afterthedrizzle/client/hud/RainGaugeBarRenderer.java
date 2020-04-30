package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.AfterTheDrizzle;

public class RainGaugeBarRenderer extends AbstractGui
{
    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/env.png");

    private final static int WIDTH = 31;
    private final static int HEIGHT = 5;

    private float rainfall = 0;

    private Minecraft mc;

    public RainGaugeBarRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, float rainfall)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlphaTest();

        if (this.rainfall == -1.0F)
        {
            this.rainfall = rainfall;
        }
        else
        {
            if (rainfall > this.rainfall)
            {
                this.rainfall += 0.0005F;
            }
            else if (rainfall < this.rainfall)
            {
                this.rainfall -= 0.0005F;
            }
        }

        mc.getTextureManager().bindTexture(OVERLAY_BAR);
        int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

        int width = Math.min((int) (29 * this.rainfall), 29);
        width = Math.max(width, 0);

        blit(offsetX + 1, offsetY + 1, 1, 0, width, HEIGHT - 2);
        blit(offsetX, offsetY, 0, 4, WIDTH, HEIGHT);
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
}
