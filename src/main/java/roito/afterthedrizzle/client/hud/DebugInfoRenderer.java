package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import org.lwjgl.opengl.GL11;

public final class DebugInfoRenderer extends AbstractGui
{
    private final Minecraft mc;

    public DebugInfoRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, int solar, long dayTime, double env, int solarTime)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        String solarS = "Solar Terms Day: " + solar;
        String dayS = "Day Time: " + dayTime;
        String envS = "Env Temp: " + env;
        String solarTimeS = "Solar Time: " + solarTime;

        int index = 0;

        drawInfo(screenWidth, screenHeight, solarS, index++);
        drawInfo(screenWidth, screenHeight, dayS, index++);
        drawInfo(screenWidth, screenHeight, envS, index++);
        drawInfo(screenWidth, screenHeight, solarTimeS, index++);

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }

    private void drawInfo(int screenWidth, int screenHeight, String s, int index)
    {
        this.drawString(mc.fontRenderer, s, screenWidth / 2 - mc.fontRenderer.getStringWidth(s) / 2, index * 9 + 3, 0xFFFFFF);
    }
}
