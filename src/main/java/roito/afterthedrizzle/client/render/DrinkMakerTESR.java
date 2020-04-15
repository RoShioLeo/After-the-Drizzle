package roito.afterthedrizzle.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.common.tileentity.DrinkMakerTileEntity;

import java.util.List;

public class DrinkMakerTESR extends TileEntityRenderer<DrinkMakerTileEntity>
{

    @Override
    @SuppressWarnings("deprecation")
    public void render(DrinkMakerTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage)
    {
        super.render(tileEntityIn, x, y, z, partialTicks, destroyStage);
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
            GlStateManager.rotated(45, 0, 1, 0);
            GlStateManager.scaled(0.5, 0.5, 0.5);

            RenderHelper.enableStandardItemLighting();
            renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}
