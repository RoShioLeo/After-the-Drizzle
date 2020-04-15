package roito.afterthedrizzle.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.registry.RegistryModule;

public final class TileEntityTypesRegistry extends RegistryModule
{
    public static final TileEntityType<StoveTileEntity> STOVE_TILE = (TileEntityType<StoveTileEntity>) TileEntityType.Builder.create(StoveTileEntity::new, BlocksRegistry.DIRT_STOVE, BlocksRegistry.STONE_STOVE).build(null).setRegistryName("stove");
    public static final TileEntityType<BambooTrayTileEntity> BAMBOO_TRAY = (TileEntityType<BambooTrayTileEntity>) TileEntityType.Builder.create(BambooTrayTileEntity::new, BlocksRegistry.BAMBOO_TRAY).build(null).setRegistryName("bamboo_tray");
    public static final TileEntityType<DrinkMakerTileEntity> DRINK_MAKER = (TileEntityType<DrinkMakerTileEntity>) TileEntityType.Builder.create(DrinkMakerTileEntity::new, BlocksRegistry.DRINK_MAKER).build(null).setRegistryName("drink_maker");
}
