package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.tileentity.TileEntityStoneMill;
import roito.afterthedrizzle.registry.GuiElementsRegistry;

public class BlockStoneMill extends BlockHorizontal
{
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);

    public BlockStoneMill()
    {
        super(Material.ROCK);
        this.setHardness(1.5F);
        this.setSoundType(SoundType.STONE);
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

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState origin = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        return facing;
    }

    public EnumFacing getFacing(int meta)
    {
        return getStateFromMeta(meta).getValue(FACING);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityStoneMill();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(playerIn.getHeldItem(hand), 1));
        if (handler != null)
        {
            return FluidUtil.interactWithFluidHandler(playerIn, hand, te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side));
        }
        if (te instanceof TileEntityStoneMill)
        {
            if (!playerIn.isSneaking())
            {
                if (!playerIn.getHeldItem(hand).isEmpty())
                {
                    IItemHandler container = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                    playerIn.setHeldItem(hand, container.insertItem(0, playerIn.getHeldItem(hand), false));
                    te.markDirty();
                    return true;
                }
                else
                {
                    IItemHandler container = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                    boolean flag = true;
                    for (int i = 0; i <= 2; i++)
                    {
                        ItemStack itemStack = container.extractItem(i, container.getStackInSlot(0).getCount(), false);
                        te.markDirty();
                        if (!worldIn.isRemote && !itemStack.isEmpty())
                        {
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, itemStack));
                            flag = false;
                        }
                    }
                    if (flag && ((TileEntityStoneMill) te).isCompleted())
                    {
                        container = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                        ItemStack itemStack = container.extractItem(0, container.getStackInSlot(0).getCount(), false);
                        te.markDirty();
                        if (!worldIn.isRemote)
                        {
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, itemStack));
                        }
                    }
                    return true;
                }
            }
            else
            {
                int id = GuiElementsRegistry.GUI_STONE_MILL;
                playerIn.openGui(AfterTheDrizzle.getInstance(), id, worldIn, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te != null)
        {
            IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            for (int i = inventory.getSlots() - 1; i >= 0; --i)
            {
                if (!inventory.getStackInSlot(i).isEmpty())
                {
                    Block.spawnAsEntity(worldIn, pos, inventory.getStackInSlot(i));
                    ((IItemHandlerModifiable) inventory).setStackInSlot(i, ItemStack.EMPTY);
                }
            }
            inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            for (int i = inventory.getSlots() - 1; i >= 0; --i)
            {
                if (!inventory.getStackInSlot(i).isEmpty())
                {
                    Block.spawnAsEntity(worldIn, pos, inventory.getStackInSlot(i));
                    ((IItemHandlerModifiable) inventory).setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }
}
