package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.inter.INormalRegister;
import roito.afterthedrizzle.common.tileentity.TileEntityDrinkMaker;
import roito.afterthedrizzle.helper.BlocksHelper;
import roito.afterthedrizzle.registry.GuiElementsRegistry;
import roito.afterthedrizzle.registry.ItemsRegistry;

import java.util.Random;

public class BlockDrinkMaker extends BlockHorizontal implements INormalRegister
{
    public static final PropertyBool LEFT = PropertyBool.create("left");
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

    public BlockDrinkMaker()
    {
        super(Material.WOOD);
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEFT, true).withProperty(FACING, EnumFacing.NORTH));
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(AfterTheDrizzle.TAB_CRAFT);
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
    protected boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            if (!state.getValue(LEFT))
            {
                pos = pos.offset(BlocksHelper.getPreviousHorizontal(state.getValue(FACING)));
            }
            TileEntity te = worldIn.getTileEntity(pos);
            IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(playerIn.getHeldItem(hand), 1));
            if (handler != null)
            {
                return FluidUtil.interactWithFluidHandler(playerIn, hand, te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side));
            }
            if (te instanceof TileEntityDrinkMaker)
            {
                int id = GuiElementsRegistry.GUI_DRINK_MAKER;
                playerIn.openGui(AfterTheDrizzle.getInstance(), id, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing enumfacing = state.getValue(FACING);

        if (state.getValue(LEFT))
        {
            if (worldIn.getBlockState(pos.offset(BlocksHelper.getNextHorizontal(enumfacing))).getBlock() != this)
            {
                if (!worldIn.isRemote)
                {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
                worldIn.setBlockToAir(pos);
            }
        }
        else if (worldIn.getBlockState(pos.offset(BlocksHelper.getPreviousHorizontal(enumfacing))).getBlock() != this)
        {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(LEFT) ? ItemsRegistry.DRINK_MAKER : Items.AIR;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEFT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);
        return this.getDefaultState().withProperty(LEFT, (meta & 4) > 0).withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(LEFT))
        {
            i |= 4;
        }
        return i;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return state.getValue(LEFT);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return state.getValue(LEFT) ? new TileEntityDrinkMaker() : null;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && !state.getValue(LEFT))
        {
            BlockPos blockpos = pos.offset(BlocksHelper.getPreviousHorizontal(state.getValue(FACING)));

            if (worldIn.getBlockState(blockpos).getBlock() == this)
            {
                worldIn.setBlockToAir(blockpos);
            }
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        if (state.getValue(LEFT))
        {
            spawnAsEntity(worldIn, pos, new ItemStack(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, null, stack);
        }
    }

    @Override
    public String getRegisterInfo()
    {
        return "drink_maker";
    }

    @Override
    public boolean shouldRegisterItem()
    {
        return false;
    }
}
