package roito.cultivage.client.event.hud;

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

	private float rainfall = 0;

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
				this.rainfall += 0.001F;
			}
			else if (rainfall < this.rainfall)
			{
				this.rainfall -= 0.001F;
			}
		}

		mc.getTextureManager().bindTexture(overlayBar);
		int offsetX = (screenWidth - WIDTH + 1) / 2, offsetY = (screenHeight + 36 - HEIGHT) / 2;

		int width = Math.min((int) (29 * this.rainfall), 29);
		width = Math.max(width, 0);

		drawTexturedModalRect(offsetX + 1, offsetY + 1, 1, 0, width, HEIGHT - 2);
		drawTexturedModalRect(offsetX, offsetY, 0, 4, WIDTH, HEIGHT);
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
