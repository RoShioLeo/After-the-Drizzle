package roito.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import roito.afterthedrizzle.common.block.*;

public final class BlocksRegistry extends RegistryModule
{
    public static final Block FLAT_BASKET = new BlockFlatBasket();

    public static final Block STOVE_DIRT = new BlockStoveDirt("stove_dirt", false);
    public static final Block LIT_STOVE_DIRT = new BlockStoveDirt("lit_stove_dirt", true);

    public static final Block STOVE_STONE = new BlockStoveStone("stove_stone", false);
    public static final Block LIT_STOVE_STONE = new BlockStoveStone("lit_stove_stone", true);

    public static final Block STONE_MILL = new BlockStoneMill();
    public static final Block STONE_MILL_TOP = new BlockNormal("stone_mill_top", Material.ROCK);

    public static final Block WOODEN_FRAME = new BlockWoodenFrame();
    public static final Block WOODEN_BARREL = new BlockWoodenBarrel();

    public static final Block DRINK_MAKER = new BlockDrinkMaker();


    public BlocksRegistry()
    {
        super(true);
    }
}
