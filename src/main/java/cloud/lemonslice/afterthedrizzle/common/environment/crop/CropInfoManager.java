package cloud.lemonslice.afterthedrizzle.common.environment.crop;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.common.block.TrellisBlock;
import cloud.lemonslice.afterthedrizzle.common.block.TrellisWithVineBlock;
import cloud.lemonslice.afterthedrizzle.common.block.VineType;
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

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class CropInfoManager
{
    private final static Map<Block, CropHumidityInfo> CROP_HUMIDITY_INFO = new HashMap<>();
    private final static Map<Block, CropSeasonInfo> CROP_SEASON_INFO = new HashMap<>();

    private final static Map<VinePair, TrellisWithVineBlock> TRELLIS_VINES_INFO = new HashMap<>();
    private final static Map<TrellisWithVineBlock, TrellisBlock> TRELLIS_INFO = new HashMap<>();

    public static void registerVineTypeConnections(VineType typeIn, TrellisBlock blockIn, TrellisWithVineBlock vineOut)
    {
        TRELLIS_VINES_INFO.put(new VinePair(typeIn, blockIn), vineOut);
        TRELLIS_INFO.put(vineOut, blockIn);
    }

    public static void initTrellisBlocks()
    {
        registerVineTypeConnections(VineType.GRAPE, BlocksRegistry.OAK_TRELLIS, BlocksRegistry.OAK_TRELLIS_GRAPE);
        registerVineTypeConnections(VineType.GRAPE, BlocksRegistry.BIRCH_TRELLIS, BlocksRegistry.BIRCH_TRELLIS_GRAPE);
        registerVineTypeConnections(VineType.GRAPE, BlocksRegistry.JUNGLE_TRELLIS, BlocksRegistry.JUNGLE_TRELLIS_GRAPE);
        registerVineTypeConnections(VineType.GRAPE, BlocksRegistry.SPRUCE_TRELLIS, BlocksRegistry.SPRUCE_TRELLIS_GRAPE);
        registerVineTypeConnections(VineType.GRAPE, BlocksRegistry.DARK_OAK_TRELLIS, BlocksRegistry.DARK_OAK_TRELLIS_GRAPE);
        registerVineTypeConnections(VineType.GRAPE, BlocksRegistry.ACACIA_TRELLIS, BlocksRegistry.ACACIA_TRELLIS_GRAPE);
    }

    @SubscribeEvent
    public static void init(TagsUpdatedEvent event)
    {
        CROP_HUMIDITY_INFO.clear();
        CROP_SEASON_INFO.clear();

        Arrays.asList(CropHumidityType.values()).forEach(type ->
                ItemTags.getCollection().getOrCreate(type.getRes()).getAllElements().forEach(crop -> registerCropHumidityInfo(crop, type)));
        Arrays.asList(CropSeasonType.values()).forEach(type ->
                ItemTags.getCollection().getOrCreate(type.getRes()).getAllElements().forEach(crop -> registerCropSeasonInfo(crop, type)));

        // Register the crops in Pam's that are nonstandard.
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:bellpepperseeditem")), CropHumidityType.AVERAGE);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:brusselsproutseeditem")), CropHumidityType.AVERAGE_MOIST);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:cactusfruitseeditem")), CropHumidityType.ARID_DRY);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:candleberryseeditem")), CropHumidityType.DRY_MOIST);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:chilipepperseeditem")), CropHumidityType.AVERAGE);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:coffeebeanseeditem")), CropHumidityType.AVERAGE_HUMID);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:greengrapeseeditem")), CropHumidityType.MOIST_HUMID);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:juniperberryseeditem")), CropHumidityType.DRY_AVERAGE);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:mustardseedsseeditem")), CropHumidityType.DRY_MOIST);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:sesameseedsseeditem")), CropHumidityType.AVERAGE_MOIST);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:oatsseeditem")), CropHumidityType.DRY);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:peasseeditem")), CropHumidityType.AVERAGE);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:sweetpotatoseeditem")), CropHumidityType.DRY_MOIST);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:waterchestnutseeditem")), CropHumidityType.MOIST_HUMID);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:whitemushroomseeditem")), CropHumidityType.MOIST_HUMID);
        registerCropHumidityInfo(ForgeRegistries.ITEMS.getValue(new ResourceLocation("pamhc2crops:wintersquashseeditem")), CropHumidityType.DRY_MOIST);

        registerCropHumidityInfo(BlocksRegistry.OAK_TRELLIS_GRAPE, CropHumidityType.MOIST_HUMID, true);
        registerCropHumidityInfo(BlocksRegistry.BIRCH_TRELLIS_GRAPE, CropHumidityType.MOIST_HUMID, true);
        registerCropHumidityInfo(BlocksRegistry.JUNGLE_TRELLIS_GRAPE, CropHumidityType.MOIST_HUMID, true);
        registerCropHumidityInfo(BlocksRegistry.SPRUCE_TRELLIS_GRAPE, CropHumidityType.MOIST_HUMID, true);
        registerCropHumidityInfo(BlocksRegistry.DARK_OAK_TRELLIS_GRAPE, CropHumidityType.MOIST_HUMID, true);
        registerCropHumidityInfo(BlocksRegistry.ACACIA_TRELLIS_GRAPE, CropHumidityType.MOIST_HUMID, true);

        registerCropSeasonInfo(BlocksRegistry.OAK_TRELLIS_GRAPE, CropSeasonType.SP_SU, true);
        registerCropSeasonInfo(BlocksRegistry.BIRCH_TRELLIS_GRAPE, CropSeasonType.SP_SU, true);
        registerCropSeasonInfo(BlocksRegistry.JUNGLE_TRELLIS_GRAPE, CropSeasonType.SP_SU, true);
        registerCropSeasonInfo(BlocksRegistry.SPRUCE_TRELLIS_GRAPE, CropSeasonType.SP_SU, true);
        registerCropSeasonInfo(BlocksRegistry.DARK_OAK_TRELLIS_GRAPE, CropSeasonType.SP_SU, true);
        registerCropSeasonInfo(BlocksRegistry.ACACIA_TRELLIS_GRAPE, CropSeasonType.SP_SU, true);

        ForgeRegistries.BLOCKS.forEach(block ->
        {
            registerCropHumidityInfo(block, CropHumidityType.AVERAGE_MOIST, false);
            registerCropSeasonInfo(block, CropSeasonType.SP_SU_AU, false);
        });
    }

    public static void registerCropHumidityInfo(Item item, CropHumidityType info)
    {
        if (item instanceof BlockItem && !CROP_HUMIDITY_INFO.containsKey(((BlockItem) item).getBlock()))
        {
            CROP_HUMIDITY_INFO.put(((BlockItem) item).getBlock(), info.getInfo());
        }
    }

    public static void registerCropHumidityInfo(Block block, CropHumidityType info, boolean force)
    {
        if (force || block instanceof CropsBlock)
        {
            if (!CROP_HUMIDITY_INFO.containsKey(block))
            {
                CROP_HUMIDITY_INFO.put(block, info.getInfo());
            }
        }
    }

    public static void registerCropSeasonInfo(Item item, CropSeasonType info)
    {
        if (item instanceof BlockItem && !CROP_SEASON_INFO.containsKey(((BlockItem) item).getBlock()))
        {
            CROP_SEASON_INFO.put(((BlockItem) item).getBlock(), info.getInfo());
        }
    }

    public static void registerCropSeasonInfo(Block block, CropSeasonType info, boolean force)
    {
        if (force || block instanceof CropsBlock)
        {
            if (!CROP_SEASON_INFO.containsKey(block))
            {
                CROP_SEASON_INFO.put(block, info.getInfo());
            }
        }
    }

    public static Collection<Block> getHumidityCrops()
    {
        return CROP_HUMIDITY_INFO.keySet();
    }

    public static Collection<Block> getSeasonCrops()
    {
        return CROP_SEASON_INFO.keySet();
    }

    @Nullable
    public static CropHumidityInfo getHumidityInfo(Block crop)
    {
        return CROP_HUMIDITY_INFO.get(crop);
    }

    @Nullable
    public static CropSeasonInfo getSeasonInfo(Block crop)
    {
        return CROP_SEASON_INFO.get(crop);
    }

    public static TrellisWithVineBlock getVineTrellis(VineType typeIn, TrellisBlock blockIn)
    {
        return TRELLIS_VINES_INFO.get(new VinePair(typeIn, blockIn));
    }

    public static TrellisBlock getEmptyTrellis(TrellisWithVineBlock blockIn)
    {
        return TRELLIS_INFO.get(blockIn);
    }
}
