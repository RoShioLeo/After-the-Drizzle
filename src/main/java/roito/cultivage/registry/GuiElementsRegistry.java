package roito.cultivage.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import roito.cultivage.Cultivage;
import roito.cultivage.client.gui.GuiContainerFlatBasket;
import roito.cultivage.common.inventory.ContainerFlatBasket;

public class GuiElementsRegistry implements IGuiHandler
{
	public static final int GUI_FLAT_BAKSET = 0;

	public GuiElementsRegistry()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Cultivage.getInstance(), this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_FLAT_BAKSET:
				return new ContainerFlatBasket(player, world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}


	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_FLAT_BAKSET:
				return new GuiContainerFlatBasket(new ContainerFlatBasket(player, world.getTileEntity(new BlockPos(x, y, z))));
			default:
				return null;
		}
	}


}
