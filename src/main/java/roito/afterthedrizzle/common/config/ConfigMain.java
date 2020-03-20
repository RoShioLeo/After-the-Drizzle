package roito.afterthedrizzle.common.config;

import net.minecraftforge.common.config.Config;
import roito.afterthedrizzle.AfterTheDrizzle;

@Config(modid = AfterTheDrizzle.MODID)
@Config.LangKey("afterthedrizzle.config")
public final class ConfigMain
{
    @Config.Name("Time")
    @Config.LangKey("afterthedrizzle.config.time")
    public static final Time time = new Time();

    @Config.Name("Blocks")
    @Config.LangKey("afterthedrizzle.config.blocks")
    public static final Blocks blocks = new Blocks();

    @Config.Name("Others")
    @Config.LangKey("afterthedrizzle.config.others")
    public static final Others others = new Others();

    public static final class Time
    {
        @Config.Comment("The ticks of drying per item outdoors. (1 second = 20 ticks)")
        @Config.LangKey("afterthedrizzle.config.time.outdoor_drying_basic")
        @Config.Name("DryingOutdoorsBasicTime")
        @Config.RangeInt(min = 250, max = 12000)
        public int dryingOutdoorsBasicTime = 800;

        @Config.Comment("The ticks of drying per item indoors. (1 second = 20 ticks)")
        @Config.LangKey("afterthedrizzle.config.time.indoor_drying_basic")
        @Config.Name("DryingIndoorsBasicTime")
        @Config.RangeInt(min = 250, max = 12000)
        public int dryingIndoorsBasicTime = 900;

        @Config.Comment("The ticks of fermentation per item. (1 second = 20 ticks)")
        @Config.LangKey("afterthedrizzle.config.time.fermentation_basic")
        @Config.Name("FermentationBasicTime")
        @Config.RangeInt(min = 500, max = 12000)
        public int fermentationBasicTime = 1000;

        @Config.Comment("The ticks of baking per item. (1 second = 20 ticks)")
        @Config.LangKey("afterthedrizzle.config.time.bake_basic")
        @Config.Name("BakeBasicTime")
        @Config.RangeInt(min = 200, max = 12000)
        public int bakeBasicTime = 300;
    }

    public static final class Blocks
    {
        @Config.Comment("The capacity of wooden barrel. (mB)")
        @Config.LangKey("afterthedrizzle.config.block.wooden_barrel")
        @Config.Name("WoodenBarrelCapacity")
        @Config.RangeInt(min = 100, max = 12000)
        public int woodenBarrelCapacity = 4000;

        @Config.Comment("The capacity of drink maker. (mB)")
        @Config.LangKey("afterthedrizzle.config.block.drink_maker")
        @Config.Name("DrinkMakerCapacity")
        @Config.RangeInt(min = 100, max = 12000)
        public int drinkMakerCapacity = 2000;
    }

    public static final class Others
    {
        @Config.Comment("Can ash be used as bone meal?")
        @Config.LangKey("afterthedrizzle.config.others.ash")
        @Config.Name("UseAshAsBoneMeal")
        public boolean useAshAsBoneMeal = true;
    }
}
