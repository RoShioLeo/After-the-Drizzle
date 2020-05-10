package roito.afterthedrizzle.common.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilitiesRegistry
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(CapabilityPlayerTemperature.Data.class, new CapabilityPlayerTemperature.Storage(), CapabilityPlayerTemperature.Data::new);
    }
}
