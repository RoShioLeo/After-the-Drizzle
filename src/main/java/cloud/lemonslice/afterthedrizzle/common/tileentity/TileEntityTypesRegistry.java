package cloud.lemonslice.afterthedrizzle.common.tileentity;

import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.registry.RegistryModule;
import net.minecraft.tileentity.TileEntityType;

public final class TileEntityTypesRegistry extends RegistryModule
{
    public static final TileEntityType<StoveTileEntity> STOVE_TILE = (TileEntityType<StoveTileEntity>) TileEntityType.Builder.create(StoveTileEntity::new, BlocksRegistry.DIRT_STOVE, BlocksRegistry.STONE_STOVE).build(null).setRegistryName("stove");
    public static final TileEntityType<BambooTrayTileEntity> BAMBOO_TRAY = (TileEntityType<BambooTrayTileEntity>) TileEntityType.Builder.create(BambooTrayTileEntity::new, BlocksRegistry.BAMBOO_TRAY, BlocksRegistry.STONE_CATAPULT_BOARD_WITH_TRAY).build(null).setRegistryName("bamboo_tray");
    public static final TileEntityType<DrinkMakerTileEntity> DRINK_MAKER = (TileEntityType<DrinkMakerTileEntity>) TileEntityType.Builder.create(DrinkMakerTileEntity::new, BlocksRegistry.DRINK_MAKER).build(null).setRegistryName("drink_maker");
    public static final TileEntityType<TeapotTileEntity> TEAPOT = (TileEntityType<TeapotTileEntity>) TileEntityType.Builder.create(TeapotTileEntity::new, BlocksRegistry.TEAPOT).build(null).setRegistryName("teapot");
    public static final TileEntityType<TeaCupTileEntity> PORCELAIN_CUP = (TileEntityType<TeaCupTileEntity>) TileEntityType.Builder.create(() -> new TeaCupTileEntity(250), BlocksRegistry.TEAPOT).build(null).setRegistryName("porcelain_cup");
}
