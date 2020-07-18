package cloud.lemonslice.afterthedrizzle.client.color.item;

import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import cloud.lemonslice.afterthedrizzle.common.item.ItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;

public final class ItemColorsRegistry
{
    private final static IItemColor BUCKET_COLOR = new BucketItemColors();
    private final static IItemColor CUP_COLOR = new CupItemColors();
    private final static IItemColor BOTTLE_COLOR = new BottleItemColors();
    private final static IItemColor HYBRIDIZABLE_FLOWER_COLOR = new HybridizableFlowerItemColor();
    private final static IItemColor GRASS_BLOCK_COLOR = new GrassBlockItemColors();

    public static void init()
    {
        FluidsRegistry.ITEMS.getEntries().forEach(e -> Minecraft.getInstance().getItemColors().register(BUCKET_COLOR, e.get()));
        Minecraft.getInstance().getItemColors().register(CUP_COLOR, ItemsRegistry.PORCELAIN_CUP_DRINK);
        Minecraft.getInstance().getItemColors().register(BOTTLE_COLOR, ItemsRegistry.BOTTLE_DRINK);
        Minecraft.getInstance().getItemColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlocksRegistry.CHRYSANTHEMUM_ITEM, BlocksRegistry.HYACINTH_ITEM, BlocksRegistry.ZINNIA_ITEM);
        Minecraft.getInstance().getItemColors().register(GRASS_BLOCK_COLOR, BlocksRegistry.GRASS_BLOCK_WITH_HOLE_ITEM);
    }
}
