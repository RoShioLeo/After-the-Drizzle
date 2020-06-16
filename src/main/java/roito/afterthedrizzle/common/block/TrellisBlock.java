package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

import java.util.Map;
import java.util.Random;

public class TrellisBlock extends NormalBlock implements IWaterLoggable
{
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty POST = BooleanProperty.create("post");
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty HORIZONTAL = BooleanProperty.create("horizontal");
    public static final EnumProperty<VineType> VINE = EnumProperty.create("vine", VineType.class);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE_0_7;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((direction) -> direction.getKey().getAxis().isHorizontal()).collect(Util.toMapCollector());

    private static final VoxelShape[] SHAPES;

    public TrellisBlock(String name, Properties properties)
    {
        super(name, properties.tickRandomly());
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL, false).with(VINE, VineType.NONE).with(AGE, 0).with(DISTANCE, 0).with(POST, false).with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
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
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        if (state.get(VINE) != VineType.NONE)
        {
            if (hasPost(state))
            {
                int i = state.get(AGE);
                float f = 8.0F; //TODO Connected with humidity.
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
                {
                    if (i < 3)
                    {
                        worldIn.setBlockState(pos, state.with(AGE, i + 1), 2);
                    }
                    else
                    {
                        BlockState up = worldIn.getBlockState(pos.up());
                        if (up.getBlock() instanceof TrellisBlock && up.get(VINE) == VineType.NONE)
                        {
                            worldIn.setBlockState(pos.up(), up.with(VINE, state.get(VINE)).with(AGE, 0).with(DISTANCE, state.get(DISTANCE)), 2);
                        }
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                    return;
                }
            }
            if (hasHorizontalBar(state))
            {
                int i = state.get(AGE);
                float f = 5.0F; //TODO Connected with humidity.
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
                {
                    if (i < 3)
                    {
                        if (worldIn.getBlockState(pos.down()).getBlock() != state.get(VINE).getFruit())
                            worldIn.setBlockState(pos, state.with(AGE, i + 1), 2);
                    }
                    else
                    {
                        if (worldIn.getBlockState(pos.down()).isAir() && state.get(DISTANCE) % 2 == 1 && random.nextBoolean())
                        {
                            worldIn.setBlockState(pos, state.with(AGE, 0));
                            worldIn.setBlockState(pos.down(), state.get(VINE).getFruit().getDefaultState());
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                            return;
                        }
                    }
                    BlockPos blockPos = pos;
                    switch (random.nextInt(4))
                    {
                        case 0:
                            blockPos = blockPos.north();
                            break;
                        case 1:
                            blockPos = blockPos.south();
                            break;
                        case 2:
                            blockPos = blockPos.east();
                            break;
                        default:
                            blockPos = blockPos.west();
                    }
                    BlockState next = worldIn.getBlockState(blockPos);
                    if (next.getBlock() instanceof TrellisBlock && next.get(VINE) == VineType.NONE && state.get(DISTANCE) < 7)
                    {
                        worldIn.setBlockState(blockPos, next.with(VINE, state.get(VINE)).with(AGE, 0).with(DISTANCE, state.get(DISTANCE) + 1), 2);
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        int bar = hasHorizontalBar(state) ? 4 : 0;
        int post = hasPost(state) ? 2 : 0;
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

    public boolean canConnect(BlockState state, boolean isSolidSide, Direction direction)
    {
        Block block = state.getBlock();
        return !cannotAttach(block) && isSolidSide || block instanceof TrellisBlock;
    }

    public BlockState getNoneVineState(BlockState state)
    {
        return state.with(VINE, VineType.NONE).with(DISTANCE, 0).with(AGE, 0);
    }

    public int getNearDistance(IWorld world, BlockPos pos, VineType vine)
    {
        int distance = 7;
        distance = Math.min(distance, getDistance(world.getBlockState(pos.north()), vine));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.south()), vine));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.east()), vine));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.west()), vine));
        return distance;
    }

    public int getDistance(BlockState state, VineType vine)
    {
        if (state.getBlock() instanceof TrellisBlock && state.get(VINE) == vine)
        {
            return state.get(DISTANCE);
        }
        return 7;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        // Trellis
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL)
        {
            stateIn = stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite()), facing.getOpposite()));
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

        // Vines
        if (stateIn.get(VINE) != VineType.NONE)
        {
            boolean valid = false;
            if (hasHorizontalBar(stateIn))
            {
                int distance = getNearDistance(worldIn, currentPos, stateIn.get(VINE));
                if (distance < 7)
                {
                    stateIn = stateIn.with(DISTANCE, distance + 1);
                    valid = true;
                }
            }
            if (hasPost(stateIn))
            {
                BlockState down = worldIn.getBlockState(currentPos.down());
                if (down.getBlock().isIn(Tags.Blocks.DIRT))
                {
                    stateIn = stateIn.with(DISTANCE, 0);
                    valid = true;
                }
                else if (down.getBlock() instanceof TrellisBlock)
                {
                    if (down.get(AGE) == 3 && down.get(VINE) == stateIn.get(VINE))
                    {
                        stateIn = stateIn.with(DISTANCE, down.get(DISTANCE));
                        valid = true;
                    }
                }
            }
            if (!valid)
            {
                stateIn = getNoneVineState(stateIn);
            }
        }
        else stateIn = getNoneVineState(stateIn);
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
        builder.add(HORIZONTAL, POST, UP, NORTH, EAST, WEST, SOUTH, AGE, DISTANCE, VINE, WATERLOGGED);
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

    public enum VineType implements IStringSerializable
    {
        NONE,
        GRAPE;

        public Block getFruit()
        {
            if (getName().equals("grape"))
            {
                return BlocksRegistry.GRAPE;
            }
            else return Blocks.AIR;
        }

        @Override
        public String getName()
        {
            return toString().toLowerCase();
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
