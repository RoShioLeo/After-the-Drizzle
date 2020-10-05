package cloud.lemonslice.afterthedrizzle.common.block;

import cloud.lemonslice.silveroak.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class TrellisBlock extends HorizontalConnectedBlock implements IWaterLoggable
{
    public static final BooleanProperty POST = BooleanProperty.create("post");
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty HORIZONTAL = BooleanProperty.create("horizontal");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape[] SHAPES;

    public TrellisBlock(String name, Properties properties)
    {
        super(name, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL, false).with(POST, false).with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
        BlocksRegistry.TRELLIS_BLOCKS.add(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        return getProperState(world, pos);
    }

    public boolean hasHorizontalBar(BlockState state)
    {
        return state.get(NORTH) || state.get(SOUTH) || state.get(WEST) || state.get(EAST);
    }

    public boolean hasPost(BlockState state)
    {
        return state.get(POST);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        int north = state.get(NORTH) ? 32 : 0;
        int south = state.get(SOUTH) ? 16 : 0;
        int east = state.get(WEST) ? 8 : 0;
        int west = state.get(EAST) ? 4 : 0;
        int up = state.get(UP) ? 2 : 0;
        int post = hasPost(state) ? 1 : 0;
        return SHAPES[north + south + east + west + post + up];
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
            if (this.canConnect(facingState, facingState.isSolidSide(world, facingPos, facing.getOpposite())))
            {
                state = state.with(FACING_TO_PROPERTY_MAP.get(facing), true);
            }
        }
        state = state.with(HORIZONTAL, hasHorizontalBar(state));
        return state.with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    public boolean canConnect(BlockState state, boolean isSolidSide)
    {
        Block block = state.getBlock();
        return !cannotAttach(block) && isSolidSide || block instanceof TrellisBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        // Update the connecting state of trellis. 更新棚架方块的连接状态。
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL)
        {
            stateIn = stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite())));
            stateIn = stateIn.with(HORIZONTAL, hasHorizontalBar(stateIn));
        }
        else if (facing == Direction.DOWN)
        {
            BlockPos posDown = currentPos.offset(facing);
            BlockState state = worldIn.getBlockState(posDown);
            stateIn = stateIn.with(POST, state.isIn(BlockTags.WOODEN_FENCES) || state.isSolidSide(worldIn, posDown, Direction.UP));
        }
        else if (facing == Direction.UP)
        {
            BlockPos posUp = currentPos.offset(facing);
            BlockState state = worldIn.getBlockState(posUp);
            stateIn = stateIn.with(UP, state.isIn(BlockTags.WOODEN_FENCES) || state.isSolidSide(worldIn, posUp, Direction.DOWN));
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
        super.fillStateContainer(builder);
        builder.add(HORIZONTAL, POST, UP, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> list = Lists.newArrayList();
        list.add(new ItemStack(this));
        return list;
    }

    public BlockState getRelevantState(BlockState old)
    {
        BlockState newState = this.getDefaultState();
        return newState.with(NORTH, old.get(NORTH)).with(SOUTH, old.get(SOUTH)).with(WEST, old.get(WEST)).with(EAST, old.get(EAST)).with(POST, old.get(POST)).with(UP, old.get(UP)).with(HORIZONTAL, old.get(HORIZONTAL)).with(WATERLOGGED, old.get(WATERLOGGED));
    }

    static
    {
        VoxelShape CENTER = VoxelShapeHelper.createVoxelShape(7.0D, 7.0D, 7.0D, 2.0D, 3.0D, 2.0D);
        VoxelShape TOP_NORTH = VoxelShapeHelper.createVoxelShape(7.0D, 7.0D, 0.0D, 2.0D, 3.0D, 14.0D);
        VoxelShape TOP_SOUTH = VoxelShapeHelper.createVoxelShape(7.0D, 7.0D, 2.0D, 2.0D, 3.0D, 14.0D);
        VoxelShape TOP_EAST = VoxelShapeHelper.createVoxelShape(0.0D, 7.0D, 7.0D, 14.0D, 3.0D, 2.0D);
        VoxelShape TOP_WEST = VoxelShapeHelper.createVoxelShape(2.0D, 7.0D, 7.0D, 14.0D, 3.0D, 2.0D);
        VoxelShape POST_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 0.0D, 6.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_UP_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 7.0D, 6.0D, 4.0D, 9.0D, 4.0D);
        SHAPES = new VoxelShape[]{CENTER, POST_SHAPE, POST_UP_SHAPE, VoxelShapes.or(POST_UP_SHAPE, POST_SHAPE),
                TOP_WEST, VoxelShapes.or(TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                TOP_EAST, VoxelShapes.or(TOP_EAST, POST_SHAPE), VoxelShapes.or(TOP_EAST, POST_UP_SHAPE), VoxelShapes.or(TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_EAST, TOP_WEST), VoxelShapes.or(TOP_EAST, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_EAST, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                TOP_SOUTH, VoxelShapes.or(TOP_SOUTH, POST_SHAPE), VoxelShapes.or(TOP_SOUTH, POST_UP_SHAPE), VoxelShapes.or(TOP_SOUTH, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_SOUTH, TOP_WEST), VoxelShapes.or(TOP_SOUTH, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_SOUTH, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_SOUTH, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_SOUTH, TOP_EAST), VoxelShapes.or(TOP_SOUTH, TOP_EAST, POST_SHAPE), VoxelShapes.or(TOP_SOUTH, TOP_EAST, POST_UP_SHAPE), VoxelShapes.or(TOP_SOUTH, TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST), VoxelShapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                TOP_NORTH, VoxelShapes.or(TOP_NORTH, POST_SHAPE), VoxelShapes.or(TOP_NORTH, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_WEST), VoxelShapes.or(TOP_NORTH, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_EAST), VoxelShapes.or(TOP_NORTH, TOP_EAST, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_EAST, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_EAST, TOP_WEST), VoxelShapes.or(TOP_NORTH, TOP_EAST, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_SOUTH), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST, POST_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE), VoxelShapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
        };
    }
}
