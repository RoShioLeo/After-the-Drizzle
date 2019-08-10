package roito.afterthedrizzle.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.inventory.ContainerFlatBasket;

@SideOnly(Side.CLIENT)
public class GuiContainerFlatBasket extends GuiContainer
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_single_recipe.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);
    private ContainerFlatBasket inventory;

    public GuiContainerFlatBasket(ContainerFlatBasket inventorySlotsIn)
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
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
        drawTexturedModalRect(offsetX + 51, offsetY + 29, 176, 107, 20, 20);

        int totalTicks = inventory.getTotalTicks();
        int processTicks = inventory.getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(24 * processTicks / totalTicks);
        }
        drawTexturedModalRect(offsetX + 76, offsetY + 31, 176, 0, textureWidth, 17);

        int id = inventory.getMode().ordinal();
        drawTexturedModalRect(offsetX + 52, offsetY + 30, 176, 17 + id * 18, 18, 18);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = I18n.format("tile.afterthedrizzle.flat_basket.name");
        fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        if (offsetX + 52 < mouseX && mouseX < offsetX + 70 && offsetY + 30 < mouseY && mouseY < offsetY + 48)
        {
            this.drawHoveringText(inventory.getMode().getTranslationKey(), mouseX, mouseY);
        }
    }
}
