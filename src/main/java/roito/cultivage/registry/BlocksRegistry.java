package roito.cultivage.registry;

import net.minecraft.block.Block;
import roito.cultivage.Cultivage;
import roito.cultivage.common.block.BlockFlatBasket;
import roito.silveroakoutpost.register.RegisterType;
import roito.silveroakoutpost.register.annotation.AutoRegistry;
import roito.silveroakoutpost.register.annotation.RegisterInfo;

@AutoRegistry(modid = Cultivage.MODID, registerType = RegisterType.BLOCK)
public final class BlocksRegistry
{
	@RegisterInfo("flat_basket")
	public static final Block FLAT_BASKET = new BlockFlatBasket();
}
