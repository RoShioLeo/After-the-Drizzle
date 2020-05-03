package roito.afterthedrizzle.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.inventory.BambooTrayContainer;

public class BambooTrayGuiContainer extends ContainerScreen<BambooTrayContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_bamboo_tray.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);
    private BambooTrayContainer container;

    public BambooTrayGuiContainer(BambooTrayContainer container, PlayerInventory inv, ITextComponent name)
    {
        super(container, inv, name);
        this.container = container;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTick);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        blit(offsetX, offsetY, 0, 0, xSize, ySize);
        blit(offsetX + 51, offsetY + 29, 176, 107, 20, 20);

        int totalTicks = container.getTileEntity().getTotalTicks();
        int processTicks = container.getTileEntity().getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(24 * processTicks / totalTicks);
        }
        blit(offsetX + 76, offsetY + 31, 176, 0, textureWidth, 17);

        int id = container.getTileEntity().getMode().ordinal();
        blit(offsetX + 52, offsetY + 30, 176, 17 + id * 18, 18, 18);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString(this.title.getFormattedText(), (float) (this.xSize / 2 - this.font.getStringWidth(this.title.getFormattedText()) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        if (offsetX + 52 < mouseX && mouseX < offsetX + 70 && offsetY + 30 < mouseY && mouseY < offsetY + 48)
        {
            this.renderTooltip(container.getTileEntity().getMode().getTranslationKey(), mouseX, mouseY);
        }
    }
}
