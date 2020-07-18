package cloud.lemonslice.afterthedrizzle.client;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilityWorldWeather;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropHumidityInfo;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropInfoManager;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropSeasonInfo;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.WeatherType;
import cloud.lemonslice.afterthedrizzle.common.fluid.NormalFlowingFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID, value = Dist.CLIENT)
public final class ClientEventHandler
{
    public static WeatherType current = WeatherType.NONE;
    public static float currentDensity = 0.006F;

    @SubscribeEvent
    public static void addTooltips(ItemTooltipEvent event)
    {
        addCropTooltips(event);
        addArmorTempTooltips(event);
    }

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.FogDensity event)
    {
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
            if (currentDensity > 0.006F)
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

    public static void addArmorTempTooltips(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof ArmorItem)
        {
            if (event.getItemStack().getOrCreateTag().getString("Resistance").equals("Cold"))
            {
                event.getToolTip().add(1, new TranslationTextComponent("info.afterthedrizzle.environment.temperature.cold_resistance").applyTextStyle(TextFormatting.ITALIC).applyTextStyle(TextFormatting.GRAY));
            }
            else if (event.getItemStack().getOrCreateTag().getString("Resistance").equals("Heat"))
            {
                event.getToolTip().add(1, new TranslationTextComponent("info.afterthedrizzle.environment.temperature.heat_resistance").applyTextStyle(TextFormatting.ITALIC).applyTextStyle(TextFormatting.GRAY));
            }
        }
    }

    public static void addCropTooltips(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof BlockItem)
        {
            if (CropInfoManager.getHumidityCrops().contains(((BlockItem) event.getItemStack().getItem()).getBlock()))
            {
                CropHumidityInfo info = CropInfoManager.getHumidityInfo(((BlockItem) event.getItemStack().getItem()).getBlock());
                event.getToolTip().addAll(info.getTooltip());
            }
            if (CropInfoManager.getSeasonCrops().contains(((BlockItem) event.getItemStack().getItem()).getBlock()))
            {
                CropSeasonInfo info = CropInfoManager.getSeasonInfo(((BlockItem) event.getItemStack().getItem()).getBlock());
                event.getToolTip().addAll(info.getTooltip());
            }
        }
    }
}
