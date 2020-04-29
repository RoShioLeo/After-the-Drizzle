package roito.afterthedrizzle.common.environment.crop;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.environment.Humidity;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class CropGrowthHandler
{
    @SubscribeEvent
    public static void canCropGrowUp(BlockEvent.CropGrowEvent.Pre event)
    {
        IWorld world = event.getWorld();
        BlockPos pos = event.getPos();
        Humidity env = Humidity.getHumid(world.getBiome(pos).getDownfall(), world.getBiome(pos).getTemperature(pos));
        CropInfo info = CropInfoManager.getInfo(event.getState().getBlock());
        if (info != null)
        {
            float f = info.getGrowChance(env);
            System.out.println(f);
            if (f == 0)
            {
                event.setResult(Event.Result.DENY);
            }
            else if (f == 1.0F)
            {
                event.setResult(Event.Result.DEFAULT);
            }
            else
            {
                if (world.getRandom().nextInt(1000) < 1000 * f)
                {
                    event.setResult(Event.Result.DEFAULT);
                }
                else
                {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
