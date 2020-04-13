package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.DrinkMakerItem;
import roito.afterthedrizzle.common.item.NormalBlockItem;
import roito.afterthedrizzle.registry.RegistryModule;

import java.util.Random;

public final class BlocksRegistry extends RegistryModule
{
    // CRAFT 工艺
    public static final Block BAMBOO_TRAY = new BambooTrayBlock();
    public static final BlockItem BAMBOO_TRAY_ITEM = new NormalBlockItem(BAMBOO_TRAY, BambooTrayBlock.getItemProperties());

    public static final Block DIRT_STOVE = new StoveBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), "dirt_stove", 2);
    public static final BlockItem DIRT_STOVE_ITEM = new NormalBlockItem(DIRT_STOVE, StoveBlock.getItemProperties());

    public static final Block STONE_STOVE = new StoveBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), "stone_stove", 2);
    public static final BlockItem STONE_STOVE_ITEM = new NormalBlockItem(STONE_STOVE, StoveBlock.getItemProperties());

    public static final Block WOODEN_FRAME = new WoodenFrameBlock();
    public static final BlockItem WOODEN_FRAME_ITEM = new NormalBlockItem(WOODEN_FRAME, WoodenFrameBlock.getItemProperties());

    public static final Block DRINK_MAKER = new DrinkMakerBlock();
    public static final BlockItem DRINK_MAKER_ITEM = new DrinkMakerItem();

    // Crops 作物
    public static final Block TEA_PLANT = new TeaPlantBlock();
    public static final Block WILD_TEA_PLANT = new WildTeaPlantBlock();

    // FLUID BLOCK 流体方块
    public static final FlowingFluidBlock BOILING_WATER = new HotWaterFlowingFluidBlock("boiling_water", FluidsRegistry.BOILING_WATER_STILL);
    public static final FlowingFluidBlock HOT_WATER_80 = new HotWaterFlowingFluidBlock("hot_water_80", FluidsRegistry.HOT_WATER_80_STILL);
    public static final FlowingFluidBlock HOT_WATER_60 = new HotWaterFlowingFluidBlock("hot_water_60", FluidsRegistry.HOT_WATER_60_STILL);
    public static final FlowingFluidBlock WARM_WATER = new HotWaterFlowingFluidBlock("warm_water", FluidsRegistry.WARM_WATER_STILL);
    public static final FlowingFluidBlock SUGARY_WATER = new NormalFlowingFluidBlock("sugary_water", FluidsRegistry.SUGARY_WATER_STILL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());

    public static final FlowingFluidBlock WATER = (FlowingFluidBlock) new FlowingFluidBlock(() -> Fluids.WATER, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops().tickRandomly())
    {
        @Override
        public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random)
        {
            super.randomTick(state, worldIn, pos, random);
            if (random.nextInt(4) == 0 && worldIn.getBlockState(pos.down(2)).getBlock() instanceof CampfireBlock)
            {
                if (state.getFluidState().getLevel() == 8)
                {
                    worldIn.setBlockState(pos, WARM_WATER.getDefaultState());
                }
            }
        }
    }.setRegistryName("minecraft:water");
}
