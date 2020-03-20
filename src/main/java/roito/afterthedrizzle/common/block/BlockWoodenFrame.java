package roito.afterthedrizzle.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import roito.afterthedrizzle.AfterTheDrizzle;

public class BlockWoodenFrame extends BlockNormal
{
    public BlockWoodenFrame()
    {
        super("wooden_frame", Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(0.5F);
        this.setCreativeTab(AfterTheDrizzle.TAB_CRAFT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
