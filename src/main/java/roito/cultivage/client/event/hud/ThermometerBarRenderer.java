package roito.cultivage.client.event.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import roito.cultivage.Cultivage;
import roito.cultivage.api.environment.Temperature;

public class ThermometerBarRenderer extends Gui
{
    private final static ResourceLocation overlayBar = new ResourceLocation(Cultivage.MODID, "textures/gui/hud/env.png");

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
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlpha();

        if (this.temp == -1.0F)
        {
            this.temp = temp;
        }
        else
        {
            if (temp > this.temp)
            {
                this.temp += 0.001F;
            }
            else if (temp < this.temp)
            {
                this.temp -= 0.001F;
            }
        }

        mc.getTextureManager().bindTexture(overlayBar);
        int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

        int width = getWidth(this.temp);

        drawTexturedModalRect(offsetX + 1, offsetY + 1, 1, 10, width, HEIGHT - 2);
        drawTexturedModalRect(offsetX, offsetY, 0, 14, WIDTH, HEIGHT);
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
