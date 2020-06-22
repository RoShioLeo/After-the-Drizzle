package roito.afterthedrizzle.common.environment.weather;

import net.minecraft.nbt.CompoundNBT;

public class WeatherEvent
{
    private final WeatherType type;
    private int lastingTicks;
    public final static WeatherEvent EMPTY = new WeatherEvent(WeatherType.SUNNY, 0);

    public WeatherEvent(WeatherType type, int lastingTicks)
    {
        this.type = type;
        this.lastingTicks = lastingTicks;
    }

    public int getLastingTicks()
    {
        return lastingTicks;
    }

    public void setLastingTicks(int lastingTicks)
    {
        this.lastingTicks = lastingTicks;
    }

    public void tick()
    {
        this.lastingTicks--;
    }

    public boolean isValid()
    {
        return this.lastingTicks > 0;
    }

    public WeatherType getType()
    {
        return type;
    }

    public CompoundNBT writeToNBT()
    {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("WeatherType", type.ordinal());
        compound.putInt("LastingTicks", lastingTicks);
        return compound;
    }

    public static WeatherEvent readFromNBT(CompoundNBT nbt)
    {
        WeatherType type = WeatherType.values()[nbt.getInt("WeatherType")];
        int ticks = nbt.getInt("LastingTicks");
        return new WeatherEvent(type, ticks);
    }
}
