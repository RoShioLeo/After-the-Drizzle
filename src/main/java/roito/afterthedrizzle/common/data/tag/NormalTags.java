package roito.afterthedrizzle.common.data.tag;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public final class NormalTags
{
    public static class Items
    {
        public final static Tag<Item> ARID = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/arid_arid"));
        public final static Tag<Item> ARID_DRY = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/arid_dry"));
        public final static Tag<Item> ARID_AVERAGE = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/arid_average"));
        public final static Tag<Item> ARID_MOIST = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/arid_moist"));
        public final static Tag<Item> ARID_HUMID = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/arid_humid"));
        public final static Tag<Item> DRY = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/dry_dry"));
        public final static Tag<Item> DRY_AVERAGE = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/dry_average"));
        public final static Tag<Item> DRY_MOIST = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/dry_moist"));
        public final static Tag<Item> DRY_HUMID = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/dry_humid"));
        public final static Tag<Item> AVERAGE = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/average_average"));
        public final static Tag<Item> AVERAGE_MOIST = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/average_moist"));
        public final static Tag<Item> AVERAGE_HUMID = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/average_humid"));
        public final static Tag<Item> MOIST = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/moist_moist"));
        public final static Tag<Item> MOIST_HUMID = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/moist_humid"));
        public final static Tag<Item> HUMID = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:crops/humid_humid"));

        public final static Tag<Item> SEEDS_AGAVE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/agave"));
        public final static Tag<Item> SEEDS_AMARANTH = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/amaranth"));
        public final static Tag<Item> SEEDS_ARROWROOT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/arrowroot"));
        public final static Tag<Item> SEEDS_ARTICHOKE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/artichoke"));
        public final static Tag<Item> SEEDS_ASPARAGUS = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/asparagus"));
        public final static Tag<Item> SEEDS_BARLEY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/barley"));
        public final static Tag<Item> SEEDS_BEAN = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/bean"));
        public final static Tag<Item> SEEDS_BELL_PEPPER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/bell_pepper"));
        public final static Tag<Item> SEEDS_BLACKBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/blackberry"));
        public final static Tag<Item> SEEDS_BLUEBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/blueberry"));
        public final static Tag<Item> SEEDS_BROCCOLI = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/broccoli"));
        public final static Tag<Item> SEEDS_BRUSSELS_SPROUT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/brussels_sprout"));
        public final static Tag<Item> SEEDS_CABBAGE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cabbage"));
        public final static Tag<Item> SEEDS_CACTUS_FRUIT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cactus_fruit"));
        public final static Tag<Item> SEEDS_CANDLE_BERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/candle_berry"));
        public final static Tag<Item> SEEDS_CANTALOUPE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cantaloupe"));
        public final static Tag<Item> SEEDS_CARROT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/carrot"));
        public final static Tag<Item> SEEDS_CASSAVA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cassava"));
        public final static Tag<Item> SEEDS_CAULIFLOWER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cauliflower"));
        public final static Tag<Item> SEEDS_CELERY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/celery"));
        public final static Tag<Item> SEEDS_CHICKPEA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/chickpea"));
        public final static Tag<Item> SEEDS_CHILI_PEPPER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/chili_pepper"));
        public final static Tag<Item> SEEDS_COFFEE_BEAN = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/coffee_bean"));
        public final static Tag<Item> SEEDS_CORN = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/corn"));
        public final static Tag<Item> SEEDS_COTTON = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cotton"));
        public final static Tag<Item> SEEDS_CRANBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cranberry"));
        public final static Tag<Item> SEEDS_CUCUMBER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cucumber"));
        public final static Tag<Item> SEEDS_CUMIN = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/cumin"));
        public final static Tag<Item> SEEDS_EGGPLANT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/eggplant"));
        public final static Tag<Item> SEEDS_ELDERBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/elderberry"));
        public final static Tag<Item> SEEDS_FLAX = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/flax"));
        public final static Tag<Item> SEEDS_GARLIC = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/garlic"));
        public final static Tag<Item> SEEDS_GINGER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/ginger"));
        public final static Tag<Item> SEEDS_GRAPE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/grape"));
        public final static Tag<Item> SEEDS_GREEN_GRAPE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/green_grape"));
        public final static Tag<Item> SEEDS_HONEYDEW = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/honeydew"));
        public final static Tag<Item> SEEDS_HUCKLEBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/huckleberry"));
        public final static Tag<Item> SEEDS_JICAMA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/jicama"));
        public final static Tag<Item> SEEDS_JUNIPER_BERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/juniper_berry"));
        public final static Tag<Item> SEEDS_JUTE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/jute"));
        public final static Tag<Item> SEEDS_KALE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/kale"));
        public final static Tag<Item> SEEDS_KENAF = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/kenaf"));
        public final static Tag<Item> SEEDS_KIWI = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/kiwi"));
        public final static Tag<Item> SEEDS_KOHLRABI = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/kohlrabi"));
        public final static Tag<Item> SEEDS_LEEK = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/leek"));
        public final static Tag<Item> SEEDS_LENTIL = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/lentil"));
        public final static Tag<Item> SEEDS_LETTUCE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/lettuce"));
        public final static Tag<Item> SEEDS_MILLET = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/millet"));
        public final static Tag<Item> SEEDS_MULBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/mulberry"));
        public final static Tag<Item> SEEDS_MUSTARD_SEEDS = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/mustard_seeds"));
        public final static Tag<Item> SEEDS_OAT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/oat"));
        public final static Tag<Item> SEEDS_OKRA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/okra"));
        public final static Tag<Item> SEEDS_ONION = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/onion"));
        public final static Tag<Item> SEEDS_PARSNIP = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/parsnip"));
        public final static Tag<Item> SEEDS_PEA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/pea"));
        public final static Tag<Item> SEEDS_PEANUT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/peanut"));
        public final static Tag<Item> SEEDS_PEPPER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/pepper"));
        public final static Tag<Item> SEEDS_PINEAPPLE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/pineapple"));
        public final static Tag<Item> SEEDS_POTATO = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/potato"));
        public final static Tag<Item> SEEDS_QUINOA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/quinoa"));
        public final static Tag<Item> SEEDS_RADISH = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/radish"));
        public final static Tag<Item> SEEDS_RASPBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/raspberry"));
        public final static Tag<Item> SEEDS_RHUBARB = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/rhubarb"));
        public final static Tag<Item> SEEDS_RICE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/rice"));
        public final static Tag<Item> SEEDS_RUTABAGA = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/rutabaga"));
        public final static Tag<Item> SEEDS_RYE = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/rye"));
        public final static Tag<Item> SEEDS_SCALLION = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/scallion"));
        public final static Tag<Item> SEEDS_SESAME_SEEDS = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/sesame_seeds"));
        public final static Tag<Item> SEEDS_SISAL = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/sisal"));
        public final static Tag<Item> SEEDS_SORGHUM = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/sorghum"));
        public final static Tag<Item> SEEDS_SOYBEAN = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/soybean"));
        public final static Tag<Item> SEEDS_SPINACH = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/spinach"));
        public final static Tag<Item> SEEDS_STRAWBERRY = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/strawberry"));
        public final static Tag<Item> SEEDS_SUNFLOWER = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/sunflower"));
        public final static Tag<Item> SEEDS_SWEET_POTATO = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/sweet_potato"));
        public final static Tag<Item> SEEDS_TARO = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/taro"));
        public final static Tag<Item> SEEDS_TEA_LEAF = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/tea_leaf"));
        public final static Tag<Item> SEEDS_TOMATILLO = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/tomatillo"));
        public final static Tag<Item> SEEDS_TOMATO = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/tomato"));
        public final static Tag<Item> SEEDS_TURNIP = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/turnip"));
        public final static Tag<Item> SEEDS_WATER_CHESTNUT = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/water_chestnut"));
        public final static Tag<Item> SEEDS_WHITE_MUSHROOM = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/white_mushroom"));
        public final static Tag<Item> SEEDS_WINTER_SQUASH = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/winter_squash"));
        public final static Tag<Item> SEEDS_YAM = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/yam"));
        public final static Tag<Item> SEEDS_ZUCCHINI = new ItemTags.Wrapper(new ResourceLocation("forge:seeds/zucchini"));

        public final static Tag<Item> CROPS_BLACK_TEA_LEAF = new ItemTags.Wrapper(new ResourceLocation("forge:crops/black_tea_leaf"));
        public final static Tag<Item> CROPS_GREEN_TEA_LEAF = new ItemTags.Wrapper(new ResourceLocation("forge:crops/green_tea_leaf"));
        public final static Tag<Item> CROPS_TEA_LEAF = new ItemTags.Wrapper(new ResourceLocation("forge:crops/tea_leaf"));
        public final static Tag<Item> CROPS_WHITE_TEA_LEAF = new ItemTags.Wrapper(new ResourceLocation("forge:crops/white_tea_leaf"));

        public final static Tag<Item> FOOD_JERKY = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:food/jerky"));
        public final static Tag<Item> FOOD_MEAT = new ItemTags.Wrapper(new ResourceLocation("afterthedrizzle:food/meat"));

        public final static Tag<Item> DIRT = new ItemTags.Wrapper(new ResourceLocation("forge:dirt"));
        public final static Tag<Item> DUSTS_ASH = new ItemTags.Wrapper(new ResourceLocation("forge:dusts/ash"));
    }

    public static class Fluids
    {
        public final static Tag<Fluid> DRINK = new FluidTags.Wrapper(new ResourceLocation("afterthedrizzle:drink"));
    }
}
