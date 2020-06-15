package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class PaddyFieldBlock extends NormalBlock implements IWaterLoggable
{
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_1_8;
    protected static final VoxelShape SHAPE = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);

    public PaddyFieldBlock()
    {
        super("paddy_field", Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.6F).sound(SoundType.GROUND).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(LEVEL, 8));
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
            int level = state.get(LEVEL);
            if (level != 8)
            {
                return Fluids.WATER.getFlowingFluidState(state.get(LEVEL), false);
            }
            else return Fluids.WATER.getStillFluidState(false);
        }
        return super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        else if (worldIn.getBlockState(currentPos.up()).isAir())
        {
            stateIn = stateIn.with(LEVEL, 8);
            if (facingState.getBlock() == this && facingState.get(WATERLOGGED) && facingState.get(LEVEL) == 8)
            {
                stateIn = stateIn.with(WATERLOGGED, true);
            }
        }
        else if (worldIn.getFluidState(currentPos.up()).isTagged(FluidTags.WATER))
        {
            stateIn = stateIn.with(WATERLOGGED, true).with(LEVEL, 8);
        }
        return stateIn;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED, LEVEL);
    }

    @Override
    public boolean isFertile(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.get(WATERLOGGED);
    }
}
