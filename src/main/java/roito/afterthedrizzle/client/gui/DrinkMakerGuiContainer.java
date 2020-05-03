package roito.afterthedrizzle.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.client.texture.TexturePos;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.inventory.DrinkMakerContainer;
import roito.afterthedrizzle.helper.GuiHelper;

public class DrinkMakerGuiContainer extends ContainerScreen<DrinkMakerContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_drink_maker.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);
    private DrinkMakerContainer container;

    public DrinkMakerGuiContainer(DrinkMakerContainer container, PlayerInventory inv, ITextComponent name)
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
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.enableAlphaTest();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(offsetX, offsetY, 0, 0, xSize, ySize);

        int totalTicks = this.container.getTileEntity().getTotalTicks();
        int processTicks = this.container.getTileEntity().getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(22 * processTicks / totalTicks);
        }
        blit(offsetX + 104, offsetY + 37, 176, 0, textureWidth, 16);

        int height = (int) Math.ceil(64 * this.container.getTileEntity().getFluidAmount() / NormalConfig.drinkMakerCapacity.get());
        GuiHelper.drawTank(this, offsetX + 134, offsetY + 14, new TexturePos(134, 14, 16, 64), container.getTileEntity().getFluidTank().getFluid(), height);

        GlStateManager.disableAlphaTest();
        GlStateManager.disableBlend();
        this.container.detectAndSendChanges();
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

        GuiHelper.drawFluidTooltip(this, mouseX, mouseY, offsetX + 134, offsetY + 14, 16, 64, this.container.getTileEntity().getFluidTranslation(), this.container.getTileEntity().getFluidAmount());
    }
}
