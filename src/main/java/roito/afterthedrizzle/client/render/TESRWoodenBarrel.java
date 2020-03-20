package roito.afterthedrizzle.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;
import roito.afterthedrizzle.common.tileentity.TileEntityWoodenBarrel;

public class TESRWoodenBarrel extends TileEntitySpecialRenderer<TileEntityWoodenBarrel>
{
    @Override
    public void render(TileEntityWoodenBarrel tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(tile, x, y, z, partialTicks, destroyStage, alpha);
        Minecraft mc = Minecraft.getMinecraft();

        if (tile.getFluidAmount() != 0)
        {
            GlStateManager.pushMatrix();
            if (Minecraft.isAmbientOcclusionEnabled())
            {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            }
            else
            {
                GL11.glShadeModel(GL11.GL_FLAT);
            }
            GlStateManager.translate(x, y, z);

            if (tile.getFluidName() != null)
            {
                Fluid fluid = FluidRegistry.getFluid(tile.getFluidName());
                RenderHelper.disableStandardItemLighting();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.pushMatrix();
                mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                TextureAtlasSprite still = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
                if (still == null)
                {
                    still = mc.getTextureMapBlocks().getMissingSprite();
                }

                int brightness = mc.world.getCombinedLight(tile.getPos(), fluid.getLuminosity());
                int lx = brightness >> 0x10 & 0xFFFF;
                int ly = brightness & 0xFFFF;

                int color = fluid.getColor();
                int r = color >> 16 & 0xFF;
                int g = color >> 8 & 0xFF;
                int b = color & 0xFF;
                int a = color >> 24 & 0xFF;

                double height = tile.getHeight();

                buffer.pos(0.125, height, 0.125).color(r, g, b, a).tex(still.getMinU(), still.getMinV()).lightmap(lx, ly).endVertex();
                buffer.pos(0.125, height, 0.875).color(r, g, b, a).tex(still.getMinU(), still.getMaxV()).lightmap(lx, ly).endVertex();
                buffer.pos(0.875, height, 0.875).color(r, g, b, a).tex(still.getMaxU(), still.getMaxV()).lightmap(lx, ly).endVertex();
                buffer.pos(0.875, height, 0.125).color(r, g, b, a).tex(still.getMaxU(), still.getMinV()).lightmap(lx, ly).endVertex();

                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            RenderHelper.enableStandardItemLighting();
        }
    }
}
