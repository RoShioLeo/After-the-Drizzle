package cloud.lemonslice.afterthedrizzle.common.item.food;

import net.minecraft.item.Food;

public final class NormalFoods
{
    public static final Food DRIED_BEETROOT = (new Food.Builder()).hunger(3).saturation(0.6F).build();
    public static final Food DRIED_CARROT = (new Food.Builder()).hunger(5).saturation(0.6F).build();

    public static final Food BEEF_JERKY = (new Food.Builder()).hunger(9).saturation(1.0F).meat().build();
    public static final Food PORK_JERKY = (new Food.Builder()).hunger(9).saturation(1.0F).meat().build();
    public static final Food CHICKEN_JERKY = (new Food.Builder()).hunger(8).saturation(0.8F).meat().build();
    public static final Food RABBIT_JERKY = (new Food.Builder()).hunger(6).saturation(1.0F).meat().build();
    public static final Food MUTTON_JERKY = (new Food.Builder()).hunger(8).saturation(1.0F).meat().build();
}
