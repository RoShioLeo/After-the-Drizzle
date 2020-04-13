package roito.afterthedrizzle.common.drink;

import net.minecraft.fluid.Fluid;
import net.minecraft.potion.Effects;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;

import java.util.HashMap;
import java.util.Map;

public final class DrinkEffectsManager
{
    private final static Map<Fluid, DrinkEffectAttribute[]> DRINK_EFFECTS = new HashMap<>();

    public DrinkEffectsManager()
    {
        registerDrinkEffects(FluidsRegistry.SUGARY_WATER_STILL.get(), new DrinkEffectAttribute(Effects.SPEED, 2, 0));
    }

    public static void registerDrinkEffects(Fluid fluid, DrinkEffectAttribute... drinkEffects)
    {
        DRINK_EFFECTS.put(fluid, drinkEffects);
    }

    public static DrinkEffectAttribute[] getDrinkEffects(Fluid key)
    {
        return DRINK_EFFECTS.getOrDefault(key, new DrinkEffectAttribute[0]);
    }
}
