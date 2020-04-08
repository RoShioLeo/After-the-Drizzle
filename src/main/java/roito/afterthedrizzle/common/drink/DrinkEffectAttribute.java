package roito.afterthedrizzle.common.drink;

import net.minecraft.potion.Effect;

public class DrinkEffectAttribute
{
    private final Effect potion;
    private final int duration;
    private final int level;
    private final boolean extendTime;
    private final boolean upgrade;

    public DrinkEffectAttribute(Effect potionIn, int durationIn, int level, boolean extendTime, boolean upgrade)
    {
        this.potion = potionIn;
        this.duration = durationIn;
        this.level = level;
        this.extendTime = extendTime;
        this.upgrade = upgrade;
    }

    public DrinkEffectAttribute(Effect potionIn, int durationIn, int level)
    {
        this(potionIn, durationIn, level, true, true);
    }

    public Effect getPotion()
    {
        return potion;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getLevel()
    {
        return level;
    }

    public boolean shouldExtendTime()
    {
        return extendTime;
    }

    public boolean shouldUpgrade()
    {
        return upgrade;
    }
}
