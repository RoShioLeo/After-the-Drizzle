package roito.afterthedrizzle.helper;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import roito.afterthedrizzle.client.texture.TextureResource;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class GuiHelper
{
    public static void drawTank(Screen gui, int tankX, int tankY, TextureResource texture, Fluid fluid, int fluidHeight)
    {
        if (fluid == null)
        {
            return;
        }

        if (fluidHeight != 0)
        {
            ResourceLocation fluidTexture = fluid.getRegistryName();
            TextureAtlasSprite sprite = gui.getMinecraft().getTextureMap().getAtlasSprite(fluidTexture.toString());
            gui.getMinecraft().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            for (int i = 0; i < texture.getPos().getV() / 16; i++)
            {
                for (int j = 0; j < texture.getPos().getU() / 16; j++)
                {
                    AbstractGui.blit(tankX + j * 16, tankY + i * 16, 16, 16, 16, sprite);
                }
            }

            int remainedLength = texture.getPos().getV() - fluidHeight;
            gui.getMinecraft().getTextureManager().bindTexture(texture.getTexture());

            gui.blit(tankX, tankY, texture.getPos().getX(), texture.getPos().getY(), texture.getPos().getU(), remainedLength);
        }
    }

    public static void drawLayer(Screen gui, int x, int y, TextureResource texture)
    {
        gui.getMinecraft().getTextureManager().bindTexture(texture.getTexture());
        gui.blit(x, y, texture.getPos().getX(), texture.getPos().getY(), texture.getPos().getU(), texture.getPos().getV());
    }

    public static void drawTooltip(Screen gui, int mouseX, int mouseY, int x, int y, int weight, int height, List<String> list)
    {
        if (x <= mouseX && mouseX <= x + weight && y <= mouseY && mouseY <= y + height)
        {
            gui.renderTooltip(list, mouseX, mouseY);
        }
    }

    public static void drawFluidTooltip(Screen gui, int mouseX, int mouseY, int x, int y, int weight, int height, String name, int amount)
    {
        if (amount != 0)
        {
            List<String> list = new ArrayList<>();
            list.add(name);
            DecimalFormat df = new DecimalFormat("#,###");
            list.add(TextFormatting.GRAY.toString() + df.format(amount) + "mB");
            drawTooltip(gui, mouseX, mouseY, x, y, weight, height, list);
        }
    }
}
