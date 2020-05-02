package roito.afterthedrizzle.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.common.block.CatapultBoardBlockWithTray;
import roito.afterthedrizzle.common.tileentity.BambooTrayTileEntity;

public class BambooTrayTESR extends TileEntityRenderer<BambooTrayTileEntity>
{
    @Override
    @SuppressWarnings("deprecation")
    public void render(BambooTrayTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage)
    {
        super.render(tileEntityIn, x, y, z, partialTicks, destroyStage);
        Minecraft mc = Minecraft.getInstance();

        ItemStack itemStack = tileEntityIn.getInput();

        if (itemStack.isEmpty())
        {
            return;
        }

        ItemRenderer renderItem = mc.getItemRenderer();

        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();

        double h = 0.125;
        if (tileEntityIn.getBlockState().getBlock() instanceof CatapultBoardBlockWithTray)
        {
            h += 0.125;
        }
        GlStateManager.translated(x + 0.5, y + h, z + 0.5);

        GlStateManager.pushMatrix();
        int seed = tileEntityIn.getRandomSeed();

        GlStateManager.scaled(0.5, 0.5, 0.5);
        GlStateManager.translated(((seed % 100) - 50) / 200D, 0, ((seed % 56) - 28) / 112D);
        GlStateManager.rotated(360 * (seed % 943) / 943F, 0, 1, 0);
        GlStateManager.rotated(90, 1, 0, 0);

        RenderHelper.enableStandardItemLighting();
        renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
    }
}
