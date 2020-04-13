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
import roito.afterthedrizzle.client.gui.BambooTrayGuiContainer;
import roito.afterthedrizzle.client.gui.StoveGuiContainer;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.group.GroupCraft;
import roito.afterthedrizzle.common.group.GroupFood;
import roito.afterthedrizzle.common.inventory.BambooTrayContainer;
import roito.afterthedrizzle.common.inventory.ContainerTypeRegistry;
import roito.afterthedrizzle.common.inventory.StoveContainer;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.recipe.tag.NormalTags;
import roito.afterthedrizzle.common.tileentity.TileEntityTypeRegistry;

@Mod("afterthedrizzle")
public final class AfterTheDrizzle
{
    public static final String MODID = "afterthedrizzle";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final CommonProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());

    public AfterTheDrizzle()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ClientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NormalConfig.COMMON_CONFIG);
        new BlocksRegistry();
        new ItemsRegistry();
        new TileEntityTypeRegistry();
        new ContainerTypeRegistry();
    }

    public void setup(FMLCommonSetupEvent event)
    {
        new NormalTags();
        new RecipesRegistry();
    }

    public void ClientSetup(FMLClientSetupEvent event)
    {
        ScreenManager.registerFactory((ContainerType<StoveContainer>) ContainerTypeRegistry.STOVE_CONTAINER, StoveGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<BambooTrayContainer>) ContainerTypeRegistry.BAMBOO_TRAY_CONTAINER, BambooTrayGuiContainer::new);
    }

    public static final ItemGroup GROUP_CRAFT = new GroupCraft();
    public static final ItemGroup GROUP_FOOD = new GroupFood();
}
