package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class PlayerTemperatureRenderer extends AbstractGui
{
    private float temp = 0;

    private Minecraft mc;

    public PlayerTemperatureRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, int temp)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawString(mc.fontRenderer, String.valueOf(temp), screenWidth / 2 - mc.fontRenderer.getStringWidth(String.valueOf(temp)) / 2, 6, 4210752);
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }
}
