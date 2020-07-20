package cloud.lemonslice.afterthedrizzle.client.gui;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.client.texture.TexturePos;
import cloud.lemonslice.afterthedrizzle.client.texture.TextureResource;
import cloud.lemonslice.afterthedrizzle.common.inventory.DrinkMakerContainer;
import cloud.lemonslice.afterthedrizzle.helper.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

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

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(offsetX, offsetY, 0, 0, xSize, ySize);

        int totalTicks = this.container.getTileEntity().getTotalTicks();
        int processTicks = this.container.getTileEntity().getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(22.0 * processTicks / totalTicks);
        }
        blit(offsetX + 99, offsetY + 37, 176, 0, textureWidth, 16);
        container.getTileEntity().getFluidHandler().ifPresent(f ->
        {
            int capacity = f.getTankCapacity(0);
            int height;
            if (capacity != 0)
            {
                height = (int) Math.ceil(64.0 * this.container.getTileEntity().getFluidAmount() / capacity);
                GuiHelper.drawLayer(this, offsetX + 126, offsetY + 10, new TextureResource(TEXTURE, new TexturePos(176, 17, 20, 68)));
            }
            else
            {
                height = 0;
            }
            GuiHelper.drawTank(this, new TexturePos(offsetX + 128, offsetY + 12, 16, 64), f.getFluidInTank(0), height);
        });
        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        this.container.detectAndSendChanges();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString(this.title.getFormattedText(), (float) (this.xSize / 3 - this.font.getStringWidth(this.title.getFormattedText()) / 2), 8.0F, 0xdec674);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 95), 0xdec674);
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        GuiHelper.drawFluidTooltip(this, mouseX, mouseY, offsetX + 128, offsetY + 12, 16, 64, this.container.getTileEntity().getFluidTranslation(), this.container.getTileEntity().getFluidAmount());
    }
}
