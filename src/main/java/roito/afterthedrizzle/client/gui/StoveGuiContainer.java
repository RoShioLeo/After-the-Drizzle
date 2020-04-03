package roito.afterthedrizzle.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.inventory.StoveContainer;

public class StoveGuiContainer extends ContainerScreen<StoveContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_stove.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);
    private StoveContainer container;

    public StoveGuiContainer(StoveContainer container, PlayerInventory inv, ITextComponent name)
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
        int offsetX = (this.width - this.xSize) / 2, offsetY = (height - ySize) / 2;

        blit(offsetX, offsetY, 0, 0, xSize, ySize);

        int fuelTicks = this.container.getTileEntity().getFuelTicks();
        int remainTicks = this.container.getTileEntity().getRemainTicks();
        int textureHeight = fuelTicks == 0 ? 0 : (int) Math.ceil(14 * remainTicks / fuelTicks);

        blit(offsetX + 81, offsetY + 16 + 14 - textureHeight, 176, 14 - textureHeight, 14, textureHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString(this.title.getFormattedText(), (float) (this.xSize / 2 - this.font.getStringWidth(this.title.getFormattedText()) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }
}
