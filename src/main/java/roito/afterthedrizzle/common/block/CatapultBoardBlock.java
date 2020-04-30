package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

import java.util.Random;

public class CatapultBoardBlock extends NormalHorizontalBlock
{
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 2, 16);
    private static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public CatapultBoardBlock()
    {
        super(Block.Properties.create(Material.BAMBOO), "catapult_board");
        this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(ENABLED, false));
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, ENABLED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
    {
        if (state.get(ENABLED))
        {
            worldIn.setBlockState(pos, state.cycle(ENABLED), 2);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (!state.get(ENABLED) && worldIn.isBlockPowered(pos))
        {
            worldIn.setBlockState(pos, state.cycle(ENABLED), 2);
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 1);
            Vec3d vec3d;
            switch (state.get(HORIZONTAL_FACING))
            {
                case SOUTH:
                {
                    vec3d = new Vec3d(0, 0.4, -0.4);
                    break;
                }
                case EAST:
                {
                    vec3d = new Vec3d(-0.4, 0.4, 0);
                    break;
                }
                case WEST:
                {
                    vec3d = new Vec3d(0.4, 0.4, 0);
                    break;
                }
                default:
                    vec3d = new Vec3d(0, 0.4, 0.4);
            }
            entityIn.setMotion(vec3d);
        }
    }

}
