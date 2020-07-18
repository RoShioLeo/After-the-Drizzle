package cloud.lemonslice.afterthedrizzle.client.render;

import cloud.lemonslice.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

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
