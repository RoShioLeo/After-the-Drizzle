package cloud.lemonslice.afterthedrizzle.registry;

import cloud.lemonslice.afterthedrizzle.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegisterManager
{
    public static List<Item> ITEMS = new ArrayList<>();
    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();
    public static List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();
    public static List<Effect> EFFECTS = new ArrayList<>();
    public static List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();
    public static List<Feature<?>> FEATURES = new ArrayList<>();
    public static List<SoundEvent> SOUNDS = new ArrayList<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        LogHelper.info("Successfully registered %d Item(s).", ITEMS.size());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
        LogHelper.info("Successfully registered %d Block(s).", BLOCKS.size());
    }

    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event)
    {
        event.getRegistry().registerAll(TILE_ENTITY_TYPES.toArray(new TileEntityType<?>[0]));
        LogHelper.info("Successfully registered %d Tile Entity Type(s).", TILE_ENTITY_TYPES.size());
    }

    @SubscribeEvent
    public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().registerAll(ENTITY_TYPES.toArray(new EntityType<?>[0]));
        LogHelper.info("Successfully registered %d Entity Type(s).", ENTITY_TYPES.size());
    }

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event)
    {
        event.getRegistry().registerAll(EFFECTS.toArray(new Effect[0]));
        LogHelper.info("Successfully registered %d Effect(s).", EFFECTS.size());
    }

    @SubscribeEvent
    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event)
    {
        event.getRegistry().registerAll(CONTAINER_TYPES.toArray(new ContainerType<?>[0]));
        LogHelper.info("Successfully registered %d Container Type(s).", CONTAINER_TYPES.size());
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
    {
        event.getRegistry().registerAll(FEATURES.toArray(new Feature<?>[0]));
        LogHelper.info("Successfully registered %d Feature(s).", FEATURES.size());
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().registerAll(SOUNDS.toArray(new SoundEvent[0]));
        LogHelper.info("Successfully registered %d Sound(s).", SOUNDS.size());
    }

    public static void clearAll()
    {
        ITEMS = null;
        BLOCKS = null;
        TILE_ENTITY_TYPES = null;
        ENTITY_TYPES = null;
        CONTAINER_TYPES = null;
        EFFECTS = null;
        FEATURES = null;
        SOUNDS = null;
    }
}
