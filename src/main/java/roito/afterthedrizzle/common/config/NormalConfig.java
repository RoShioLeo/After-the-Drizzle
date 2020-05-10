package roito.afterthedrizzle.common.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public final class NormalConfig
{
    public static final ForgeConfigSpec COMMON_CONFIG = new ForgeConfigSpec.Builder().configure(NormalConfig::new).getRight();

    public static ForgeConfigSpec.IntValue dryingOutdoorsBasicTime;
    public static ForgeConfigSpec.IntValue dryingIndoorsBasicTime;
    public static ForgeConfigSpec.IntValue fermentationBasicTime;
    public static ForgeConfigSpec.IntValue bakeBasicTime;

    public static ForgeConfigSpec.IntValue woodenBarrelCapacity;
    public static ForgeConfigSpec.IntValue drinkMakerCapacity;

    public static ForgeConfigSpec.IntValue playerTemperatureX;
    public static ForgeConfigSpec.IntValue playerTemperatureY;

    public static ForgeConfigSpec.BooleanValue canUseBoneMeal;
    public static ForgeConfigSpec.BooleanValue useAshAsBoneMeal;

    private NormalConfig(ForgeConfigSpec.Builder builder)
    {
        builder.push("Time");
        dryingOutdoorsBasicTime = builder.comment("The ticks of drying per item outdoors. (1 second = 20 ticks)")
                .translation("atd.config.time.outdoor_drying_basic")
                .defineInRange("OutdoorDryingBasic", 800, 200, Integer.MAX_VALUE);

        dryingIndoorsBasicTime = builder.comment("The ticks of drying per item indoors. (1 second = 20 ticks)")
                .translation("atd.config.time.indoor_drying_basic")
                .defineInRange("IndoorDryingBasic", 900, 200, Integer.MAX_VALUE);

        fermentationBasicTime = builder.comment("The ticks of fermentation per item. (1 second = 20 ticks)")
                .translation("atd.config.time.fermentation_basic")
                .defineInRange("FermentationBasic", 1000, 200, Integer.MAX_VALUE);

        bakeBasicTime = builder.comment("The ticks of baking per item. (1 second = 20 ticks)")
                .translation("atd.config.time.bake_basic")
                .defineInRange("BakeBasic", 300, 200, Integer.MAX_VALUE);
        builder.pop();

        builder.push("Blocks");
        woodenBarrelCapacity = builder.comment("The capacity of wooden barrel. (mB)")
                .translation("atd.config.blocks.wooden_barrel_capacity")
                .defineInRange("WoodenBarrelCapacity", 4000, 100, Integer.MAX_VALUE);
        drinkMakerCapacity = builder.comment("The capacity of drink maker. (mB)")
                .translation("atd.config.blocks.drink_maker_capacity")
                .defineInRange("DrinkMakerCapacity", 2000, 100, Integer.MAX_VALUE);
        builder.pop();

        builder.push("HUD");
        playerTemperatureX = builder.comment("The position X of Player Temperature HUD")
                .translation("atd.config.hud.player_temperature_x")
                .defineInRange("PlayerTemperatureX", 10, Integer.MIN_VALUE, Integer.MAX_VALUE);
        playerTemperatureY = builder.comment("The position Y of Player Temperature HUD")
                .translation("atd.config.hud.player_temperature_y")
                .defineInRange("PlayerTemperatureY", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);
        builder.pop();

        builder.push("Others");
        canUseBoneMeal = builder.comment("Can bone meal be used to grow crops?")
                .translation("atd.config.others.bone_meal")
                .define("BoneMeal", false);
        useAshAsBoneMeal = builder.comment("Can ash be used as bone meal?")
                .translation("atd.config.others.ash")
                .define("Ash", true);
        builder.pop();
    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.Reloading event)
    {
        ((CommentedFileConfig) event.getConfig().getConfigData()).load();
    }
}

