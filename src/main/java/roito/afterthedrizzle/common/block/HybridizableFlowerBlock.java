package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;


public class HybridizableFlowerBlock extends BushBlock {
    public static final EnumProperty<FlowerColor> FLOWER_COLOR= EnumProperty.create("color", FlowerColor.class);
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);

    public HybridizableFlowerBlock(String name) {
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT));
        this.setRegistryName(name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FLOWER_COLOR,FlowerColor.WHITE));
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vec3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLOWER_COLOR);
    }

    public enum FlowerColor implements IStringSerializable {
        //一级花:自然生成
        YELLOW("yellow"),
        RED("red"),
        WHITE("white"),

        //二级花: Orange=yellow+red; Pink=white+red; Gold=yellow+white
        ORANGE("orange"),
        PINK("pink"),
        GOLD("gold"),

        //三级花: Black=2+2; Blue=2+1
        BLACK("black"),
        BLUE("blue");

        private final String name;
        private FlowerColor(String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
}
