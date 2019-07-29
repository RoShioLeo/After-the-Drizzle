package roito.cultivage.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import roito.cultivage.Cultivage;
import roito.cultivage.common.tileentity.TileEntityFlatBasket;
import roito.cultivage.common.tileentity.TileEntityStove;
import roito.silveroakoutpost.register.annotation.Load;

public final class TileEntitiesRegistry
{
	@Load
	public static void registerTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntityFlatBasket.class, new ResourceLocation(Cultivage.MODID, "flat_basket"));
		GameRegistry.registerTileEntity(TileEntityStove.class, new ResourceLocation(Cultivage.MODID, "stove"));
	}
}
