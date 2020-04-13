package roito.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.helper.LogHelper;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegisterManager
{
    public static NonNullList<Item> ITEMS = NonNullList.create();
    public static NonNullList<Block> BLOCKS = NonNullList.create();
    public static NonNullList<TileEntityType<?>> TILE_ENTITY = NonNullList.create();
    public static NonNullList<ContainerType<?>> CONTAINER_TYPE = NonNullList.create();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        LogHelper.info("Successfully registered %d Items.", ITEMS.size());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
        LogHelper.info("Successfully registered %d Blocks.", BLOCKS.size());
    }

    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event)
    {
        event.getRegistry().registerAll(TILE_ENTITY.toArray(new TileEntityType<?>[0]));
        LogHelper.info("Successfully registered %d Tile Entity Types.", TILE_ENTITY.size());
    }

    @SubscribeEvent
    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event)
    {
        event.getRegistry().registerAll(CONTAINER_TYPE.toArray(new ContainerType<?>[0]));
        LogHelper.info("Successfully registered %d Container Types.", CONTAINER_TYPE.size());
    }

    public static void clearAll()
    {
        ITEMS = null;
        BLOCKS = null;
        TILE_ENTITY = null;
    }
}
