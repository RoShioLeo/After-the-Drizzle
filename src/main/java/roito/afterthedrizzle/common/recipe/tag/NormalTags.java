package roito.afterthedrizzle.common.recipe.tag;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public final class NormalTags
{
    public static final Tag<Item> FOOD_JERKY = ItemTags.getCollection().getOrCreate(new ResourceLocation("forge", "food/jerky"));

    public static final Tag<Item> DIRT = ItemTags.getCollection().getOrCreate(new ResourceLocation("forge", "dirt"));
}
