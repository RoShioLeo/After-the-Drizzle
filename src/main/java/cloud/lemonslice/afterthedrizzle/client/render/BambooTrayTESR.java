package cloud.lemonslice.afterthedrizzle.client.render;

import cloud.lemonslice.afterthedrizzle.common.block.CatapultBoardBlockWithTray;
import cloud.lemonslice.afterthedrizzle.common.tileentity.BambooTrayTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class BambooTrayTESR extends TileEntityRenderer<BambooTrayTileEntity>
{

    public BambooTrayTESR(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(BambooTrayTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();

        ItemStack itemStack = tileEntityIn.getInput();

        if (itemStack.isEmpty())
        {
            return;
        }

        ItemRenderer renderItem = mc.getItemRenderer();

        matrixStackIn.push();
        RenderHelper.disableStandardItemLighting();

        double h = 0.125;
        if (tileEntityIn.getBlockState().getBlock() instanceof CatapultBoardBlockWithTray)
        {
            h += 0.125;
        }
        matrixStackIn.translate(0.5, h, 0.5);

        int seed = tileEntityIn.getRandomSeed();

        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        matrixStackIn.translate(((seed % 100) - 50) / 200D, 0, ((seed % 56) - 28) / 112D);
        matrixStackIn.rotate(new Quaternion(Vector3f.YP, 360 * (seed % 943) / 943F, true));
        matrixStackIn.rotate(new Quaternion(Vector3f.XP, 90, true));

        RenderHelper.enableStandardItemLighting();
        renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        RenderHelper.disableStandardItemLighting();

        matrixStackIn.pop();
    }
}
