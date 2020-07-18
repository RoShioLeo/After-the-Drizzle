package cloud.lemonslice.afterthedrizzle.data;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.data.provider.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

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
            gen.addProvider(new NormalBlockTagsProvider(gen));
            gen.addProvider(new NormalFluidTagsProvider(gen));
            gen.addProvider(new RecipesProvider(gen));
        }
    }
}
