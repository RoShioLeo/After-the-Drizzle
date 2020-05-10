package roito.afterthedrizzle.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
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

        matrixStackIn.push();
        RenderHelper.disableStandardItemLighting();

        matrixStackIn.translate(0.5, 0.13, 0.5);
        int count = 0;

        for (ItemStack stack : list)
        {
            matrixStackIn.push();
            int seed = count * 4447;

            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(((seed % 100) - 50) / 200D, count / 16D, ((seed % 56) - 28) / 112D);
            matrixStackIn.rotate(new Quaternion(Vector3f.YP, 180 * (seed % 943) / 943F, true));

            RenderHelper.enableStandardItemLighting();
            renderItem.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            RenderHelper.disableStandardItemLighting();
            matrixStackIn.pop();

            count++;
        }

        matrixStackIn.pop();
    }
}
