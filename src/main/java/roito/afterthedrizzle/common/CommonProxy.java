package roito.afterthedrizzle.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roito.afterthedrizzle.registry.*;
import roito.afterthedrizzle.registry.fluid.BlockFluidsRegistry;
import roito.afterthedrizzle.registry.fluid.FluidsRegistry;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new BlocksRegistry();
        new ItemsRegistry();
        new FluidsRegistry();
        new BlockFluidsRegistry();
        new TileEntitiesRegistry();
    }

    public void init(FMLInitializationEvent event)
    {
        new GuiElementsRegistry();
        new RecipesRegistry();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        RegisterManager.clearAll();
    }
}
