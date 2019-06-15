package roito.cultivage;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import roito.cultivage.common.CommonProxy;

@Mod(modid = Cultivage.MODID,
        name = Cultivage.NAME,
        version = Cultivage.VERSION,
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        dependencies = "required-after:forge@[14.23.5.2768,);")
public class Cultivage
{
    public static final String MODID = "cultivage";
    public static final String NAME = "Cultivation Age";
    public static final String VERSION = "@version@";

    private static final Cultivage INSTANCE = new Cultivage();

    @Mod.InstanceFactory
    public static Cultivage getInstance()
    {
        return INSTANCE;
    }

    @SidedProxy(clientSide = "roito.cultivage.client.ClientProxy",
            serverSide = "roito.cultivage.common.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

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
}
