package roito.afterthedrizzle.common.environment.crop;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.item.ItemsRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class CropInfoManager
{
    private final static Map<Item, CropInfo> INFO = new HashMap<>();
    private final static CropInfo NORMAL = new CropInfo(Humidity.AVERAGE, Humidity.MOIST);

    public static void init()
    {
        registerCropInfo(ItemsRegistry.TEA_SEEDS, Humidity.MOIST, Humidity.HUMID);
        registerCropInfo(Items.WHEAT_SEEDS, Humidity.DRY, Humidity.MOIST);
        registerCropInfo(Items.MELON_SEEDS, Humidity.MOIST, Humidity.HUMID);
        registerCropInfo(Items.PUMPKIN_SEEDS, Humidity.DRY, Humidity.HUMID);
        registerCropInfo(Items.BEETROOT_SEEDS, Humidity.AVERAGE, Humidity.HUMID);
        registerCropInfo(Items.CARROT, Humidity.AVERAGE, Humidity.HUMID);
        registerCropInfo(Items.POTATO, Humidity.DRY, Humidity.MOIST);

        ForgeRegistries.BLOCKS.forEach(block ->
        {
            if (block instanceof IPlantable && ((IPlantable) block).getPlantType(null, null) == PlantType.Crop)
            {
                if (!INFO.containsKey(block.asItem()))
                {
                    registerCropInfo(block.asItem());
                }
            }
        });
    }

    public static void registerCropInfo(Item crop, Humidity min, Humidity max)
    {
        INFO.put(crop, new CropInfo(min, max));
    }

    public static void registerCropInfo(Item crop, Humidity humidity)
    {
        INFO.put(crop, new CropInfo(humidity, humidity));
    }

    public static void registerCropInfo(Item crop)
    {
        INFO.put(crop, NORMAL);
    }

    public static Collection<Item> getCrops()
    {
        return INFO.keySet();
    }

    public static CropInfo getInfo(Item crop)
    {
        return INFO.get(crop);
    }
}
