package roito.afterthedrizzle;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roito.afterthedrizzle.client.ClientProxy;
import roito.afterthedrizzle.client.color.block.BlockColorsRegistry;
import roito.afterthedrizzle.client.color.item.ItemColorsRegistry;
import roito.afterthedrizzle.client.sound.SoundEventsRegistry;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.capability.CapabilitiesRegistry;
import roito.afterthedrizzle.common.command.SolarCommand;
import roito.afterthedrizzle.common.config.NormalConfigs;
import roito.afterthedrizzle.common.entity.EntityTypesRegistry;
import roito.afterthedrizzle.common.environment.solar.BiomeTemperatureManager;
import roito.afterthedrizzle.common.environment.weather.BiomeWeatherManager;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.group.GroupCore;
import roito.afterthedrizzle.common.group.GroupDrink;
import roito.afterthedrizzle.common.inventory.ContainerTypesRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.potion.EffectsRegistry;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.recipe.type.RecipeSerializersRegistry;
import roito.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;
import roito.afterthedrizzle.common.world.WorldGenManager;
import roito.afterthedrizzle.common.world.feature.FeaturesRegistry;
import roito.afterthedrizzle.registry.RegisterManager;

@Mod("afterthedrizzle")
public final class AfterTheDrizzle
{
    public static final String MODID = "afterthedrizzle";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NETWORK_VERSION = "1.1";

    public static final CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public AfterTheDrizzle()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NormalConfigs.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NormalConfigs.CLIENT_CONFIG);
        FluidsRegistry.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidsRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidsRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        new BlocksRegistry();
        new ItemsRegistry();
        new EffectsRegistry();
        new TileEntityTypesRegistry();
        new EntityTypesRegistry();
        new FeaturesRegistry();
        new SoundEventsRegistry();
        new ContainerTypesRegistry();
    }

    public void setup(FMLCommonSetupEvent event)
    {
        RecipesRegistry.init();
        WorldGenManager.init();
        RegisterManager.clearAll();
        CapabilitiesRegistry.init();
        SimpleNetworkHandler.init();
        BiomeWeatherManager.init();
        BiomeTemperatureManager.init();
        CommonProxy.registerCompostable();
        CommonProxy.registerFireInfo();
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
        ItemColorsRegistry.init();
        BlockColorsRegistry.init();
        ClientProxy.initBiomeColors();
        ClientProxy.registerRenderType();
        ClientProxy.registerEntityRenderer();
        ContainerTypesRegistry.clientInit();
    }

    public void serverStarting(FMLServerStartingEvent event)
    {
        SolarCommand.register(event.getCommandDispatcher());
    }

    public static final ItemGroup GROUP_CORE = new GroupCore();
    public static final ItemGroup GROUP_DRINK = new GroupDrink();
}
