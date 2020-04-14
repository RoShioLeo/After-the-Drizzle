package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

public class WoodenFrameBlock extends NormalBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public WoodenFrameBlock()
    {
        super("wooden_frame", Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.5F).doesNotBlockMovement());
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape = SHAPE;
        if (worldIn.getBlockState(pos.north()).getBlock() == this)
        {
            shape = VoxelShapes.or(shape, VoxelShapeHelper.createVoxelShape(5.0D, 0.0D, 0.0D, 6.0D, 16.0D, 5.0D));
        }
        if (worldIn.getBlockState(pos.south()).getBlock() == this)
        {
            shape = VoxelShapes.or(shape, VoxelShapeHelper.createVoxelShape(5.0D, 0.0D, 11.0D, 6.0D, 16.0D, 5.0D));
        }
        if (worldIn.getBlockState(pos.west()).getBlock() == this)
        {
            shape = VoxelShapes.or(shape, VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 5.0D, 5.0D, 16.0D, 6.0D));
        }
        if (worldIn.getBlockState(pos.east()).getBlock() == this)
        {
            shape = VoxelShapes.or(shape, VoxelShapeHelper.createVoxelShape(11.0D, 0.0D, 5.0D, 5.0D, 16.0D, 6.0D));
        }
        return shape;
    }

    @Override
    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 0.5F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity)
    {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER));
    }

    @Override
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
    }
}
