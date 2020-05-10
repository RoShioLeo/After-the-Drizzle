package roito.afterthedrizzle.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.environment.ApparentTemperature;

public class PlayerTemperatureRenderer extends AbstractGui
{

    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/hud/temperature.png");

    private static int index = -1;
    private static int up = 0;

    private Minecraft mc;

    public PlayerTemperatureRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    public void renderStatusBar(int screenWidth, int screenHeight, CapabilityPlayerTemperature.Data t, double env, int cold, int heat)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

//        this.drawString(mc.fontRenderer, String.valueOf(temp), screenWidth / 2 - mc.fontRenderer.getStringWidth(String.valueOf(temp)) / 2, 6, 4210752);
//        this.drawString(mc.fontRenderer, String.valueOf(env), screenWidth / 2 - mc.fontRenderer.getStringWidth(String.valueOf(env)) / 2, 12, 4210752);

        mc.getTextureManager().bindTexture(OVERLAY_BAR);

        if (up == 0)
        {
            up = t.getHotterOrColder();
            if (up != 0)
            {
                index = 0;
            }
            else index = -1;
        }
        if (up != 0 && index >= 0)
        {
            blit((NormalConfig.playerTemperatureX.get()) + 26, screenHeight - NormalConfig.playerTemperatureY.get() + 7, index / 200 * 7, 48 + (up - 1) * 16, 7, 16);
            index++;
            index %= 2000;
            if (index == 0)
            {
                up = t.getHotterOrColder();
            }
        }

        ApparentTemperature temperature = ApparentTemperature.getTemperature(t.getTemperature());
        blit((NormalConfig.playerTemperatureX.get()), screenHeight - NormalConfig.playerTemperatureY.get(), (temperature.getIndex() - 1) * 30, 0, 30, 30);
        blit((NormalConfig.playerTemperatureX.get() + 32), screenHeight - NormalConfig.playerTemperatureY.get(), cold * 9, 30, 9, 9);
        blit((NormalConfig.playerTemperatureX.get() + 42), screenHeight - NormalConfig.playerTemperatureY.get(), heat * 9, 39, 9, 9);
        this.drawString(mc.fontRenderer, temperature.getTranslation().getFormattedText(), NormalConfig.playerTemperatureX.get() + 34, screenHeight - NormalConfig.playerTemperatureY.get() + 22, temperature.getColor().getColor());

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(OverlayEventHandler.DEFAULT);
    }
}
