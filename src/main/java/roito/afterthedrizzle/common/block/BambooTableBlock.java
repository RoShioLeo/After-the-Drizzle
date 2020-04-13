package roito.afterthedrizzle.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.helper.VoxelShapeHelper;


public class BambooTableBlock extends NormalBlock{
    static VoxelShape one = VoxelShapeHelper.createVoxelShape(1,0,1,2,14,2);
    static VoxelShape two = VoxelShapeHelper.createVoxelShape(13,0,1,2,14,2);
    static VoxelShape three = VoxelShapeHelper.createVoxelShape(1,0,13,2,14,2);
    static VoxelShape four = VoxelShapeHelper.createVoxelShape(13,0,13,2,14,2);
    static VoxelShape table = VoxelShapeHelper.createVoxelShape(0,14,0,16,2,16);
    public static VoxelShape SHAPE_1 = VoxelShapes.combine(one,two, IBooleanFunction.OR);
    public static VoxelShape SHAPE_2 = VoxelShapes.combine(SHAPE_1,three, IBooleanFunction.OR);
    public static VoxelShape SHAPE_3 = VoxelShapes.combine(SHAPE_2,four, IBooleanFunction.OR);
    public static VoxelShape SHAPE = VoxelShapes.combine(SHAPE_3,table, IBooleanFunction.OR);
    public BambooTableBlock() {
        super("bamboo_table", Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.5F));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.SHAPE;
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
    public static Item.Properties getItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT);
    }
    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
}
