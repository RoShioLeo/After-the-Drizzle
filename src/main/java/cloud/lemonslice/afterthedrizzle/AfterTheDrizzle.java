package cloud.lemonslice.afterthedrizzle;

import cloud.lemonslice.afterthedrizzle.client.ClientProxy;
import cloud.lemonslice.afterthedrizzle.client.color.block.BlockColorsRegistry;
import cloud.lemonslice.afterthedrizzle.client.color.item.ItemColorsRegistry;
import cloud.lemonslice.afterthedrizzle.client.sound.SoundEventsRegistry;
import cloud.lemonslice.afterthedrizzle.common.CommonProxy;
import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilitiesRegistry;
import cloud.lemonslice.afterthedrizzle.common.command.SolarCommand;
import cloud.lemonslice.afterthedrizzle.common.command.WeatherCommand;
import cloud.lemonslice.afterthedrizzle.common.config.NormalConfigs;
import cloud.lemonslice.afterthedrizzle.common.entity.EntityTypesRegistry;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropInfoManager;
import cloud.lemonslice.afterthedrizzle.common.environment.solar.BiomeTemperatureManager;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.BiomeWeatherManager;
import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import cloud.lemonslice.afterthedrizzle.common.group.GroupCore;
import cloud.lemonslice.afterthedrizzle.common.group.GroupDrink;
import cloud.lemonslice.afterthedrizzle.common.inventory.ContainerTypesRegistry;
import cloud.lemonslice.afterthedrizzle.common.item.ItemsRegistry;
import cloud.lemonslice.afterthedrizzle.common.network.SimpleNetworkHandler;
import cloud.lemonslice.afterthedrizzle.common.potion.EffectsRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.RecipesRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.serializer.RecipeSerializersRegistry;
import cloud.lemonslice.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;
import cloud.lemonslice.afterthedrizzle.common.world.WorldGenManager;
import cloud.lemonslice.afterthedrizzle.common.world.feature.FeaturesRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::interModEnqueue);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, NormalConfigs.SERVER_CONFIG);
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
        CapabilitiesRegistry.init();
        SimpleNetworkHandler.init();
        BiomeWeatherManager.init();
        BiomeTemperatureManager.init();
        CommonProxy.registerCompostable();
        CommonProxy.registerFireInfo();
        CropInfoManager.initTrellisBlocks();
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
        WeatherCommand.register(event.getCommandDispatcher());
    }

    public void interModEnqueue(InterModEnqueueEvent event)
    {
        BlocksRegistry.TRELLIS_BLOCKS = null;
    }

    public static final ItemGroup GROUP_CORE = new GroupCore();
    public static final ItemGroup GROUP_DRINK = new GroupDrink();
}