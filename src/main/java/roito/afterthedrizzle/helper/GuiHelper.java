package roito.afterthedrizzle.helper;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import roito.afterthedrizzle.client.texture.TextureResource;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class GuiHelper
{
    public static void drawTank(GuiScreen gui, int tankX, int tankY, TextureResource texture, String fluidID, int fluidHeight)
    {
        if (fluidID == null)
        {
            return;
        }

        if (fluidHeight != 0)
        {
            Fluid fluid = FluidRegistry.getFluid(fluidID);
            int color = fluid.getColor();
            ResourceLocation fluidTexture = fluid.getStill();
            TextureAtlasSprite sprite = gui.mc.getTextureMapBlocks().getAtlasSprite(fluidTexture.toString());
            gui.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.color((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 1.0f);
            for (int i = 0; i < texture.getPos().getV() / 16; i++)
            {
                for (int j = 0; j < texture.getPos().getU() / 16; j++)
                {
                    gui.drawTexturedModalRect(tankX + j * 16, tankY + i * 16, sprite, 16, 16);
                }
            }

            int remainedLength = texture.getPos().getV() - fluidHeight;
            gui.mc.getTextureManager().bindTexture(texture.getTexture());

            if (fluid.isLighterThanAir())
            {
                gui.drawTexturedModalRect(tankX, tankY + fluidHeight, texture.getPos().getX(), texture.getPos().getY() + fluidHeight, texture.getPos().getU(), remainedLength);
            }
            else
            {
                gui.drawTexturedModalRect(tankX, tankY, texture.getPos().getX(), texture.getPos().getY(), texture.getPos().getU(), remainedLength);
            }
        }
    }

    public static void drawLayer(GuiScreen gui, int x, int y, TextureResource texture)
    {
        gui.mc.getTextureManager().bindTexture(texture.getTexture());
        gui.drawTexturedModalRect(x, y, texture.getPos().getX(), texture.getPos().getY(), texture.getPos().getU(), texture.getPos().getV());
    }

    public static void drawTooltip(GuiScreen gui, int mouseX, int mouseY, int x, int y, int weight, int height, List<String> list)
    {
        if (x <= mouseX && mouseX <= x + weight && y <= mouseY && mouseY <= y + height)
        {
            gui.drawHoveringText(list, mouseX, mouseY);
        }
    }

    public static void drawFluidTooltip(GuiScreen gui, int mouseX, int mouseY, int x, int y, int weight, int height, String name, int amount)
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
