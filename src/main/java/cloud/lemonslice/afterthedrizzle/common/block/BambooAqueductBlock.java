package cloud.lemonslice.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public class BambooAqueductBlock extends NormalBlock
{
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final DirectionProperty FLOWING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, 5);

    public BambooAqueductBlock()
    {
        super("bamboo_aqueduct", Block.Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.6F).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.X).with(FLOWING, Direction.NORTH).with(BOTTOM, false).with(SHAPE, Shape.POINT));
    }

    public enum Shape implements IStringSerializable
    {
        STRAIGHT,
        POINT,
        END;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
