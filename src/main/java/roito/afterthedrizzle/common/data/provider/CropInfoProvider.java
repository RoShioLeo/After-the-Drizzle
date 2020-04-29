package roito.afterthedrizzle.common.data.provider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import static net.minecraftforge.common.Tags.Items.*;
import static roito.afterthedrizzle.common.data.tag.NormalTags.Items.*;

public class CropInfoProvider extends ForgeItemTagsProvider
{
    public CropInfoProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public void registerTags()
    {
        getBuilder(ARID_DRY).add(AGAVE, CACTUS_FRUIT);
        getBuilder(ARID_AVERAGE).add(CORN, MILLET, PARSNIP);
        getBuilder(ARID_MOIST).add(SORGHUM);
        getBuilder(ARID_HUMID).add(CANTALOUPE, SISAL, SUNFLOWER);
        getBuilder(DRY).add(LENTIL, OAT, QUINOA);
        getBuilder(DRY_AVERAGE).add(CRANBERRY, HUCKLEBERRY, JUNIPER_BERRY, KALE, LEEK, RYE, YAM);
        getBuilder(DRY_MOIST).add(AMARANTH, BARLEY, BEAN, CANDLE_BERRY, CHICKPEA, COTTON, CUMIN, ELDERBERRY, KENAF, KOHLRABI, MULBERRY, MUSTARD_SEEDS, ONION, POTATO, RADISH, RUTABAGA, SOYBEAN, SWEET_POTATO, WINTER_SQUASH, SEEDS_WHEAT);
        getBuilder(DRY_HUMID).add(ASPARAGUS, JICAMA, RASPBERRY, TOMATILLO, TOMATO, SEEDS_PUMPKIN);
        getBuilder(AVERAGE).add(BELL_PEPPER, BLACKBERRY, CAULIFLOWER, CHILI_PEPPER, PEA, PEPPER);
        getBuilder(AVERAGE_MOIST).add(ARTICHOKE, BRUSSELS_SPROUT, CELERY, CUCUMBER, EGGPLANT, FLAX, GARLIC, KIWI, PEANUT, RHUBARB, SCALLION, SESAME_SEEDS, SPINACH, STRAWBERRY, SPINACH, TURNIP, ZUCCHINI);
        getBuilder(AVERAGE_HUMID).add(ARROWROOT, BROCCOLI, CABBAGE, CARROT, COFFEE_BEAN, GINGER, HONEYDEW, JUTE, OKRA, PINEAPPLE, SEEDS_BEETROOT);
        getBuilder(MOIST).add(BLUEBERRY, LETTUCE);
        getBuilder(MOIST_HUMID).add(CASSAVA, GRAPE, GREEN_GRAPE, RICE, TARO, SEEDS_MELON, WATER_CHESTNUT, TEA_LEAF, WHITE_MUSHROOM);
    }
}
