package roito.cultivage.common.event.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import roito.cultivage.Cultivage;

public class ThermometerBarRenderer extends Gui
{
	private final static ResourceLocation overlayBar = new ResourceLocation(Cultivage.MODID, "textures/gui/hud/env.png");

	private final static int WIDTH = 31;
	private final static int HEIGHT = 5;

	private float temp = -1.0F;

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
				this.temp += (temp - this.temp) / 1024;
			}
			else if (temp < this.temp)
			{
				this.temp -= (this.temp - temp) / 1024;
			}
		}

		mc.getTextureManager().bindTexture(overlayBar);
		int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

		int width = Math.min((int) (27 * this.temp), 27);
		width = Math.max(width, 0);

		drawTexturedModalRect(offsetX + 1, offsetY + 1, 1, 10, width, HEIGHT - 2);
		drawTexturedModalRect(offsetX, offsetY, 0, 14, WIDTH, HEIGHT);
	}
}
