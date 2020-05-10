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
import roito.afterthedrizzle.common.tileentity.DrinkMakerTileEntity;

import java.util.List;

public class DrinkMakerTESR extends TileEntityRenderer<DrinkMakerTileEntity>
{

    public DrinkMakerTESR(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(DrinkMakerTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();

        List<ItemStack> list = tileEntityIn.getContent();

        ItemRenderer renderItem = mc.getItemRenderer();

        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        for (int i = 0; i < 4; i++)
        {
            ItemStack itemStack = list.get(i);
            if (itemStack.isEmpty())
            {
                continue;
            }

            int x = tileEntityIn.getPos().getX();
            int y = tileEntityIn.getPos().getY();
            int z = tileEntityIn.getPos().getZ();

            GlStateManager.pushMatrix();

            switch (tileEntityIn.getFacing())
            {
                case NORTH:
                    GlStateManager.translated(x + 0.4 * (i + 1), y + 0.35, z + 0.5);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 1.0 - 0.4 * (i + 1), y + 0.35, z + 0.5);
                    break;
                case WEST:
                    GlStateManager.translated(x + 0.5, y + 0.35, z + 1.0 - 0.4 * (i + 1));
                    break;
                default:
                    GlStateManager.translated(x + 0.5, y + 0.35, z + 0.4 * (i + 1));
            }
            GlStateManager.rotatef(45, 0, 1, 0);
            GlStateManager.scaled(0.5, 0.5, 0.5);

            RenderHelper.enableStandardItemLighting();
            renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}
