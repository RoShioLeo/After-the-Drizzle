package roito.cultivage.common.config;

import net.minecraftforge.common.config.Config;
import roito.cultivage.Cultivage;

@Config(modid = Cultivage.MODID)
@Config.LangKey("cultivage.config")
public final class ConfigMain
{
    @Config.Name("Time")
    @Config.LangKey("cultivage.config.time")
    public static final Time time = new Time();

    public static final class Time
    {
        @Config.Comment("The ticks of drying per item outdoors. (1 second = 20 ticks)")
        @Config.LangKey("cultivage.config.time.outdoor_drying_basic")
        @Config.Name("DryingOutdoorsBasicTime")
        @Config.RangeInt(min = 250, max = 12000)
        public int dryingOutdoorsBasicTime = 800;

        @Config.Comment("The ticks of drying per item indoors. (1 second = 20 ticks)")
        @Config.LangKey("cultivage.config.time.indoor_drying_basic")
        @Config.Name("DryingIndoorsBasicTime")
        @Config.RangeInt(min = 250, max = 12000)
        public int dryingIndoorsBasicTime = 900;

        @Config.Comment("The ticks of fermentation per item. (1 second = 20 ticks)")
        @Config.LangKey("cultivage.config.time.fermentation_basic")
        @Config.Name("FermentationBasicTime")
        @Config.RangeInt(min = 500, max = 12000)
        public int fermentationBasicTime = 1000;

        @Config.Comment("The ticks of baking per item. (1 second = 20 ticks)")
        @Config.LangKey("cultivage.config.time.bake_basic")
        @Config.Name("BakeBasicTime")
        @Config.RangeInt(min = 200, max = 12000)
        public int BakeBasicTime = 300;
    }
}
