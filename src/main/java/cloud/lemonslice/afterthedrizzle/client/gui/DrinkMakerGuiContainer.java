package cloud.lemonslice.afterthedrizzle.client.gui;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.inventory.DrinkMakerContainer;
import cloud.lemonslice.afterthedrizzle.common.recipe.drink.DrinkRecipe;
import cloud.lemonslice.silveroak.client.texture.TexturePos;
import cloud.lemonslice.silveroak.client.texture.TextureResource;
import cloud.lemonslice.silveroak.helper.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;


public class DrinkMakerGuiContainer extends ContainerScreen<DrinkMakerContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_drink_maker.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(AfterTheDrizzle.MODID, TEXTURE_PATH);

    private static int QUESTION_X = 83;
    private static int QUESTION_Y = 16;
    private static int EXCLAMATION_X = 96;
    private static int EXCLAMATION_Y = 54;

    private DrinkMakerContainer container;
    private boolean isEnough = true;

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

        GuiHelper.drawLayer(this, offsetX + QUESTION_X, offsetY + QUESTION_Y, new TextureResource(TEXTURE, new TexturePos(176, 94, 11, 11)));

        if (this.container.getTileEntity().getCurrentRecipe() == null || !isEnough)
        {
            GuiHelper.drawLayer(this, offsetX + EXCLAMATION_X, offsetY + EXCLAMATION_Y, new TextureResource(TEXTURE, new TexturePos(187, 94, 11, 11)));
        }

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
        DrinkRecipe recipe = container.getTileEntity().getCurrentRecipe();
        if (!isEnough) isEnough = true;
        if (recipe != null)
        {
            int n = container.getTileEntity().getNeededAmount();
            for (int i = 0; i < 4; i++)
            {
                Slot slot = this.container.inventorySlots.get(i);
                ItemStack itemStack = slot.getStack();
                if (!itemStack.isEmpty() && itemStack.getCount() < n)
                {
                    renderSlotWarning(slot.xPos, slot.yPos);
                    isEnough = false;
                }
            }
        }

        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        this.font.drawString(this.title.getFormattedText(), this.xSize / 3.0F - this.font.getStringWidth(this.title.getFormattedText()) / 2.0F - 1, 8.0F, 0xdec674);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 95), 0xdec674);
        if (offsetX + QUESTION_X <= mouseX && mouseX <= offsetX + QUESTION_X + 11 && offsetY + QUESTION_Y <= mouseY && mouseY <= offsetY + QUESTION_Y + 11)
        {
            TranslationTextComponent ingredient = new TranslationTextComponent("info.afterthedrizzle.tooltip.drink_maker.help.1");
            TranslationTextComponent residue = new TranslationTextComponent("info.afterthedrizzle.tooltip.drink_maker.help.2");

            GuiHelper.drawTransparentStringDefault(this.font, ingredient.getFormattedText(), this.xSize / 3.0F - this.font.getStringWidth(ingredient.getFormattedText()) / 2.0F - 1, 28, 0xbfdec674, true);
            GuiHelper.drawTransparentStringDefault(this.font, residue.getFormattedText(), this.xSize / 3.0F - this.font.getStringWidth(residue.getFormattedText()) / 2.0F - 1, 55, 0xbfdec674, true);
        }
    }

    private void renderSlotWarning(int x, int y)
    {
        this.fillGradient(x, y, x + 16, y + 16, 0x9fd64f44, 0x9fd64f44);
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY)
    {
        super.renderHoveredToolTip(mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        GuiHelper.drawFluidTooltip(this, mouseX, mouseY, offsetX + 128, offsetY + 12, 16, 64, this.container.getTileEntity().getFluidTranslation(), this.container.getTileEntity().getFluidAmount());

        TranslationTextComponent warn_1 = new TranslationTextComponent("info.afterthedrizzle.tooltip.drink_maker.warn.1");
        TranslationTextComponent warn_2 = new TranslationTextComponent("info.afterthedrizzle.tooltip.drink_maker.warn.2");

        if (this.container.getTileEntity().getCurrentRecipe() == null)
        {
            GuiHelper.drawTooltip(this, mouseX, mouseY, offsetX + EXCLAMATION_X, offsetY + EXCLAMATION_Y, 11, 11, Collections.singletonList(warn_1.getFormattedText()));
        }
        else if (!isEnough)
        {
            GuiHelper.drawTooltip(this, mouseX, mouseY, offsetX + EXCLAMATION_X, offsetY + EXCLAMATION_Y, 11, 11, Collections.singletonList(warn_2.getFormattedText()));
        }
    }
}
