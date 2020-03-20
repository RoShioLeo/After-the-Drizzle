package roito.afterthedrizzle.client;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.helper.ModelHelper;

import static roito.afterthedrizzle.registry.RegisterManager.*;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = AfterTheDrizzle.MODID)
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        ModelHelper.clear();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        for (Item item : MODEL_ITEMS)
        {
            ModelHelper.registerItemVariants(AfterTheDrizzle.MODID, item);
            ModelHelper.registerRender(AfterTheDrizzle.MODID, item);
        }
        for (BlockFluidBase fluid : FLUID_BLOCK)
        {
            ModelHelper.registerFluidRender(fluid, new ResourceLocation(AfterTheDrizzle.MODID, "fluid"));
        }
        ModelHelper.clear();
    }

    @SubscribeEvent
    public static void registerFluidSpirit(TextureStitchEvent.Pre event)
    {
        TextureMap textureMap = event.getMap();
        for (Fluid fluid : FLUID)
        {
            textureMap.registerSprite(fluid.getFlowing());
            textureMap.registerSprite(fluid.getStill());
        }
    }
}
