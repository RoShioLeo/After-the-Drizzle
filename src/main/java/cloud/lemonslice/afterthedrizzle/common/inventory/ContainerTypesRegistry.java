package cloud.lemonslice.afterthedrizzle.common.inventory;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.client.gui.BambooTrayGuiContainer;
import cloud.lemonslice.afterthedrizzle.client.gui.DrinkMakerGuiContainer;
import cloud.lemonslice.afterthedrizzle.client.gui.StoveGuiContainer;
import cloud.lemonslice.afterthedrizzle.registry.RegistryModule;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public final class ContainerTypesRegistry extends RegistryModule
{
    public final static ContainerType<?> STOVE_CONTAINER = IForgeContainerType.create(((windowId, inv, data) -> new StoveContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("stove");
    public final static ContainerType<?> BAMBOO_TRAY_CONTAINER = IForgeContainerType.create(((windowId, inv, data) -> new BambooTrayContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("bamboo_tray");
    public final static ContainerType<?> DRINK_MAKER_CONTAINER = IForgeContainerType.create(((windowId, inv, data) -> new DrinkMakerContainer(windowId, inv, data.readBlockPos(), AfterTheDrizzle.proxy.getClientWorld()))).setRegistryName("drink_maker");

    public static void clientInit()
    {
        ScreenManager.registerFactory((ContainerType<StoveContainer>) STOVE_CONTAINER, StoveGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<BambooTrayContainer>) BAMBOO_TRAY_CONTAINER, BambooTrayGuiContainer::new);
        ScreenManager.registerFactory((ContainerType<DrinkMakerContainer>) DRINK_MAKER_CONTAINER, DrinkMakerGuiContainer::new);
    }
}
