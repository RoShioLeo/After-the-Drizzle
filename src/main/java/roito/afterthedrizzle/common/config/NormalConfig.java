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
    public static ForgeConfigSpec.IntValue fermentationBasicTime;
    public static ForgeConfigSpec.IntValue bakeBasicTime;
    public static ForgeConfigSpec.IntValue woodenBarrelCapacity;
    public static ForgeConfigSpec.IntValue drinkMakerCapacity;
    public static ForgeConfigSpec.BooleanValue useAshAsBoneMeal;

    private NormalConfig(ForgeConfigSpec.Builder builder)
    {
        builder.push("Time");
        dryingOutdoorsBasicTime = builder.comment("The ticks of drying per item outdoors. (1 second = 20 ticks)")
                .translation("atd.config.time.outdoorDryingBasic")
                .defineInRange("outdoorDryingBasic", 800, 200, Integer.MAX_VALUE);

        fermentationBasicTime = builder.comment("The ticks of fermentation per item. (1 second = 20 ticks)")
                .translation("atd.config.time.fermentationBasic")
                .defineInRange("fermentationBasic", 1000, 200, Integer.MAX_VALUE);

        bakeBasicTime = builder.comment("The ticks of baking per item. (1 second = 20 ticks)")
                .translation("atd.config.time.bakeBasic")
                .defineInRange("bakeBasic", 300, 200, Integer.MAX_VALUE);
        builder.pop();

        builder.push("Blocks");
        woodenBarrelCapacity = builder.comment("The capacity of wooden barrel. (mB)")
                .translation("atd.config.blocks.woodenBarrelCapacity")
                .defineInRange("woodenBarrelCapacity", 4000, 100, Integer.MAX_VALUE);
        drinkMakerCapacity = builder.comment("The capacity of drink maker. (mB)")
                .translation("atd.config.blocks.drinkMakerCapacity")
                .defineInRange("drinkMakerCapacity", 2000, 100, Integer.MAX_VALUE);
        builder.pop();

        builder.push("Others");
        useAshAsBoneMeal = builder.comment("Can ash be used as bone meal?")
                .translation("atd.config.others.ash")
                .define("ash", true);
        builder.pop();
    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.ConfigReloading event)
    {
        ((CommentedFileConfig) event.getConfig().getConfigData()).load();
    }
}

