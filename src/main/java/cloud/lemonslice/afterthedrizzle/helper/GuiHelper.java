package cloud.lemonslice.afterthedrizzle.helper;

import cloud.lemonslice.afterthedrizzle.client.texture.TexturePos;
import cloud.lemonslice.afterthedrizzle.client.texture.TextureResource;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class GuiHelper
{
    public static void drawTank(Screen gui, TexturePos pos, FluidStack fluid, int fluidHeight)
    {
        int width = pos.getWidth();
        if (fluid == null)
        {
            return;
        }

        if (fluidHeight != 0)
        {
            TextureAtlasSprite sprite = gui.getMinecraft().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluid.getFluid().getAttributes().getStillTexture());
            gui.getMinecraft().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
            int color = fluid.getFluid().getAttributes().getColor(fluid);
            RenderSystem.color4f(ColorHelper.getRedF(color), ColorHelper.getGreenF(color), ColorHelper.getBlueF(color), ColorHelper.getAlphaF(color));
            for (int j = 0; j < width / 16; j++)
            {
                int count = 0;
                int fluidH = fluidHeight;
                while (fluidH > 16)
                {
                    drawFluid(pos.getX() + j * 16, pos.getY() + pos.getHeight() - (count + 1) * 16, 0, 16, 16, sprite);
                    fluidH -= 16;
                    count++;
                }
                drawFluid(pos.getX() + j * 16, pos.getY() + pos.getHeight() - count * 16 - fluidH, 0, 16, fluidH, sprite);
            }
            int fluidWidth = width % 16;
            int j = width / 16;
            if (fluidWidth != 0)
            {
                int count = 0;
                int fluidH = fluidHeight;
                while (fluidH > 16)
                {
                    drawFluid(pos.getX() + j * 16, pos.getY() + pos.getHeight() - (count + 1) * 16, 0, fluidWidth, 16, sprite);
                    fluidH -= 16;
                    count++;
                }
                drawFluid(pos.getX() + j * 16, pos.getY() + pos.getHeight() - count * 16 - fluidH, 0, fluidWidth, fluidH, sprite);
            }
        }
    }

    public static void drawLayer(Screen gui, int x, int y, TextureResource texture)
    {
        gui.getMinecraft().getTextureManager().bindTexture(texture.getTexture());
        gui.blit(x, y, texture.getPos().getX(), texture.getPos().getY(), texture.getPos().getWidth(), texture.getPos().getHeight());
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

    public static void drawFluid(int x0, int y0, int z, int destWidth, int destHeight, TextureAtlasSprite sprite)
    {
        int height = ModelLoader.instance().sheetData.get(PlayerContainer.LOCATION_BLOCKS_TEXTURE).getSecond().height;
        int width = ModelLoader.instance().sheetData.get(PlayerContainer.LOCATION_BLOCKS_TEXTURE).getSecond().width;
        innerBlit(x0, x0 + destWidth, y0, y0 + destHeight, z, sprite.getMinU(), (sprite.getMaxU() * width - sprite.getWidth() + destWidth / 16.0F * sprite.getWidth()) / width, (sprite.getMinV() * height + sprite.getHeight() - destHeight / 16.0F * sprite.getHeight()) / height, sprite.getMaxV());
    }

    protected static void innerBlit(int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1)
    {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x0, y1, z).tex(u0, v1).endVertex();
        bufferbuilder.pos(x1, y1, z).tex(u1, v1).endVertex();
        bufferbuilder.pos(x1, y0, z).tex(u1, v0).endVertex();
        bufferbuilder.pos(x0, y0, z).tex(u0, v0).endVertex();
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }
}
