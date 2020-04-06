package roito.afterthedrizzle.common.tag;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class NormalTags
{
    public static final Tag<Item> FOOD_JERKY = ItemTags.getCollection().getOrCreate(new ResourceLocation("forge", "food/jerky"));
    public static final Tag<Item> DIRT = ItemTags.getCollection().getOrCreate(new ResourceLocation("forge", "dirt"));

    public static final Tag<Fluid> WATER = FluidTags.getCollection().getOrCreate(new ResourceLocation("minecraft", "water"));
    public static final Tag<Fluid> HOT_WATER = FluidTags.getCollection().getOrCreate(new ResourceLocation("afterthedrizzle", "hot_water"));
}
