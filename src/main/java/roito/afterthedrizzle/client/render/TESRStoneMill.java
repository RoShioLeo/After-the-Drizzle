package roito.afterthedrizzle.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.common.tileentity.TileEntityStoneMill;
import roito.afterthedrizzle.registry.BlocksRegistry;

public class TESRStoneMill extends TileEntitySpecialRenderer<TileEntityStoneMill>
{
    @Override
    public void render(TileEntityStoneMill tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(tile, x, y, z, partialTicks, destroyStage, alpha);
        Minecraft mc = Minecraft.getMinecraft();

        RenderItem renderItem = mc.getRenderItem();

        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();

        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);

        GlStateManager.pushMatrix();

        GlStateManager.scale(1, 1, 1);
        GlStateManager.rotate(0, 0, 1, 0);

        RenderHelper.enableStandardItemLighting();
        renderItem.renderItem(new ItemStack(BlocksRegistry.STONE_MILL_TOP), ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
