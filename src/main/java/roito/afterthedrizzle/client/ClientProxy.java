package roito.afterthedrizzle.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.CommonProxy;
import roito.afterthedrizzle.common.block.BlocksRegistry;

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

    public static void registerRenderType()
    {
        registerCutoutType(BlocksRegistry.CHRYSANTHEMUM);
        registerCutoutType(BlocksRegistry.ZINNIA);
        registerCutoutType(BlocksRegistry.HYACINTH);
        registerCutoutType(BlocksRegistry.TEA_PLANT);
        registerCutoutType(BlocksRegistry.WILD_TEA_PLANT);
        registerCutoutType(BlocksRegistry.GRASS_BLOCK_WITH_HOLE);
        registerCutoutType(BlocksRegistry.BAMBOO_GLASS_DOOR);
    }

    private static void registerCutoutType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
    }
}
