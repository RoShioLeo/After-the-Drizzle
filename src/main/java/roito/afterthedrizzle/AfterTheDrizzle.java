package roito.afterthedrizzle;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roito.afterthedrizzle.client.ClientProxy;
import roito.afterthedrizzle.client.color.ItemColorsRegistry;
import roito.afterthedrizzle.client.gui.BambooTrayGuiContainer;
import roito.afterthedrizzle.client.gui.DrinkMakerGuiContainer;
import roito.afterthedrizzle.client.gui.StoveGuiContainer;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.drink.DrinkEffectsManager;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.group.GroupCraft;
import roito.afterthedrizzle.common.group.GroupDrink;
import roito.afterthedrizzle.common.group.GroupFood;
import roito.afterthedrizzle.common.inventory.BambooTrayContainer;
import roito.afterthedrizzle.common.inventory.ContainerTypeRegistry;
import roito.afterthedrizzle.common.inventory.DrinkMakerContainer;
import roito.afterthedrizzle.common.inventory.StoveContainer;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.tileentity.TileEntityTypeRegistry;
import roito.afterthedrizzle.common.world.WorldGenManager;
import roito.afterthedrizzle.common.world.feature.FeaturesRegistry;
import roito.afterthedrizzle.registry.RegisterManager;

@Mod("afterthedrizzle")
public final class AfterTheDrizzle
{
    public static final String MODID = "afterthedrizzle";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public AfterTheDrizzle()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ClientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NormalConfig.COMMON_CONFIG);
        FluidsRegistry.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidsRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        new BlocksRegistry();
        new ItemsRegistry();
        new TileEntityTypeRegistry();
        new ContainerTypeRegistry();
        new FeaturesRegistry();
    }

    public void setup(FMLCommonSetupEvent event)
    {
        new RecipesRegistry();
        new DrinkEffectsManager();
        new WorldGenManager();
        RegisterManager.clearAll();
    }

    public void ClientSetup(FMLClientSetupEvent event)
    {
        new ItemColorsRegistry();
        ScreenManager.registerFactory((ContainerType<StoveContainer>) ContainerTypeRegistry.STOVE_CONTAINER, StoveGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<BambooTrayContainer>) ContainerTypeRegistry.BAMBOO_TRAY_CONTAINER, BambooTrayGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<DrinkMakerContainer>) ContainerTypeRegistry.DRINK_MAKER_CONTAINER, DrinkMakerGuiContainer::new);
    }

    public static final ItemGroup GROUP_CRAFT = new GroupCraft();
    public static final ItemGroup GROUP_DRINK = new GroupDrink();
    public static final ItemGroup GROUP_FOOD = new GroupFood();
}
