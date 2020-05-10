package roito.afterthedrizzle.common.environment.crop;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.AfterTheDrizzle;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class CropInfoManager
{
    private final static Map<Block, CropInfo> BLOCK_INFO = new HashMap<>();

    @SubscribeEvent
    public static void init(TagsUpdatedEvent event)
    {
        BLOCK_INFO.clear();

        Arrays.asList(CropType.values()).forEach(type ->
                ItemTags.getCollection().getOrCreate(type.getRes()).getAllElements().forEach(crop -> registerCropInfo(crop, type)));


        // Register the crops in Pam's that are nonstandard.
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:bellpepperseeditem")), CropType.AVERAGE);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:brusselsproutseeditem")), CropType.AVERAGE_MOIST);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:cactusfruitseeditem")), CropType.ARID_DRY);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:candleberryseeditem")), CropType.DRY_MOIST);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:chilipepperseeditem")), CropType.AVERAGE);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:coffeebeanseeditem")), CropType.AVERAGE_HUMID);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:greengrapeseeditem")), CropType.MOIST_HUMID);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:juniperberryseeditem")), CropType.DRY_AVERAGE);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:mustardseedsseeditem")), CropType.DRY_MOIST);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:sesameseedsseeditem")), CropType.AVERAGE_MOIST);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:oatsseeditem")), CropType.DRY);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:peasseeditem")), CropType.AVERAGE);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:sweetpotatoseeditem")), CropType.DRY_MOIST);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:waterchestnutseeditem")), CropType.MOIST_HUMID);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:whitemushroomseeditem")), CropType.MOIST_HUMID);
        registerCropInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:wintersquashseeditem")), CropType.DRY_MOIST);

        ForgeRegistries.BLOCKS.forEach(block -> registerCropInfo(block, CropType.AVERAGE_MOIST, false));
    }

    public static void registerCropInfo(Item item, CropType info)
    {
        if (item instanceof BlockItem && !BLOCK_INFO.containsKey(((BlockItem) item).getBlock()))
        {
            BLOCK_INFO.put(((BlockItem) item).getBlock(), info.getInfo());
        }
    }

    public static void registerCropInfo(Block block, CropType info, boolean force)
    {
        if (force || block instanceof CropsBlock)
        {
            if (!BLOCK_INFO.containsKey(block))
            {
                BLOCK_INFO.put(block, info.getInfo());
            }
        }
    }

    public static Collection<Block> getCrops()
    {
        return BLOCK_INFO.keySet();
    }

    @Nullable
    public static CropInfo getInfo(Block crop)
    {
        return BLOCK_INFO.get(crop);
    }
}
