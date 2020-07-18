package cloud.lemonslice.afterthedrizzle.common.drink;

import net.minecraft.fluid.Fluid;

import java.util.HashMap;
import java.util.Map;

public final class DrinkEffectsManager
{
    private final static Map<Fluid, DrinkEffectAttribute[]> DRINK_EFFECTS = new HashMap<>();

    public static void registerDrinkEffects(Fluid fluid, DrinkEffectAttribute... drinkEffects)
    {
        DRINK_EFFECTS.put(fluid, drinkEffects);
    }

    public static DrinkEffectAttribute[] getDrinkEffects(Fluid key)
    {
        return DRINK_EFFECTS.getOrDefault(key, new DrinkEffectAttribute[0]);
    }
}
