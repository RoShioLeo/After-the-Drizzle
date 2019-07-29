package roito.cultivage.client.render;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import roito.cultivage.Cultivage;
import roito.cultivage.common.tileentity.TileEntityFlatBasket;
import roito.cultivage.common.tileentity.TileEntityStove;

@Mod.EventBusSubscriber(modid = Cultivage.MODID, value = Side.CLIENT)
public final class TESRender
{
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlatBasket.class, new TESRFlatBasket());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStove.class, new TESRStove());
	}
}
