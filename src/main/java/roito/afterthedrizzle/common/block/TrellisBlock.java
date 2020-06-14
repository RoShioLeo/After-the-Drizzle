package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

import java.util.Map;

public class TrellisBlock extends NormalBlock implements IWaterLoggable
{
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty POST = BooleanProperty.create("post");
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((direction) -> direction.getKey().getAxis().isHorizontal()).collect(Util.toMapCollector());

    private static final VoxelShape[] SHAPES;

    public TrellisBlock(String name, Properties properties)
    {
        super(name, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(POST, false).with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        return getProperState(world, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        int bar = (state.get(NORTH) || state.get(SOUTH) || state.get(WEST) || state.get(EAST)) ? 4 : 0;
        int post = state.get(POST) ? 2 : 0;
        int up = state.get(UP) ? 1 : 0;
        return SHAPES[bar + post + up];
    }

    @SuppressWarnings("deprecation")
    public BlockState getProperState(World world, BlockPos pos)
    {
        BlockState state = this.getDefaultState();
        BlockState down = world.getBlockState(pos.down());
        if (down.isIn(BlockTags.WOODEN_FENCES) || down.isSolidSide(world, pos.down(), Direction.UP))
        {
            state = state.with(POST, true);
        }
        BlockState up = world.getBlockState(pos.up());
        if (up.isIn(BlockTags.WOODEN_FENCES) || up.isSolidSide(world, pos.up(), Direction.DOWN))
        {
            state = state.with(UP, true);
        }

        for (Direction facing : Direction.Plane.HORIZONTAL)
        {
            BlockPos facingPos = pos.offset(facing);
            BlockState facingState = world.getBlockState(facingPos);
            if (this.canConnect(facingState, facingState.isSolidSide(world, facingPos, facing.getOpposite()), facing.getOpposite()))
            {
                state = state.with(FACING_TO_PROPERTY_MAP.get(facing), true);
            }
        }
        return state.with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    public boolean canConnect(BlockState state, boolean isSolidSide, Direction direction)
    {
        Block block = state.getBlock();
        return !cannotAttach(block) && isSolidSide || block instanceof TrellisBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL)
        {
            return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite()), facing.getOpposite()));
        }
        else if (facing == Direction.DOWN)
        {
            BlockPos posDown = currentPos.offset(facing);
            BlockState state = worldIn.getBlockState(posDown);
            return stateIn.with(POST, state.isIn(BlockTags.WOODEN_FENCES) || state.isSolidSide(worldIn, posDown, Direction.UP));
        }
        else if (facing == Direction.UP)
        {
            BlockPos posUp = currentPos.offset(facing);
            BlockState state = worldIn.getBlockState(posUp);
            return stateIn.with(UP, state.isIn(BlockTags.WOODEN_FENCES) || state.isSolidSide(worldIn, posUp, Direction.DOWN));
        }
        return stateIn;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return !state.get(WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(POST, UP, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:
                return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90:
                return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90:
                return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default:
                return state;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK:
                return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default:
                return super.mirror(state, mirrorIn);
        }
    }

    static
    {
        VoxelShape TOP_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 7.0D, 0.0D, 16.0D, 3.0D, 16.0D);
        VoxelShape POST_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 0.0D, 6.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_UP_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 7.0D, 6.0D, 4.0D, 9.0D, 4.0D);
        SHAPES = new VoxelShape[]{TOP_SHAPE, POST_UP_SHAPE, POST_SHAPE, VoxelShapes.or(POST_UP_SHAPE, POST_SHAPE),
                TOP_SHAPE, VoxelShapes.or(TOP_SHAPE, POST_UP_SHAPE), VoxelShapes.or(TOP_SHAPE, POST_SHAPE), VoxelShapes.or(TOP_SHAPE, POST_UP_SHAPE, POST_SHAPE)};
    }
}
