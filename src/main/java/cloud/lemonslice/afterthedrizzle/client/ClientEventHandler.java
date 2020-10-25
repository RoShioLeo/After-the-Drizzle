package cloud.lemonslice.afterthedrizzle.client;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilityWorldWeather;
import cloud.lemonslice.afterthedrizzle.common.config.ServerConfig;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.WeatherType;
import cloud.lemonslice.afterthedrizzle.common.fluid.NormalFlowingFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cloud.lemonslice.afterthedrizzle.common.handler.CommonEventHandler.addArmorTempTooltips;
import static cloud.lemonslice.afterthedrizzle.common.handler.CommonEventHandler.addCropTooltips;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID, value = Dist.CLIENT)
public final class ClientEventHandler
{
    public static WeatherType current = WeatherType.NONE;
    public static float currentDensity = 0.005F;

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.FogDensity event)
    {
        if (ServerConfig.Weather.enable.get())
            AfterTheDrizzle.proxy.getClientWorld().getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data ->
            {
                if (current == WeatherType.FOGGY)
                {
                    Entity entity = event.getInfo().getRenderViewEntity();
                    if (entity instanceof PlayerEntity && ((PlayerEntity) entity).isPotionActive(Effects.BLINDNESS))
                    {
                        currentDensity = 0.006F;
                        return;
                    }
                    BlockPos blockpos = new BlockPos(entity);
                    if (entity.getEntityWorld().getLightFor(LightType.SKY, blockpos) > 0)
                    {
                        if (currentDensity < 0.1F) currentDensity += 0.0001F;
                        event.setDensity(currentDensity);
                        event.setCanceled(true);
                        return;
                    }
                }
                else if (current == WeatherType.STORM)
                {
                    Entity entity = event.getInfo().getRenderViewEntity();
                    if (entity instanceof PlayerEntity && ((PlayerEntity) entity).isPotionActive(Effects.BLINDNESS))
                    {
                        currentDensity = 0.006F;
                        return;
                    }
                    BlockPos blockpos = new BlockPos(entity);
                    if (entity.getEntityWorld().getLightFor(LightType.SKY, blockpos) > 0)
                    {
                        if (currentDensity < 0.02F) currentDensity += 0.0001F;
                        event.setDensity(currentDensity);
                        event.setCanceled(true);
                        return;
                    }
                }
                if (currentDensity > 0.0055F)
                {
                    currentDensity -= 0.0001F;
                    event.setDensity(currentDensity);
                    event.setCanceled(true);
                }
            });
    }

    @SubscribeEvent
    public static void addFogColor(EntityViewRenderEvent.FogColors event)
    {
        BlockState state = event.getInfo().getFluidState().getBlockState();

        if (state.getBlock() instanceof NormalFlowingFluidBlock)
        {
            int color = state.getFluidState().getFluid().getAttributes().getColor();

            event.setRed((float) (((color >> 16) & 255) / 255.0));
            event.setGreen((float) (((color >> 8) & 255) / 255.0));
            event.setBlue((float) ((color & 255) / 255.0));
        }
    }

    @SubscribeEvent
    public static void addTooltips(ItemTooltipEvent event)
    {
        if (ServerConfig.Season.enable.get())
        {
            addCropTooltips(event);
        }
        addArmorTempTooltips(event);
    }
}
