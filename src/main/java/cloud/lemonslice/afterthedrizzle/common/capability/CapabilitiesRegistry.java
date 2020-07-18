package cloud.lemonslice.afterthedrizzle.common.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilitiesRegistry
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(CapabilityPlayerTemperature.Data.class, new CapabilityPlayerTemperature.Storage(), CapabilityPlayerTemperature.Data::new);
        CapabilityManager.INSTANCE.register(CapabilitySolarTermTime.Data.class, new CapabilitySolarTermTime.Storage(), CapabilitySolarTermTime.Data::new);
        CapabilityManager.INSTANCE.register(CapabilityWorldWeather.Data.class, new CapabilityWorldWeather.Storage(), CapabilityWorldWeather.Data::new);
    }
}
