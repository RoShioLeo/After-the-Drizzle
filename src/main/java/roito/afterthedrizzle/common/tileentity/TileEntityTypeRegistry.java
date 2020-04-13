package roito.afterthedrizzle.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.registry.RegistryModule;

public final class TileEntityTypeRegistry extends RegistryModule
{
    public static final TileEntityType<StoveTileEntity> STOVE_TILE_ENTITY_TYPE = (TileEntityType<StoveTileEntity>) TileEntityType.Builder.create(StoveTileEntity::new, BlocksRegistry.DIRT_STOVE, BlocksRegistry.LIT_DIRT_STOVE, BlocksRegistry.STONE_STOVE, BlocksRegistry.LIT_STONE_STOVE).build(null).setRegistryName("stove");
    public static final TileEntityType<BambooTrayTileEntity> BAMBOO_TRAY_TILE_ENTITY_TYPE = (TileEntityType<BambooTrayTileEntity>) TileEntityType.Builder.create(BambooTrayTileEntity::new, BlocksRegistry.BAMBOO_TRAY).build(null).setRegistryName("bamboo_tray");
}
