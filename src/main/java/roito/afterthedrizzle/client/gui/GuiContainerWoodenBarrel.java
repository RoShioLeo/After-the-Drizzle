package roito.afterthedrizzle.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.client.texture.TexturePos;
import roito.afterthedrizzle.client.texture.TextureResource;
import roito.afterthedrizzle.common.config.ConfigMain;
import roito.afterthedrizzle.common.inventory.ContainerWoodenBarrel;
import roito.afterthedrizzle.helper.GuiHelper;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiContainerWoodenBarrel extends GuiContainer
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_wooden_barrel.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);
    private ContainerWoodenBarrel inventory;

    public GuiContainerWoodenBarrel(ContainerWoodenBarrel inventorySlotsIn)
    {
        super(inventorySlotsIn);
        xSize = 176;
        ySize = 166;
        inventory = inventorySlotsIn;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTick);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);

        int height = (int) Math.ceil(48 * this.inventory.getTileEntity().getFluidAmount() / ConfigMain.blocks.woodenBarrelCapacity);
        GuiHelper.drawTank(this, offsetX + 80, offsetY + 22, new TextureResource(TEXTURE, new TexturePos(80, 22, 16, 48)), inventory.getTileEntity().getFluidName(), height);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = I18n.format("tile.afterthedrizzle.wooden_barrel.name");
        fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        if (offsetX + 80 < mouseX && mouseX < offsetX + 96 && offsetY + 22 < mouseY && mouseY < offsetY + 70)
        {
            if (this.inventory.getTileEntity().getFluidAmount() != 0)
            {
                List<String> list = new ArrayList();
                list.add(this.inventory.getTileEntity().getFluidTranslation());
                list.add(this.inventory.getTileEntity().getFluidAmount() + "mB");
                this.drawHoveringText(list, mouseX, mouseY);
            }
        }
    }
}
