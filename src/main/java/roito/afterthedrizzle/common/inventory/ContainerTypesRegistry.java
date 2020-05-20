package roito.afterthedrizzle.common.inventory;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.registry.RegistryModule;

public final class ContainerTypesRegistry extends RegistryModule
{
    public final static ContainerType<StoveContainer> STOVE_CONTAINER = (ContainerType<StoveContainer>) IForgeContainerType.create(((windowId, inv, data) -> new StoveContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("stove");
    public final static ContainerType<BambooTrayContainer> BAMBOO_TRAY_CONTAINER = (ContainerType<BambooTrayContainer>) IForgeContainerType.create(((windowId, inv, data) -> new BambooTrayContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("bamboo_tray");
    public final static ContainerType<DrinkMakerContainer> DRINK_MAKER_CONTAINER = (ContainerType<DrinkMakerContainer>) IForgeContainerType.create(((windowId, inv, data) -> new DrinkMakerContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("drink_maker");
}
