package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

public class BambooChairBlock extends NormalHorizontalBlock{
    static VoxelShape north_seat = VoxelShapeHelper.createVoxelShape(1D,0D,2D,14D,8D,14D);
    static VoxelShape north_back = VoxelShapeHelper.createVoxelShape(1D,8D,15D,14D,13D,1D);
    static VoxelShape south_seat = VoxelShapeHelper.createVoxelShape(1D,0D,0D,14D,8D,14D);
    static VoxelShape south_back = VoxelShapeHelper.createVoxelShape(1D,8D,0D,14D,13D,1D);
    static VoxelShape east_seat = VoxelShapeHelper.createVoxelShape(0D,0D,1D,14D,8D,14D);
    static VoxelShape east_back = VoxelShapeHelper.createVoxelShape(0D,8D,1D,1D,13D,14D);
    static VoxelShape west_seat = VoxelShapeHelper.createVoxelShape(2D,0D,1D,14D,8D,14D);
    static VoxelShape west_back = VoxelShapeHelper.createVoxelShape(15D,8D,1D,1D,13D,14D);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.combine(north_seat,north_back, IBooleanFunction.OR);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.combine(east_seat,east_back, IBooleanFunction.OR);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.combine(west_seat,west_back, IBooleanFunction.OR);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.combine(south_seat,south_back, IBooleanFunction.OR);
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(HORIZONTAL_FACING)){
            case NORTH: return this.NORTH_SHAPE;
            case EAST: return this.EAST_SHAPE;
            case WEST: return this.WEST_SHAPE;
            case SOUTH: return this.SOUTH_SHAPE;
        }
        return this.NORTH_SHAPE;
    }

    protected BambooChairBlock() {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.5F),"bamboo_chair");
        this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }
    public static Item.Properties getItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
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

}
