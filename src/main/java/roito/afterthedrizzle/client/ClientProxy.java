package roito.afterthedrizzle.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
