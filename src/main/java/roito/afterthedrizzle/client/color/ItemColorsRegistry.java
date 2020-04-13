package roito.afterthedrizzle.client.color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;

public final class ItemColorsRegistry
{
    private final static IItemColor BUCKET_COLOR = new BucketItemColors();
    private final static IItemColor CUP_COLOR = new CupItemColors();
    private final static IItemColor BOTTLE_COLOR = new BottleItemColors();

    public ItemColorsRegistry()
    {
        Minecraft.getInstance().getItemColors().register(BUCKET_COLOR, FluidsRegistry.BOILING_WATER_BUCKET.get(), FluidsRegistry.HOT_WATER_80_BUCKET.get(), FluidsRegistry.HOT_WATER_60_BUCKET.get(), FluidsRegistry.WARM_WATER_BUCKET.get(), FluidsRegistry.SUGARY_WATER_BUCKET.get());
        Minecraft.getInstance().getItemColors().register(CUP_COLOR, ItemsRegistry.PORCELAIN_CUP_DRINK);
        Minecraft.getInstance().getItemColors().register(BOTTLE_COLOR, ItemsRegistry.BOTTLE_DRINK);
    }
}
