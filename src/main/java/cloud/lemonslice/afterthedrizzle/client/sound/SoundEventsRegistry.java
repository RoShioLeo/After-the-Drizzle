package cloud.lemonslice.afterthedrizzle.client.sound;

import cloud.lemonslice.afterthedrizzle.registry.RegistryModule;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class SoundEventsRegistry extends RegistryModule
{
    public static final SoundEvent CUP_BROKEN = new NormalSoundEvent(new ResourceLocation("afterthedrizzle:block.cup_broken"));
}
