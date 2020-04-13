package roito.afterthedrizzle.common.inventory;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.registry.RegistryModule;

public final class ContainerTypeRegistry extends RegistryModule
{
    public final static ContainerType<?> STOVE_CONTAINER = IForgeContainerType.create(((windowId, inv, data) -> new StoveContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("stove");
    public final static ContainerType<?> BAMBOO_TRAY_CONTAINER = IForgeContainerType.create(((windowId, inv, data) -> new BambooTrayContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("bamboo_tray");
}
