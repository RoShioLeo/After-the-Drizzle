package cloud.lemonslice.afterthedrizzle.common.entity;

import cloud.lemonslice.afterthedrizzle.registry.RegistryModule;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public final class EntityTypesRegistry extends RegistryModule
{
    public static final EntityType<SeatEntity> SEAT_ENTITY = (EntityType<SeatEntity>) EntityType.Builder.create((type, world) -> new SeatEntity(world), EntityClassification.MISC).size(0F, 0F).build("seat").setRegistryName("seat");
}