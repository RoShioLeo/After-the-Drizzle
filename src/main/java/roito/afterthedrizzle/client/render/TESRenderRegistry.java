package roito.afterthedrizzle.client.render;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TESRenderRegistry
{
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesRegistry.STOVE_TILE, StoveTESR::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesRegistry.BAMBOO_TRAY, BambooTrayTESR::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesRegistry.DRINK_MAKER, DrinkMakerTESR::new);
    }
}
