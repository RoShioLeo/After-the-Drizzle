package roito.afterthedrizzle.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig
{

    protected ClientConfig(ForgeConfigSpec.Builder builder)
    {
        GUI.load(builder);
    }

    public static class GUI
    {
        public static ForgeConfigSpec.IntValue playerTemperatureX;
        public static ForgeConfigSpec.IntValue playerTemperatureY;

        private static void load(ForgeConfigSpec.Builder builder)
        {
            builder.push("GUI");
            playerTemperatureX = builder.comment("The position X of Player Temperature UI")
                    .translation("config.afterthedrizzle.gui.player_temperature_x")
                    .defineInRange("PlayerTemperatureX", 10, Integer.MIN_VALUE, Integer.MAX_VALUE);
            playerTemperatureY = builder.comment("The position Y of Player Temperature UI")
                    .translation("config.afterthedrizzle.gui.player_temperature_y")
                    .defineInRange("PlayerTemperatureY", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
