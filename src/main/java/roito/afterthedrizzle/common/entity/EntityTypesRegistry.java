package roito.afterthedrizzle.common.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import roito.afterthedrizzle.registry.RegistryModule;

public final class EntityTypesRegistry extends RegistryModule
{
    public static final EntityType<SeatEntity> SEAT_ENTITY = (EntityType<SeatEntity>) EntityType.Builder.create((type, world) -> new SeatEntity(world), EntityClassification.MISC).size(0F, 0F).build("seat").setRegistryName("seat");
}