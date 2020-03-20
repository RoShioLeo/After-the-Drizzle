package roito.afterthedrizzle.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.tileentity.*;

public final class TileEntitiesRegistry
{
    public TileEntitiesRegistry()
    {
        GameRegistry.registerTileEntity(TileEntityFlatBasket.class, new ResourceLocation(AfterTheDrizzle.MODID, "flat_basket"));
        GameRegistry.registerTileEntity(TileEntityStove.class, new ResourceLocation(AfterTheDrizzle.MODID, "stove"));
        GameRegistry.registerTileEntity(TileEntityStoneMill.class, new ResourceLocation(AfterTheDrizzle.MODID, "stone_mill"));
        GameRegistry.registerTileEntity(TileEntityWoodenBarrel.class, new ResourceLocation(AfterTheDrizzle.MODID, "wooden_barrel"));
        GameRegistry.registerTileEntity(TileEntityDrinkMaker.class, new ResourceLocation(AfterTheDrizzle.MODID, "drink_maker"));
    }
}
