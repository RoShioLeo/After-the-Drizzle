package roito.afterthedrizzle.client.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import roito.afterthedrizzle.registry.RegistryModule;

public final class SoundEventsRegistry extends RegistryModule
{
    public static final SoundEvent CUP_BROKEN = new NormalSoundEvent(new ResourceLocation("afterthedrizzle:block.cup_broken"));
}
