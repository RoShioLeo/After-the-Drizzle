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
		int width = 0;
		int levelWidth = 5;

		if (temp <= 0)
		{
			return width;
		}
		if (temp <= 0.15F)
		{
			return (int) (levelWidth * (temp / 0.15F));
		}

		width += levelWidth;
		if (temp <= 0.3F)
		{
			temp -= 0.15F;
			return width + (int) (levelWidth * (temp / 0.15F));
		}

		width += levelWidth;
		if (temp <= 0.6F)
		{
			temp -= 0.3F;
			return width + (int) (levelWidth * (temp / 0.3F));
		}

		width += levelWidth;
		if (temp <= 0.8F)
		{
			temp -= 0.6F;
			return width + (int) (levelWidth * (temp / 0.2F));
		}

		width += levelWidth;
		if (temp <= 1.5F)
		{
			temp -= 0.8F;
			return width + (int) (levelWidth * (temp / 0.7F));
		}

		width += levelWidth;
		if (temp <= 2.0F)
		{
			temp -= 1.5F;
			return width + (int) ((levelWidth - 1) * (temp / 0.5F));
		}
		else
		{
			return width + (levelWidth - 1);
		}
	}
}
