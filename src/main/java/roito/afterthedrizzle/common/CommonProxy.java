package roito.afterthedrizzle.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roito.afterthedrizzle.registry.GuiElementsRegistry;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    public void init(FMLInitializationEvent event)
    {
        new GuiElementsRegistry();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}