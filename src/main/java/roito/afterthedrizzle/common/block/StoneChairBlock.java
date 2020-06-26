package roito.afterthedrizzle.common.block;

import roito.afterthedrizzle.helper.VoxelShapeHelper;

public class StoneChairBlock extends ChairBlock{
    public StoneChairBlock(String name, Properties properties) {
        super(name, properties);
    }

    static {
        NORTH_SHAPE = VoxelShapeHelper.createVoxelShape(0,0,5,16,8,9);
        SOUTH_SHAPE = VoxelShapeHelper.createVoxelShape(0,0,2,16,8,9);
        WEST_SHAPE = VoxelShapeHelper.createVoxelShape(9,0,0,9,8,16);
        EAST_SHAPE = VoxelShapeHelper.createVoxelShape(2,0,0,9,8,16);
    }
}
