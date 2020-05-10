package roito.afterthedrizzle.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roito.afterthedrizzle.common.tileentity.StoveTileEntity;

public class StoveTESR extends TileEntityRenderer<StoveTileEntity>
{
    public StoveTESR(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(StoveTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();

        NonNullList<ItemStack> list = tileEntityIn.getContents();

        if (list.isEmpty())
        {
            return;
        }

        int x = tileEntityIn.getPos().getX();
        int y = tileEntityIn.getPos().getY();
        int z = tileEntityIn.getPos().getZ();

        ItemRenderer renderItem = mc.getItemRenderer();

        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();

        GlStateManager.translated(x + 0.5, y + 0.13, z + 0.5);
        int count = 0;

        for (ItemStack stack : list)
        {
            GlStateManager.pushMatrix();
            int seed = count * 4447;

            GlStateManager.scaled(0.5, 0.5, 0.5);
            GlStateManager.translated(((seed % 100) - 50) / 200D, count / 16D, ((seed % 56) - 28) / 112D);
            GlStateManager.rotatef(180 * (seed % 943) / 943F, 0, 1, 0);
//            GlStateManager.rotated(90, 1, 0, 0);

            RenderHelper.enableStandardItemLighting();
            renderItem.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();

            count++;
        }

        GlStateManager.popMatrix();
    }
}
