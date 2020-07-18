package cloud.lemonslice.afterthedrizzle.common.potion;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class NormalEffect extends Effect
{
    public NormalEffect(String name, int liquidColorIn, EffectType typeIn)
    {
        super(typeIn, liquidColorIn);
        this.setRegistryName(name);
    }
}
