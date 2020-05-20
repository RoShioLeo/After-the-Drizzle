package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.environment.temperature.Temperature;

public class ThermometerBarRenderer extends AbstractGui
{
    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/env.png");

    private final static int WIDTH = 31;
    private final static int HEIGHT = 5;

    private float temp = 0;

    private Minecraft mc;

    public ThermometerBarRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, float temp)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlphaTest();

        if (this.temp == -1.0F)
        {
            this.temp = temp;
        }
        else
        {
            if (temp > this.temp)
            {
                this.temp += 0.0005F;
            }
            else if (temp < this.temp)
            {
                this.temp -= 0.0005F;
            }
        }

        mc.getTextureManager().bindTexture(OVERLAY_BAR);
        int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

        int width = getWidth(this.temp);

        blit(offsetX + 1, offsetY + 1, 1, 10, width, HEIGHT - 2);
        blit(offsetX, offsetY, 0, 14, WIDTH, HEIGHT);
        GlStateManager.disableAlphaTest();
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
}
