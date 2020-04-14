package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

public class BambooChairBlock extends NormalHorizontalBlock
{
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;

    public BambooChairBlock()
    {
        super(Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F), "bamboo_chair");
        this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.get(HORIZONTAL_FACING))
        {
            case NORTH:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return SOUTH_SHAPE;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING);
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

    static
    {
        VoxelShape north_seat = VoxelShapeHelper.createVoxelShape(1D, 0D, 2D, 14D, 8D, 14D);
        VoxelShape north_back = VoxelShapeHelper.createVoxelShape(1D, 8D, 15D, 14D, 13D, 1D);
        VoxelShape south_seat = VoxelShapeHelper.createVoxelShape(1D, 0D, 0D, 14D, 8D, 14D);
        VoxelShape south_back = VoxelShapeHelper.createVoxelShape(1D, 8D, 0D, 14D, 13D, 1D);
        VoxelShape east_seat = VoxelShapeHelper.createVoxelShape(0D, 0D, 1D, 14D, 8D, 14D);
        VoxelShape east_back = VoxelShapeHelper.createVoxelShape(0D, 8D, 1D, 1D, 13D, 14D);
        VoxelShape west_seat = VoxelShapeHelper.createVoxelShape(2D, 0D, 1D, 14D, 8D, 14D);
        VoxelShape west_back = VoxelShapeHelper.createVoxelShape(15D, 8D, 1D, 1D, 13D, 14D);
        NORTH_SHAPE = VoxelShapes.or(north_seat, north_back);
        EAST_SHAPE = VoxelShapes.or(east_seat, east_back);
        WEST_SHAPE = VoxelShapes.or(west_seat, west_back);
        SOUTH_SHAPE = VoxelShapes.or(south_seat, south_back);
    }

}
