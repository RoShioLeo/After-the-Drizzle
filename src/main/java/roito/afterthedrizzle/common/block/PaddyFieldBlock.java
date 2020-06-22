package roito.afterthedrizzle.common.block;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;

import java.util.List;
import java.util.Random;

public class PaddyFieldBlock extends NormalBlock implements IWaterLoggable
{
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Water> WATER = EnumProperty.create("water", Water.class);
    protected static final VoxelShape SHAPE = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);

    public PaddyFieldBlock()
    {
        super("paddy_field", Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.6F).sound(SoundType.GROUND).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(WATER, Water.SKIP));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn)
    {
        return false;
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn)
    {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state)
    {
        if (state.get(WATERLOGGED))
        {
            return Fluids.WATER.getStillFluidState(false);
        }
        return super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        updateWater(worldIn, pos);
    }

    public void updateWater(IWorld world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this)
        {
            boolean hasWater = state.get(WATERLOGGED);
            Water trend = state.get(WATER);

            if (trend != Water.SKIP)
            {
                if (trend == Water.POUR)
                {
                    if (!hasWater)
                    {
                        world.setBlockState(pos, state.with(WATERLOGGED, true).with(WATER, Water.SKIP), 3);
                        setToUpdate(world, pos.north(), trend);
                        setToUpdate(world, pos.south(), trend);
                        setToUpdate(world, pos.west(), trend);
                        setToUpdate(world, pos.east(), trend);
                    }
                }
                else
                {
                    if (hasWater)
                    {
                        world.setBlockState(pos, state.with(WATERLOGGED, false).with(WATER, Water.SKIP), 3);
                        setToUpdate(world, pos.north(), trend);
                        setToUpdate(world, pos.south(), trend);
                        setToUpdate(world, pos.west(), trend);
                        setToUpdate(world, pos.east(), trend);
                    }
                }
            }
        }
    }

    public void setToUpdate(IWorld world, BlockPos pos, Water water)
    {
        if (water == Water.SKIP) return;
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this)
        {
            if (water == Water.POUR && !state.get(WATERLOGGED))
            {
                world.setBlockState(pos, state.with(WATER, water), 3);
                world.getPendingBlockTicks().scheduleTick(pos, this, Fluids.WATER.getTickRate(world) + 1);
            }
            else if (water == Water.DRAIN && state.get(WATERLOGGED))
            {
                world.setBlockState(pos, state.with(WATER, water), 3);
                world.getPendingBlockTicks().scheduleTick(pos, this, Fluids.WATER.getTickRate(world));
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return stateIn;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED, WATER);
    }

    @Override
    public boolean isFertile(BlockState state, IBlockReader world, BlockPos pos)
    {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> list = Lists.newArrayList();
        list.add(new ItemStack(Blocks.DIRT));
        return list;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
    {
        return new ItemStack(Blocks.DIRT);
    }

    public enum Water implements IStringSerializable
    {
        SKIP,
        DRAIN,
        POUR;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
