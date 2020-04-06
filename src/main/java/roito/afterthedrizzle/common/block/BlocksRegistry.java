package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.DrinkMakerItem;
import roito.afterthedrizzle.registry.RegistryModule;

import java.util.Random;

public final class BlocksRegistry extends RegistryModule
{
    public static final Block BAMBOO_TRAY = new BambooTrayBlock();
    public static final BlockItem BAMBOO_TRAY_ITEM = new ItemBlock(BAMBOO_TRAY, BambooTrayBlock.getItemProperties());

    public static final Block DIRT_STOVE = new DirtStoveBlock("dirt_stove");
    public static final BlockItem DIRT_STOVE_ITEM = new ItemBlock(DIRT_STOVE, StoveBlock.getItemProperties());

    public static final Block STONE_STOVE = new StoneStoveBlock("stone_stove");
    public static final BlockItem STONE_STOVE_ITEM = new ItemBlock(STONE_STOVE, StoveBlock.getItemProperties());

    public static final Block WOODEN_FRAME = new WoodenFrameBlock();
    public static final BlockItem WOODEN_FRAME_ITEM = new ItemBlock(WOODEN_FRAME, WoodenFrameBlock.getItemProperties());

    public static final Block DRINK_MAKER = new DrinkMakerBlock();
    public static final BlockItem DRINK_MAKER_ITEM = new DrinkMakerItem();

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
