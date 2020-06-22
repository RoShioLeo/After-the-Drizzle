package roito.afterthedrizzle.client;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.environment.crop.CropInfo;
import roito.afterthedrizzle.common.environment.crop.CropInfoManager;
import roito.afterthedrizzle.common.environment.weather.WeatherType;
import roito.afterthedrizzle.common.fluid.NormalFlowingFluidBlock;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID, value = Dist.CLIENT)
public final class ClientEventHander
{
    private static float currentDensity = 0.0F;
    private static float trendDensity = 0.0F;

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
            if (data.getCurrentWeather().getType() == WeatherType.FOGGY)
            {
                Entity entity = event.getInfo().getRenderViewEntity();
                BlockPos blockpos = new BlockPos(entity);
                if (entity.getEntityWorld().getLightFor(LightType.SKY, blockpos) > 0)
                {
                    event.setDensity(0.1F);
                    event.setCanceled(true);
                }
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
        if (event.getItemStack().getItem() instanceof BlockItem && CropInfoManager.getCrops().contains(((BlockItem) event.getItemStack().getItem()).getBlock()))
        {
            CropInfo info = CropInfoManager.getInfo(((BlockItem) event.getItemStack().getItem()).getBlock());
            event.getToolTip().addAll(info.getTooltip());
        }
    }
}
