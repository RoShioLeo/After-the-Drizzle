package roito.afterthedrizzle.common.environment.crop;

import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.common.environment.Humidity;

public enum CropType
{
    ARID(new CropInfo(Humidity.ARID), new ResourceLocation("afterthedrizzle:crops/arid_arid")),
    ARID_DRY(new CropInfo(Humidity.ARID, Humidity.DRY), new ResourceLocation("afterthedrizzle:crops/arid_dry")),
    ARID_AVERAGE(new CropInfo(Humidity.ARID, Humidity.AVERAGE), new ResourceLocation("afterthedrizzle:crops/arid_average")),
    ARID_MOIST(new CropInfo(Humidity.ARID, Humidity.MOIST), new ResourceLocation("afterthedrizzle:crops/arid_moist")),
    ARID_HUMID(new CropInfo(Humidity.ARID, Humidity.HUMID), new ResourceLocation("afterthedrizzle:crops/arid_humid")),
    DRY(new CropInfo(Humidity.DRY), new ResourceLocation("afterthedrizzle:crops/dry_dry")),
    DRY_AVERAGE(new CropInfo(Humidity.DRY, Humidity.AVERAGE), new ResourceLocation("afterthedrizzle:crops/dry_average")),
    DRY_MOIST(new CropInfo(Humidity.DRY, Humidity.MOIST), new ResourceLocation("afterthedrizzle:crops/dry_moist")),
    DRY_HUMID(new CropInfo(Humidity.DRY, Humidity.HUMID), new ResourceLocation("afterthedrizzle:crops/dry_humid")),
    AVERAGE(new CropInfo(Humidity.AVERAGE), new ResourceLocation("afterthedrizzle:crops/average_average")),
    AVERAGE_MOIST(new CropInfo(Humidity.AVERAGE, Humidity.MOIST), new ResourceLocation("afterthedrizzle:crops/average_moist")),
    AVERAGE_HUMID(new CropInfo(Humidity.AVERAGE, Humidity.HUMID), new ResourceLocation("afterthedrizzle:crops/average_humid")),
    MOIST(new CropInfo(Humidity.MOIST), new ResourceLocation("afterthedrizzle:crops/moist_moist")),
    MOIST_HUMID(new CropInfo(Humidity.MOIST, Humidity.HUMID), new ResourceLocation("afterthedrizzle:crops/moist_humid")),
    HUMID(new CropInfo(Humidity.HUMID), new ResourceLocation("afterthedrizzle:crops/humid_humid"));

    private final CropInfo info;
    private final ResourceLocation res;

    CropType(CropInfo info, ResourceLocation res)
    {
        this.info = info;
        this.res = res;
    }

    public CropInfo getInfo()
    {
        return info;
    }

    public ResourceLocation getRes()
    {
        return res;
    }
}
