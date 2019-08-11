package roito.afterthedrizzle.client.render;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.tileentity.TileEntityFlatBasket;
import roito.afterthedrizzle.common.tileentity.TileEntityStoneMill;
import roito.afterthedrizzle.common.tileentity.TileEntityStove;
import roito.afterthedrizzle.common.tileentity.TileEntityWoodenBarrel;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID, value = Side.CLIENT)
public final class TESRender
{
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlatBasket.class, new TESRFlatBasket());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStove.class, new TESRStove());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStoneMill.class, new TESRStoneMill());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenBarrel.class, new TESRWoodenBarrel());
    }
}