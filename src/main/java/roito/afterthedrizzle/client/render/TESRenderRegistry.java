package roito.afterthedrizzle.client.render;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.common.tileentity.BambooTrayTileEntity;
import roito.afterthedrizzle.common.tileentity.DrinkMakerTileEntity;
import roito.afterthedrizzle.common.tileentity.StoveTileEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TESRenderRegistry
{
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(StoveTileEntity.class, new StoveTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(BambooTrayTileEntity.class, new BambooTrayTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(DrinkMakerTileEntity.class, new DrinkMakerTESR());
    }
}
