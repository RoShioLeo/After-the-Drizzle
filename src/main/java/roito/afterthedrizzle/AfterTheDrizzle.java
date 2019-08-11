package roito.afterthedrizzle;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.common.tab.TabCraft;
import roito.afterthedrizzle.common.tab.TabEnv;
import roito.afterthedrizzle.common.tab.TabFood;

@Mod(modid = AfterTheDrizzle.MODID,
        name = AfterTheDrizzle.NAME,
        version = AfterTheDrizzle.VERSION,
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        dependencies = "required-after:forge@[14.23.5.2768,);" +
                "required-after:silveroakoutpost;")
public class AfterTheDrizzle
{
    public static final String MODID = "afterthedrizzle";
    public static final String NAME = "After the Drizzle";
    public static final String VERSION = "@version@";

    private static final AfterTheDrizzle INSTANCE = new AfterTheDrizzle();

    @Mod.InstanceFactory
    public static AfterTheDrizzle getInstance()
    {
        return INSTANCE;
    }

    @SidedProxy(clientSide = "roito.afterthedrizzle.client.ClientProxy",
            serverSide = "roito.afterthedrizzle.common.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    public AfterTheDrizzle()
    {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    public static final CreativeTabs TAB_ENV = new TabEnv();
    public static final CreativeTabs TAB_CRAFT = new TabCraft();
    public static final CreativeTabs TAB_FOOD = new TabFood();
}
