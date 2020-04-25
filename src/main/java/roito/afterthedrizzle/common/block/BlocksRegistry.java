package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.fluid.NormalFlowingFluidBlock;
import roito.afterthedrizzle.common.item.DrinkMakerItem;
import roito.afterthedrizzle.common.item.HybridizableFlowerBlockItem;
import roito.afterthedrizzle.common.item.NormalBlockItem;
import roito.afterthedrizzle.registry.RegistryModule;

import java.util.Random;

import static roito.afterthedrizzle.common.fluid.FluidsRegistry.WARM_WATER;

public final class BlocksRegistry extends RegistryModule
{
    // CRAFT 工艺
    public static final Block BAMBOO_TRAY = new BambooTrayBlock();
    public static final Block DRINK_MAKER = new DrinkMakerBlock();
    public static final Block DIRT_STOVE = new StoveBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), "dirt_stove", 1);
    public static final Block STONE_STOVE = new StoveBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), "stone_stove", 2);
    public static final Block WOODEN_FRAME = new WoodenFrameBlock();
    public static final Block TEAPOT = new TeapotBlock();

    public static final BlockItem BAMBOO_TRAY_ITEM = new NormalBlockItem(BAMBOO_TRAY);
    public static final BlockItem DRINK_MAKER_ITEM = new DrinkMakerItem();
    public static final BlockItem DIRT_STOVE_ITEM = new NormalBlockItem(DIRT_STOVE);
    public static final BlockItem STONE_STOVE_ITEM = new NormalBlockItem(STONE_STOVE);
    public static final BlockItem WOODEN_FRAME_ITEM = new NormalBlockItem(WOODEN_FRAME);

    // DECORATIONS 装饰
    public static final Block BAMBOO_TABLE = new BambooTableBlock();
    public static final Block BAMBOO_CHAIR = new BambooChairBlock();
    public static final Block BAMBOO_LANTERN = new LanternBlock(Block.Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F).lightValue(15)).setRegistryName("bamboo_lantern");
    public static final Block BAMBOO_DOOR = new BambooDoorBlock("bamboo_door");
    public static final Block BAMBOO_GLASS_DOOR = new BambooDoorBlock("bamboo_glass_door");
//    public static final Block BAMBOO_GLASS_WINDOW = new BambooGlassWindow();

    public static final BlockItem BAMBOO_TABLE_ITEM = new NormalBlockItem(BAMBOO_TABLE);
    public static final BlockItem BAMBOO_CHAIR_ITEM = new NormalBlockItem(BAMBOO_CHAIR);
    public static final BlockItem BAMBOO_LANTERN_ITEM = new NormalBlockItem(BAMBOO_LANTERN);
    public static final BlockItem BAMBOO_DOOR_ITEM = new NormalBlockItem(BAMBOO_DOOR);
    public static final BlockItem BAMBOO_GLASS_DOOR_ITEM = new NormalBlockItem(BAMBOO_GLASS_DOOR);
//    public static final BlockItem BAMBOO_GLASS_WINDOW_ITEM = new NormalBlockItem(BAMBOO_GLASS_WINDOW);

    // CROPS 作物
    public static final Block TEA_PLANT = new TeaPlantBlock();
    public static final Block WILD_TEA_PLANT = new WildTeaPlantBlock();

    public static final BlockItem WILD_TEA_PLANT_ITEM = new NormalBlockItem(WILD_TEA_PLANT);

    // FLOWERS 花朵
    public static final Block CHRYSANTHEMUM = new HybridizableFlowerBlock("chrysanthemum");
    public static final Block HYACINTH = new HybridizableFlowerBlock("hyacinth");
    public static final Block ZINNIA = new HybridizableFlowerBlock("zinnia");

    public static final BlockItem CHRYSANTHEMUM_ITEM = new HybridizableFlowerBlockItem(CHRYSANTHEMUM);
    public static final BlockItem HYACINTH_ITEM = new HybridizableFlowerBlockItem(HYACINTH);
    public static final BlockItem ZINNIA_ITEM = new HybridizableFlowerBlockItem(ZINNIA);

    // MISC 杂项
    public static final FlowingFluidBlock WATER = (FlowingFluidBlock) new FlowingFluidBlock(() -> Fluids.WATER, NormalFlowingFluidBlock.getProperties().tickRandomly())
    {
        @Override
        public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random)
        {
            super.randomTick(state, worldIn, pos, random);
            if (random.nextInt(4) == 0 && worldIn.getBlockState(pos.down(2)).getBlock() instanceof CampfireBlock)
            {
                if (state.getFluidState().getLevel() == 8)
                {
                    worldIn.setBlockState(pos, WARM_WATER.get().getDefaultState());
                }
            }
        }
    }.setRegistryName("minecraft:water");
}
