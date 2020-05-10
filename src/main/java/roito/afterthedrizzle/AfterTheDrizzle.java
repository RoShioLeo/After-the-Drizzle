package roito.afterthedrizzle;

import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
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
import roito.afterthedrizzle.client.color.block.BlockColorsRegistry;
import roito.afterthedrizzle.client.color.item.ItemColorsRegistry;
import roito.afterthedrizzle.client.gui.BambooTrayGuiContainer;
import roito.afterthedrizzle.client.gui.DrinkMakerGuiContainer;
import roito.afterthedrizzle.client.gui.StoveGuiContainer;
import roito.afterthedrizzle.client.sound.SoundEventsRegistry;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.capability.CapabilitiesRegistry;
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.entity.EntityTypesRegistry;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.group.GroupCore;
import roito.afterthedrizzle.common.group.GroupDrink;
import roito.afterthedrizzle.common.inventory.BambooTrayContainer;
import roito.afterthedrizzle.common.inventory.ContainerTypesRegistry;
import roito.afterthedrizzle.common.inventory.DrinkMakerContainer;
import roito.afterthedrizzle.common.inventory.StoveContainer;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.potion.EffectsRegistry;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;
import roito.afterthedrizzle.common.recipe.type.RecipeSerializersRegistry;
import roito.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;
import roito.afterthedrizzle.common.world.WorldGenManager;
import roito.afterthedrizzle.common.world.feature.FeaturesRegistry;
import roito.afterthedrizzle.registry.RegisterManager;

import static net.minecraft.block.ComposterBlock.CHANCES;

@Mod("afterthedrizzle")
public final class AfterTheDrizzle
{
    public static final String MODID = "afterthedrizzle";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NETWORK_VERSION = "1";

    public static final CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public AfterTheDrizzle()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ClientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NormalConfig.COMMON_CONFIG);
        FluidsRegistry.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidsRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidsRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        new BlocksRegistry();
        new ItemsRegistry();
        new EffectsRegistry();
        new TileEntityTypesRegistry();
        new EntityTypesRegistry();
        new ContainerTypesRegistry();
        new FeaturesRegistry();
        new SoundEventsRegistry();
    }

    public void setup(FMLCommonSetupEvent event)
    {
        RecipesRegistry.init();
        WorldGenManager.init();
        RegisterManager.clearAll();
        registerCompostable();
        registerFireInfo();
        CapabilitiesRegistry.init();
        SimpleNetworkHandler.init();
    }

    public void ClientSetup(FMLClientSetupEvent event)
    {
        ItemColorsRegistry.init();
        BlockColorsRegistry.init();
        ClientProxy.registerRenderType();
        ScreenManager.registerFactory((ContainerType<StoveContainer>) ContainerTypesRegistry.STOVE_CONTAINER, StoveGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<BambooTrayContainer>) ContainerTypesRegistry.BAMBOO_TRAY_CONTAINER, BambooTrayGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<DrinkMakerContainer>) ContainerTypesRegistry.DRINK_MAKER_CONTAINER, DrinkMakerGuiContainer::new);
    }

    private static void registerCompostable()
    {
        CHANCES.put(ItemsRegistry.TEA_SEEDS, 0.3F);
        CHANCES.put(ItemsRegistry.TEA_LEAVES, 0.15F);
        CHANCES.put(ItemsRegistry.GREEN_TEA_LEAVES, 0.3F);
        CHANCES.put(ItemsRegistry.BLACK_TEA_LEAVES, 0.4F);
        CHANCES.put(ItemsRegistry.TEA_RESIDUES, 0.5F);
        CHANCES.put(BlocksRegistry.CHRYSANTHEMUM_ITEM, 0.3F);
        CHANCES.put(BlocksRegistry.ZINNIA_ITEM, 0.3F);
        CHANCES.put(BlocksRegistry.HYACINTH_ITEM, 0.3F);
        CHANCES.put(Items.POISONOUS_POTATO, 0.3F);
    }

    private static void registerFireInfo()
    {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(BlocksRegistry.WOODEN_FRAME, 5, 20);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_CHAIR, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_DOOR, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_GLASS_DOOR, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_LANTERN, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_TABLE, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_TRAY, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_CATAPULT_BOARD, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.HYACINTH, 60, 100);
        fireblock.setFireInfo(BlocksRegistry.CHRYSANTHEMUM, 60, 100);
        fireblock.setFireInfo(BlocksRegistry.ZINNIA, 60, 100);
    }

    public static final ItemGroup GROUP_CORE = new GroupCore();
    public static final ItemGroup GROUP_DRINK = new GroupDrink();
}
