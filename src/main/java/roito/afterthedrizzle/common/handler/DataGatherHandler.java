package roito.afterthedrizzle.common.handler;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.data.provider.CropInfoProvider;
import roito.afterthedrizzle.common.data.provider.NormalItemTagsProvider;
import roito.afterthedrizzle.common.data.provider.RecipesProvider;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGatherHandler
{
    @SubscribeEvent
    public static void onDataGather(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        if (event.includeServer())
        {
            gen.addProvider(new CropInfoProvider(gen));
            gen.addProvider(new NormalItemTagsProvider(gen));
            gen.addProvider(new RecipesProvider(gen));
        }
    }
}
