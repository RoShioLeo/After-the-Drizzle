package roito.afterthedrizzle.helper;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import roito.afterthedrizzle.client.texture.TexturePos;
import roito.afterthedrizzle.client.texture.TextureResource;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class GuiHelper
{
    public static void drawTank(ContainerScreen gui, int tankX, int tankY, TexturePos pos, FluidStack fluid, int fluidHeight)
    {
        if (fluid == null)
        {
            return;
        }

        if (fluidHeight != 0)
        {
            TextureAtlasSprite sprite = gui.getMinecraft().getTextureMap().getSprite(fluid.getFluid().getAttributes().getStillTexture());
            gui.getMinecraft().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            int color = fluid.getFluid().getAttributes().getColor(fluid);
            GlStateManager.color4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >> 24 & 255) / 255.0F);
            for (int j = 0; j < pos.getU() / 16; j++)
            {
                int count = 0;
                while (fluidHeight > 16)
                {
                    gui.blit(tankX + j * 16, tankY + pos.getV() - (count + 1) * 16, 176, 16, 16, sprite);
                    fluidHeight -= 16;
                    count++;
                }
                gui.blit(tankX + j * 16, tankY + pos.getV() - count * 16 - fluidHeight, 176, 16, fluidHeight, sprite);
            }
        }
    }

    public static void drawLayer(ContainerScreen gui, int x, int y, TextureResource texture)
    {
        gui.getMinecraft().getTextureManager().bindTexture(texture.getTexture());
        gui.blit(x, y, texture.getPos().getX(), texture.getPos().getY(), texture.getPos().getU(), texture.getPos().getV());
    }

    public static void drawTooltip(ContainerScreen gui, int mouseX, int mouseY, int x, int y, int weight, int height, List<String> list)
    {
        if (x <= mouseX && mouseX <= x + weight && y <= mouseY && mouseY <= y + height)
        {
            gui.renderTooltip(list, mouseX, mouseY);
        }
    }

    public static void drawFluidTooltip(ContainerScreen gui, int mouseX, int mouseY, int x, int y, int weight, int height, String name, int amount)
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
