package roito.cultivage.common.event.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import roito.cultivage.Cultivage;

public class RainGaugeBarRenderer extends Gui
{
	private final static ResourceLocation overlayBar = new ResourceLocation(Cultivage.MODID, "textures/gui/hud/env.png");

	private final static int WIDTH = 31;
	private final static int HEIGHT = 5;

	private float rainfall = -1.0F;

	private Minecraft mc;

	public RainGaugeBarRenderer(Minecraft mc)
	{
		this.mc = mc;
	}

	public void renderStatusBar(int screenWidth, int screenHeight, float rainfall)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		GlStateManager.enableAlpha();

		if (this.rainfall == -1.0F)
		{
			this.rainfall = rainfall;
		}
		else
		{
			if (rainfall > this.rainfall)
			{
				this.rainfall += (rainfall - this.rainfall) / 1024;
			}
			else if (rainfall < this.rainfall)
			{
				this.rainfall -= (this.rainfall - rainfall) / 1024;
			}
		}

		mc.getTextureManager().bindTexture(overlayBar);
		int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

		int width = Math.min((int) (27 * this.rainfall), 27);
		width = Math.max(width, 0);

		drawTexturedModalRect(offsetX + 1, offsetY + 1, 1, 0, width, HEIGHT - 2);
		drawTexturedModalRect(offsetX, offsetY, 0, 4, WIDTH, HEIGHT);
	}
}
