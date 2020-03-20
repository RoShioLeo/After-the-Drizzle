package roito.afterthedrizzle.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.tileentity.TileEntityWoodenBarrel;
import roito.afterthedrizzle.registry.GuiElementsRegistry;

public class BlockWoodenBarrel extends BlockNormal
{
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

    public BlockWoodenBarrel()
    {
        super("wooden_barrel", Material.WOOD);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
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

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityWoodenBarrel();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);
            IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(playerIn.getHeldItem(hand), 1));
            if (handler != null)
            {
                return FluidUtil.interactWithFluidHandler(playerIn, hand, te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side));
            }
            int id = GuiElementsRegistry.GUI_WOODEN_BARREL;
            playerIn.openGui(AfterTheDrizzle.getInstance(), id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
