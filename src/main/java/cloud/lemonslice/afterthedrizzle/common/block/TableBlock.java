package cloud.lemonslice.afterthedrizzle.common.block;

import cloud.lemonslice.afterthedrizzle.helper.VoxelShapeHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;


public class TableBlock extends NormalBlock
{
    public static final VoxelShape SHAPE;

    public TableBlock(String name, Properties properties)
    {
        super(name, properties);
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
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }


    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    static
    {
        VoxelShape one = VoxelShapeHelper.createVoxelShape(1, 0, 1, 2, 14, 2);
        VoxelShape two = VoxelShapeHelper.createVoxelShape(13, 0, 1, 2, 14, 2);
        VoxelShape three = VoxelShapeHelper.createVoxelShape(1, 0, 13, 2, 14, 2);
        VoxelShape four = VoxelShapeHelper.createVoxelShape(13, 0, 13, 2, 14, 2);
        VoxelShape table = VoxelShapeHelper.createVoxelShape(0, 14, 0, 16, 2, 16);
        SHAPE = VoxelShapes.or(one, two, three, four, table);
    }
}
