package roito.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.*;
import roito.silveroakoutpost.register.RegisterType;
import roito.silveroakoutpost.register.annotation.AutoRegistry;
import roito.silveroakoutpost.register.annotation.RegisterInfo;

@AutoRegistry(modid = AfterTheDrizzle.MODID, registerType = RegisterType.BLOCK)
public final class BlocksRegistry
{
    @RegisterInfo("flat_basket")
    public static final Block FLAT_BASKET = new BlockFlatBasket();

    @RegisterInfo("stove_dirt")
    public static final Block STOVE_DIRT = new BlockStoveDirt(false);
    @RegisterInfo("lit_stove_dirt")
    public static final Block LIT_STOVE_DIRT = new BlockStoveDirt(true);

    @RegisterInfo("stove_stone")
    public static final Block STOVE_STONE = new BlockStoveStone(false);
    @RegisterInfo("lit_stove_stone")
    public static final Block LIT_STOVE_STONE = new BlockStoveStone(true);

    @RegisterInfo("stone_mill")
    public static final Block STONE_MILL = new BlockStoneMill();
    @RegisterInfo("stone_mill_top")
    public static final Block STONE_MILL_TOP = new Block(Material.ROCK);

    @RegisterInfo("wooden_frame")
    public static final Block WOODEN_FRAME = new BlockWoodenFrame();
    @RegisterInfo("wooden_barrel")
    public static final Block WOODEN_BARREL = new BlockWoodenBarrel();
}
