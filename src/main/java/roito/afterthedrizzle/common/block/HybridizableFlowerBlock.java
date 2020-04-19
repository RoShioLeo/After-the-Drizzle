package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

import javax.annotation.Nullable;
import java.util.*;


public class HybridizableFlowerBlock extends BushBlock implements IGrowable {
    public static final EnumProperty<FlowerColor> FLOWER_COLOR = EnumProperty.create("color", FlowerColor.class);
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);

    public HybridizableFlowerBlock(String name) {
        super(Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).doesNotBlockMovement().sound(SoundType.PLANT));
        this.setRegistryName(name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FLOWER_COLOR, FlowerColor.WHITE));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vec3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ItemStack stack = new ItemStack(BlocksRegistry.CHRYSANTHEMUM_ITEM);
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("color", new StringNBT(state.get(FLOWER_COLOR).getName()));
        stack.setTag(nbt);
        List<ItemStack> list = new ArrayList();
        list.add(stack);
        return list;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLOWER_COLOR);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getItem().hasTag() && context.getItem().getTag().contains("color")) {
            BlockState state = this.getStateContainer().getBaseState().with(FLOWER_COLOR, FlowerColor.getColorFromName(context.getItem().getTag().getString("color")));
            return state;
        }
        return this.getDefaultState();
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        List<BlockPos> positions = new ArrayList<>();
        Collections.addAll(positions, pos.east(), pos.west(), pos.north(), pos.south());
        for (BlockPos p : positions) {
            if (worldIn.getBlockState(p).isAir() && Block.isDirt(worldIn.getBlockState(p.down()).getBlock())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        List<BlockPos> growPositions = new ArrayList<>();
        List<BlockPos> canGrowPositions = new ArrayList<>();
        Collections.addAll(growPositions, pos.east(), pos.west(), pos.north(), pos.south());
        for (BlockPos p : growPositions) {
            if (worldIn.getBlockState(p).isAir() && Block.isDirt(worldIn.getBlockState(p.down()).getBlock())) {
                canGrowPositions.add(p);
            }
        }
        final double randomD1 = Math.random();
        final int growPosI = (int) (randomD1 * canGrowPositions.size());
        List<BlockPos> isHybFlowersPos = new ArrayList<>();
        List<BlockPos> hybFlowersPos = new ArrayList<>();
        BlockPos growPos = canGrowPositions.get(growPosI);
        Collections.addAll(isHybFlowersPos, growPos.east(), growPos.west(), growPos.north(), growPos.south());
        for (BlockPos p : isHybFlowersPos) {
            if (isSameKind(worldIn, p)) {
                hybFlowersPos.add(p);
            }
        }
        final double randomD2 = Math.random();
        final int hybPosI = (int) (randomD2 * hybFlowersPos.size());

        worldIn.setBlockState(growPos, this.getDefaultState().with(FLOWER_COLOR, getHybColor(state.get(FLOWER_COLOR), worldIn.getBlockState(hybFlowersPos.get(hybPosI)).get(FLOWER_COLOR))));
    }

    public FlowerColor getHybColor(FlowerColor color1, FlowerColor color2) {
        switch (color1) {
            case RED:
                switch (color2) {
                    case WHITE:
                        return FlowerColor.PINK;
                    case YELLOW:
                        return FlowerColor.ORANGE;

                    case GOLD:
                    case PINK:
                    case ORANGE:
                        return FlowerColor.BLUE;

                    default:
                        return FlowerColor.RED;
                }
            case WHITE:
                switch (color2) {
                    case RED:
                        return FlowerColor.PINK;
                    case YELLOW:
                        return FlowerColor.GOLD;

                    case GOLD:
                    case PINK:
                    case ORANGE:
                        return FlowerColor.BLUE;

                    default:
                        return FlowerColor.WHITE;
                }
            case YELLOW:
                switch (color2) {
                    case RED:
                        return FlowerColor.ORANGE;
                    case WHITE:
                        return FlowerColor.GOLD;

                    case GOLD:
                    case PINK:
                    case ORANGE:
                        return FlowerColor.BLUE;

                    default:
                        return FlowerColor.YELLOW;
                }

            case GOLD:
                switch (color2) {
                    case RED:
                    case WHITE:
                    case YELLOW:
                        return FlowerColor.BLUE;

                    case PINK:
                    case ORANGE:
                        return FlowerColor.BLACK;

                    default:
                        return FlowerColor.GOLD;
                }
            case PINK:
                switch (color2) {
                    case RED:
                    case WHITE:
                    case YELLOW:
                        return FlowerColor.BLUE;

                    case GOLD:
                    case ORANGE:
                        return FlowerColor.BLACK;

                    default:
                        return FlowerColor.PINK;
                }
            case ORANGE:
                switch (color2) {
                    case RED:
                    case WHITE:
                    case YELLOW:
                        return FlowerColor.BLUE;

                    case GOLD:
                    case PINK:
                        return FlowerColor.BLACK;

                    default:
                        return FlowerColor.ORANGE;
                }

            case BLUE:
                return FlowerColor.BLUE;
            case BLACK:
                return FlowerColor.BLACK;

            default:
                return FlowerColor.WHITE;
        }
    }

    public boolean isSameKind(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getBlock().getRegistryName() == this.getRegistryName();
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        ItemStack stack = new ItemStack(BlocksRegistry.CHRYSANTHEMUM_ITEM);
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("color", new StringNBT(state.get(FLOWER_COLOR).getName()));
        stack.setTag(nbt);
        return stack;
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

        public static Collection<FlowerColor> getColors() {
            Collection<FlowerColor> colors = new ArrayList<>();
            Collections.addAll(colors, FlowerColor.BLACK, FlowerColor.BLUE, FlowerColor.RED, FlowerColor.WHITE, FlowerColor.YELLOW, FlowerColor.ORANGE, FlowerColor.GOLD, FlowerColor.PINK);
            return colors;
        }

        public static FlowerColor getColorFromName(String name) {
            for (FlowerColor color : getColors()) {
                if (color.getName().equals(name)) {
                    return color;
                }
            }
            return WHITE;
        }
    }
}
