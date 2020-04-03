package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import roito.afterthedrizzle.registry.RegistryModule;

public final class BlocksRegistry extends RegistryModule
{
    public static final Block BAMBOO_TRAY = new BambooTrayBlock();
    public static final BlockItem BAMBOO_TRAY_ITEM = new ItemBlock(BAMBOO_TRAY, BambooTrayBlock.getItemProperties());

    public static final Block DIRT_STOVE = new DirtStoveBlock("dirt_stove", 0);
    public static final BlockItem DIRT_STOVE_ITEM = new ItemBlock(DIRT_STOVE, StoveBlock.getItemProperties());
    public static final Block LIT_DIRT_STOVE = new DirtStoveBlock("lit_dirt_stove", 14);

    public static final Block STONE_STOVE = new StoneStoveBlock("stone_stove", 0);
    public static final BlockItem STONE_STOVE_ITEM = new ItemBlock(STONE_STOVE, StoveBlock.getItemProperties());
    public static final Block LIT_STONE_STOVE = new StoneStoveBlock("lit_stone_stove", 14);

    public static final Block WOODEN_FRAME = new WoodenFrameBlock();
    public static final BlockItem WOODEN_FRAME_ITEM = new ItemBlock(WOODEN_FRAME, WoodenFrameBlock.getItemProperties());
}
