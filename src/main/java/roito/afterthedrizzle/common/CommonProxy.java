package roito.afterthedrizzle.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CommonProxy
{
    public World getClientWorld()
    {
        throw new IllegalStateException("Only run this on the client!");
    }

    public PlayerEntity getClientPlayer()
    {
        throw new IllegalStateException("Only run this on the client!");
    }
}
