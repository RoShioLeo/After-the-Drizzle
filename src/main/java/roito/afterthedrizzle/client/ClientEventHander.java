package roito.afterthedrizzle.client;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.environment.crop.CropInfo;
import roito.afterthedrizzle.common.environment.crop.CropInfoManager;
import roito.afterthedrizzle.common.fluid.NormalFlowingFluidBlock;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID, value = Dist.CLIENT)
public final class ClientEventHander
{
    @SubscribeEvent
    public static void addCropTooltips(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof BlockItem && CropInfoManager.getCrops().contains(((BlockItem) event.getItemStack().getItem()).getBlock()))
        {
            CropInfo info = CropInfoManager.getInfo(((BlockItem) event.getItemStack().getItem()).getBlock());
            event.getToolTip().addAll(info.getTooltip());
        }
    }

    public static void onFogRender(EntityViewRenderEvent.FogDensity event)
    {

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
}
