package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import roito.afterthedrizzle.common.environment.FlowerColor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class HybridizableFlowerBlock extends BushBlock implements IGrowable
{
    public static final EnumProperty<FlowerColor> FLOWER_COLOR = EnumProperty.create("color", FlowerColor.class);
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);

    public HybridizableFlowerBlock(String name)
    {
        super(Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).doesNotBlockMovement().sound(SoundType.PLANT));
        this.setRegistryName(name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FLOWER_COLOR, FlowerColor.WHITE));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        Vec3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> list = super.getDrops(state, builder);
        ItemStack stack = new ItemStack(this);
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("color", new StringNBT(state.get(FLOWER_COLOR).getName()));
        stack.setTag(nbt);
        list.add(stack);
        return list;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FLOWER_COLOR);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (context.getItem().hasTag() && context.getItem().getTag().contains("color"))
        {
            BlockState state = this.getStateContainer().getBaseState().with(FLOWER_COLOR, FlowerColor.getFlowerColor(context.getItem().getTag().getString("color")));
            return state;
        }
        return this.getDefaultState();
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
    {
        List<BlockPos> positions = new ArrayList<>();
        Collections.addAll(positions, pos.east(), pos.west(), pos.north(), pos.south());
        for (BlockPos p : positions)
        {
            if (worldIn.getBlockState(p).isAir() && Block.isDirt(worldIn.getBlockState(p.down()).getBlock()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state)
    {
        List<BlockPos> growPositions = new ArrayList<>();
        List<BlockPos> canGrowPositions = new ArrayList<>();
        Collections.addAll(growPositions, pos.east(), pos.west(), pos.north(), pos.south());
        for (BlockPos p : growPositions)
        {
            if (worldIn.getBlockState(p).isAir() && Block.isDirt(worldIn.getBlockState(p.down()).getBlock()))
            {
                canGrowPositions.add(p);
            }
        }
        final double randomD1 = Math.random();
        final int growPosI = (int) (randomD1 * canGrowPositions.size());
        List<BlockPos> isHybFlowersPos = new ArrayList<>();
        List<BlockPos> hybFlowersPos = new ArrayList<>();
        BlockPos growPos = canGrowPositions.get(growPosI);
        Collections.addAll(isHybFlowersPos, growPos.east(), growPos.west(), growPos.north(), growPos.south());
        for (BlockPos p : isHybFlowersPos)
        {
            if (isSameKind(worldIn, p))
            {
                hybFlowersPos.add(p);
            }
        }
        if (hybFlowersPos.size() >= 2)
        {
            hybFlowersPos.remove(pos);
        }
        final double randomD2 = Math.random();
        final int hybPosI = (int) (randomD2 * hybFlowersPos.size());

        worldIn.setBlockState(growPos, this.getDefaultState().with(FLOWER_COLOR, FlowerColor.getHybColor(state.get(FLOWER_COLOR), worldIn.getBlockState(hybFlowersPos.get(hybPosI)).get(FLOWER_COLOR))));
    }

    public boolean isSameKind(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos).getBlock().getRegistryName() == this.getRegistryName();
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        ItemStack stack = new ItemStack(BlocksRegistry.CHRYSANTHEMUM_ITEM);
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("color", new StringNBT(state.get(FLOWER_COLOR).getName()));
        stack.setTag(nbt);
        return stack;
    }
}
