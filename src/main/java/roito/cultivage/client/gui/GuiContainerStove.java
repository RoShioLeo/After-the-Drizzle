package roito.cultivage.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.cultivage.Cultivage;
import roito.cultivage.common.inventory.ContainerStove;

@SideOnly(Side.CLIENT)
public class GuiContainerStove extends GuiContainer
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_stove.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(Cultivage.MODID, TEXTURE_PATH);
    private ContainerStove inventory;

    public GuiContainerStove(ContainerStove inventorySlotsIn)
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
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (height - ySize) / 2;

        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);

        int fuelTicks = inventory.getFuelTicks();
        int remainTicks = inventory.getRemainTicks();
        int textureHeight = fuelTicks == 0 ? 0 : (int) Math.ceil(14 * remainTicks / fuelTicks);

        drawTexturedModalRect(offsetX + 81, offsetY + 16 + 14 - textureHeight, 176, 14 - textureHeight, 14, textureHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = I18n.format("tile.cultivage.stove.name");
        fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }
}
