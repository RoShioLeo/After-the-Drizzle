package roito.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.helper.LogHelper;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegisterManager
{
    public static NonNullList<Item> ITEMS = NonNullList.create();
    public static NonNullList<Block> BLOCKS = NonNullList.create();
    public static NonNullList<TileEntityType<?>> TILE_ENTITY_TYPES = NonNullList.create();
    public static NonNullList<EntityType<?>> ENTITY_TYPES = NonNullList.create();
    public static NonNullList<Effect> EFFECTS = NonNullList.create();
    public static NonNullList<ContainerType<?>> CONTAINER_TYPES = NonNullList.create();
    public static NonNullList<Feature<?>> FEATURES = NonNullList.create();

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

    public static void clearAll()
    {
        ITEMS = null;
        BLOCKS = null;
        TILE_ENTITY_TYPES = null;
        ENTITY_TYPES = null;
        CONTAINER_TYPES = null;
        EFFECTS = null;
        FEATURES = null;
    }
}
