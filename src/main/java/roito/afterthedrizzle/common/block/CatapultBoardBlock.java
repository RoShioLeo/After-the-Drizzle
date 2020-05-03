package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

import java.util.Random;

public class CatapultBoardBlock extends NormalHorizontalBlock
{
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 2, 16);
    private static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    private final float motion;

    public CatapultBoardBlock(float motion, String name, Properties properties)
    {
        super(properties, name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(ENABLED, false));
        this.motion = motion;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return hasSolidSideOnTop(worldIn, pos.down());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, ENABLED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
    {
        worldIn.setBlockState(pos, state.with(ENABLED, false), 2);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (player.getHeldItem(handIn).getItem() == BlocksRegistry.BAMBOO_TRAY.asItem())
        {
            worldIn.setBlockState(pos, BlocksRegistry.STONE_CATAPULT_BOARD_WITH_TRAY.getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)));
            SoundType soundtype = BlocksRegistry.BAMBOO_TRAY.getDefaultState().getSoundType(worldIn, pos, player);
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            return true;
        }
        else return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (worldIn.isBlockPowered(pos))
        {
            worldIn.setBlockState(pos, state.with(ENABLED, true), 2);
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 5);
            Vec3d vec3d;
            switch (state.get(HORIZONTAL_FACING))
            {
                case SOUTH:
                {
                    vec3d = new Vec3d(0, motion, -motion);
                    break;
                }
                case EAST:
                {
                    vec3d = new Vec3d(-motion, motion, 0);
                    break;
                }
                case WEST:
                {
                    vec3d = new Vec3d(motion, motion, 0);
                    break;
                }
                default:
                    vec3d = new Vec3d(0, motion, motion);
            }
            entityIn.fallDistance = 0.0F;
            entityIn.setMotion(vec3d);
        }
    }

}
