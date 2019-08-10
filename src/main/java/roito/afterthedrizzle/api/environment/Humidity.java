package roito.afterthedrizzle.api.environment;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.config.ConfigMain;

public enum Humidity
{
    ARID,
    DRY,
    AVERAGE,
    MOIST,
    HUMID;

    public int getId()
    {
        return this.ordinal() + 1;
    }

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    @SideOnly(Side.CLIENT)
    public String getTranslation()
    {
        return I18n.format(AfterTheDrizzle.MODID + ".environment.humidity." + getName());
    }

    public int getOutdoorDryingTicks()
    {
        switch (this)
        {
            case ARID:
                return (int) (ConfigMain.time.dryingOutdoorsBasicTime * 0.5F);
            case DRY:
                return (int) (ConfigMain.time.dryingOutdoorsBasicTime * 0.75F);
            case AVERAGE:
                return ConfigMain.time.dryingOutdoorsBasicTime;
            case MOIST:
                return (int) (ConfigMain.time.dryingOutdoorsBasicTime * 1.25F);
            default:
                return (int) (ConfigMain.time.dryingOutdoorsBasicTime * 1.5F);
        }
    }

    public int getIndoorDryingTicks()
    {
        switch (this)
        {
            case ARID:
                return (int) (ConfigMain.time.dryingIndoorsBasicTime * 0.5F);
            case DRY:
                return (int) (ConfigMain.time.dryingIndoorsBasicTime * 0.75F);
            case AVERAGE:
                return ConfigMain.time.dryingIndoorsBasicTime;
            case MOIST:
                return (int) (ConfigMain.time.dryingIndoorsBasicTime * 1.25F);
            default:
                return (int) (ConfigMain.time.dryingIndoorsBasicTime * 1.5F);
        }
    }

    public int getFermentationTicks()
    {
        switch (this)
        {
            case ARID:
                return (int) (ConfigMain.time.fermentationBasicTime * 0.5F);
            case DRY:
                return (int) (ConfigMain.time.fermentationBasicTime * 0.75F);
            case AVERAGE:
                return ConfigMain.time.fermentationBasicTime;
            case MOIST:
                return (int) (ConfigMain.time.fermentationBasicTime * 1.25F);
            default:
                return (int) (ConfigMain.time.fermentationBasicTime * 1.5F);
        }
    }

    public static Humidity getHumid(Rainfall rainfall, Temperature temperature)
    {
        int rOrder = rainfall.ordinal();
        int tOrder = temperature.ordinal();
        int level = Math.max(0, rOrder - Math.abs(rOrder - tOrder) / 2);
        return Humidity.values()[level];
    }

    public static Humidity getHumid(float rainfall, float temperature)
    {
        return Humidity.getHumid(Rainfall.getRainfallLevel(rainfall), Temperature.getTemperatureLevel(temperature));
    }
}
