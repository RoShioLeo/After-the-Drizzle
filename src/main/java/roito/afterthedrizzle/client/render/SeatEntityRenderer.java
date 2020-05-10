package roito.afterthedrizzle.client.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.common.entity.SeatEntity;

public class SeatEntityRenderer extends EntityRenderer<SeatEntity>
{
    public SeatEntityRenderer(EntityRendererManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(SeatEntity entity)
    {
        return null;
    }
}
