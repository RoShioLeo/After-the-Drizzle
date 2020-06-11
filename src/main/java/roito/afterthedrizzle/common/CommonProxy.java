package roito.afterthedrizzle.common;

import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;

import static net.minecraft.block.ComposterBlock.CHANCES;

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

    public static void registerCompostable()
    {
        CHANCES.put(ItemsRegistry.TEA_SEEDS, 0.3F);
        CHANCES.put(ItemsRegistry.TEA_LEAVES, 0.15F);
        CHANCES.put(ItemsRegistry.GREEN_TEA_LEAVES, 0.3F);
        CHANCES.put(ItemsRegistry.BLACK_TEA_LEAVES, 0.4F);
        CHANCES.put(ItemsRegistry.TEA_RESIDUES, 0.5F);
        CHANCES.put(BlocksRegistry.CHRYSANTHEMUM_ITEM, 0.3F);
        CHANCES.put(BlocksRegistry.ZINNIA_ITEM, 0.3F);
        CHANCES.put(BlocksRegistry.HYACINTH_ITEM, 0.3F);
        CHANCES.put(Items.POISONOUS_POTATO, 0.3F);
    }

    public static void registerFireInfo()
    {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(BlocksRegistry.WOODEN_FRAME, 5, 20);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_CHAIR, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_DOOR, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_GLASS_DOOR, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_LANTERN, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_TABLE, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_TRAY, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_CATAPULT_BOARD, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.BAMBOO_LATTICE, 60, 60);
        fireblock.setFireInfo(BlocksRegistry.HYACINTH, 60, 100);
        fireblock.setFireInfo(BlocksRegistry.CHRYSANTHEMUM, 60, 100);
        fireblock.setFireInfo(BlocksRegistry.ZINNIA, 60, 100);
    }
}
