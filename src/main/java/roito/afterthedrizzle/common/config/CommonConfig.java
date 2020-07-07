package roito.afterthedrizzle.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
    protected CommonConfig(ForgeConfigSpec.Builder builder)
    {
        Time.load(builder);
        Block.load(builder);
        Temperature.load(builder);
        Agriculture.load(builder);
        Season.load(builder);
        Weather.load(builder);
        Others.load(builder);
    }

    public static class Time
    {
        public static ForgeConfigSpec.IntValue dryingOutdoorsBasicTime;
        public static ForgeConfigSpec.IntValue dryingIndoorsBasicTime;
        public static ForgeConfigSpec.IntValue fermentationBasicTime;
        public static ForgeConfigSpec.IntValue bakeBasicTime;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Time");
            dryingOutdoorsBasicTime = builder.comment("The ticks of drying per item outdoors. (1 second = 20 ticks)")
                    .defineInRange("OutdoorDryingBasic", 800, 200, Integer.MAX_VALUE);
            dryingIndoorsBasicTime = builder.comment("The ticks of drying per item indoors. (1 second = 20 ticks)")
                    .defineInRange("IndoorDryingBasic", 900, 200, Integer.MAX_VALUE);
            fermentationBasicTime = builder.comment("The ticks of fermentation per item. (1 second = 20 ticks)")
                    .defineInRange("FermentationBasic", 1000, 200, Integer.MAX_VALUE);
            bakeBasicTime = builder.comment("The ticks of baking per item. (1 second = 20 ticks)")
                    .defineInRange("BakeBasic", 300, 200, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Block
    {
        public static ForgeConfigSpec.IntValue woodenBarrelCapacity;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Block");
            woodenBarrelCapacity = builder.comment("The capacity of wooden barrel. (mB)")
                    .defineInRange("WoodenBarrelCapacity", 4000, 100, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Temperature
    {
        public static ForgeConfigSpec.BooleanValue enable;
        public static ForgeConfigSpec.BooleanValue coolUnderground;
        public static ForgeConfigSpec.IntValue undergroundHeight;
        public static ForgeConfigSpec.BooleanValue fluctuation;
        public static ForgeConfigSpec.BooleanValue fluctuationDecreaseWhenRaining;
        public static ForgeConfigSpec.BooleanValue coolerIndoors;
        public static ForgeConfigSpec.BooleanValue heatInfluencedByLight;
        public static ForgeConfigSpec.BooleanValue coolerWithoutArmor;
        public static ForgeConfigSpec.BooleanValue iceMelt;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Temperature");
            enable = builder.comment("Enable apparent temperature system.")
                    .define("EnableApparentTemperature", true);
            coolUnderground = builder.comment("Player's apparent temperature won't be lower than Cool underground.")
                    .define("CoolUnderground", true);
            undergroundHeight = builder.comment("The underground height limit.")
                    .defineInRange("UndergroundHeight", 45, 0, 255);
            fluctuation = builder.comment("Enable environment apparent temperature fluctuation.")
                    .define("EnvironmentFluctuation", true);
            fluctuationDecreaseWhenRaining = builder.comment("Environment apparent temperature fluctuation will decrease when it's raining.")
                    .define("FluctuationDecreaseWhenRaining", true);
            coolerIndoors = builder.comment("Player will feel cooler indoors.")
                    .define("CoolerIndoors", true);
            heatInfluencedByLight = builder.comment("Heat will depend on blocks' light level.")
                    .define("HeatInfluencedByLight", true);
            coolerWithoutArmor = builder.comment("Player will feel cooler without wearing any armor.")
                    .define("CoolerWithoutArmor", true);
            iceMelt = builder.comment("Ice or snow layer will melt in warm place..")
                    .define("IceAndSnowMelt", true);
            builder.pop();
        }
    }

    public static class Season
    {
        public static ForgeConfigSpec.BooleanValue enable;
        public static ForgeConfigSpec.IntValue lastingDaysOfEachTerm;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Season");
            enable = builder.comment("Enable solar term season system.")
                    .define("EnableSeason", true);
            lastingDaysOfEachTerm = builder.comment("The lasting days of each term (24 in total).")
                    .defineInRange("LastingDaysOfEachTerm", 7, 1, 30);
            builder.pop();
        }
    }

    public static class Weather
    {
        public static ForgeConfigSpec.BooleanValue enable;
        public static ForgeConfigSpec.BooleanValue enableOvercast;
        public static ForgeConfigSpec.BooleanValue enableFoggy;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Weather");
            enable = builder.comment("Enable independent weather system.")
                    .define("EnableWeather", true);
            enableOvercast = builder.comment("Enable Overcast Weather.")
                    .define("EnableOvercast", true);
            enableFoggy = builder.comment("Enable Foggy Weather.")
                    .define("EnableFoggy", true);
            builder.pop();
        }
    }

    public static class Agriculture
    {
        public static ForgeConfigSpec.BooleanValue canUseBoneMeal;
        public static ForgeConfigSpec.BooleanValue useAshAsBoneMeal;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Agriculture");
            canUseBoneMeal = builder.comment("Can bone meal be used to grow crops?")
                    .define("BoneMeal", false);
            useAshAsBoneMeal = builder.comment("Can ash be used as bone meal?")
                    .define("Ash", true);
            builder.pop();
        }
    }

    public static class Others
    {
        public static ForgeConfigSpec.BooleanValue woodDropsAshWhenBurning;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("Others");
            woodDropsAshWhenBurning = builder.comment("Wooden blocks will drop ashes when burning.")
                    .define("WoodDropsAshWhenBurning", true);
            builder.pop();
        }
    }
}

