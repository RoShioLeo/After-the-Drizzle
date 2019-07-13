package roito.cultivage.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.cultivage.Cultivage;
import roito.cultivage.common.inventory.ContainerFlatBasket;

@SideOnly(Side.CLIENT)
public class GuiContainerFlatBasket extends GuiContainer
{
	private static final String TEXTURE_PATH = "textures/gui/container/gui_single_recipe.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cultivage.MODID, TEXTURE_PATH);
	private ContainerFlatBasket inventory;

	public GuiContainerFlatBasket(ContainerFlatBasket inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.xSize = 176;
		this.ySize = 166;
		this.inventory = inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTick);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(TEXTURE);
		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

		int totalTicks = this.inventory.getTotalTicks();
		int processTicks = this.inventory.getProcessTicks();
		int textureWidth = 0;
		if (totalTicks != 0)
		{
			textureWidth = (int) Math.ceil(110.0 * processTicks / totalTicks);
		}
		this.drawTexturedModalRect(offsetX + 33, offsetY + 62, 0, 166, textureWidth, 6);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String title = I18n.format("tile.cultivage.flat_basket.name");
		this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);

	}
}
