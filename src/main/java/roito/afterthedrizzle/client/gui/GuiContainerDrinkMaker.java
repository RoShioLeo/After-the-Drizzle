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
import roito.afterthedrizzle.common.inventory.ContainerDrinkMaker;
import roito.afterthedrizzle.helper.GuiHelper;

@SideOnly(Side.CLIENT)
public class GuiContainerDrinkMaker extends GuiContainer
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_drink_maker.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);
    private ContainerDrinkMaker inventory;

    public GuiContainerDrinkMaker(ContainerDrinkMaker inventorySlotsIn)
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

        int totalTicks = this.inventory.getTileEntity().getTotalTicks();
        int processTicks = this.inventory.getTileEntity().getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(22 * processTicks / totalTicks);
        }
        this.drawTexturedModalRect(offsetX + 104, offsetY + 37, 176, 0, textureWidth, 16);

        int height = (int) Math.ceil(64 * this.inventory.getTileEntity().getFluidAmount() / ConfigMain.blocks.drinkMakerCapacity);
        GuiHelper.drawTank(this, offsetX + 134, offsetY + 14, new TextureResource(TEXTURE, new TexturePos(134, 14, 16, 64)), inventory.getTileEntity().getFluidName(), height);
        this.inventory.detectAndSendChanges();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = I18n.format("tile.afterthedrizzle.drink_maker.name");
        fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        GuiHelper.drawFluidTooltip(this, mouseX, mouseY, offsetX + 134, offsetY + 14, 16, 64, this.inventory.getTileEntity().getFluidTranslation(), this.inventory.getTileEntity().getFluidAmount());
    }
}
