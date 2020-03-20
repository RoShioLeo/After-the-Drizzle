package roito.afterthedrizzle.registry.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.registry.RegisterManager;

import java.lang.reflect.Field;

public final class FluidsRegistry
{
    public static final Fluid SUGARY_WATER = new Fluid("sugary_water", new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/sugary_water_still"), new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/sugary_water_flow")).setGaseous(true).setDensity(Integer.MAX_VALUE);
    public static final Fluid APPLE_JUICE = new Fluid("apple_juice", new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/apple_juice_still"), new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/apple_juice_flow")).setGaseous(true).setDensity(Integer.MAX_VALUE);
    public static final Fluid BEET_JUICE = new Fluid("beet_juice", new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/beet_juice_still"), new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/beet_juice_flow")).setGaseous(true).setDensity(Integer.MAX_VALUE);
    public static final Fluid CARROT_JUICE = new Fluid("carrot_juice", new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/carrot_juice_still"), new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/carrot_juice_flow")).setGaseous(true).setDensity(Integer.MAX_VALUE);
    public static final Fluid SUGAR_CANE_JUICE = new Fluid("sugar_cane_juice", new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/sugar_cane_juice_still"), new ResourceLocation(AfterTheDrizzle.MODID, "blocks/fluid/sugar_cane_juice_flow")).setGaseous(true).setDensity(Integer.MAX_VALUE);

    public FluidsRegistry()
    {
        for (Field field : getClass().getFields())
        {
            Object o;
            try
            {
                o = field.get(null);
                if (o instanceof Fluid)
                {
                    RegisterManager.registerFluids((Fluid) o);
                }
            }
            catch (Exception ignored)
            {
            }
        }
    }
}
