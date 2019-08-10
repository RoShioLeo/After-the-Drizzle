package roito.afterthedrizzle.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.client.gui.GuiContainerFlatBasket;
import roito.afterthedrizzle.client.gui.GuiContainerStove;
import roito.afterthedrizzle.common.inventory.ContainerFlatBasket;
import roito.afterthedrizzle.common.inventory.ContainerStove;

public class GuiElementsRegistry implements IGuiHandler
{
    public static final int GUI_FLAT_BAKSET = 0;
    public static final int GUI_STOVE = 1;

    public GuiElementsRegistry()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(AfterTheDrizzle.getInstance(), this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case GUI_FLAT_BAKSET:
                return new ContainerFlatBasket(player, world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_STOVE:
                return new ContainerStove(player, world.getTileEntity(new BlockPos(x, y, z)));
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
            case GUI_STOVE:
                return new GuiContainerStove(new ContainerStove(player, world.getTileEntity(new BlockPos(x, y, z))));
            default:
                return null;
        }
    }


}
