package roito.afterthedrizzle.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import roito.afterthedrizzle.common.recipe.bamboo_tray.BambooTaryRecipe;
import roito.afterthedrizzle.common.tileentity.BambooTrayTileEntity;
import roito.afterthedrizzle.common.tileentity.NormalContainerTileEntity;
import roito.afterthedrizzle.common.tileentity.TileEntityTypeRegistry;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

public class BambooTrayBlock extends NormalBlock
{
    protected static final VoxelShape SHAPE;

    public BambooTrayBlock()
    {
        super("bamboo_tray", Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F));
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

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP) || worldIn.getBlockState(pos.down()).getBlock() instanceof HopperBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    private void dropItems(World worldIn, BlockPos pos)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null)
        {
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i)
                {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY)
                    {
                        Block.spawnAsEntity(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.hasTileEntity() && !(newState.getBlock() == this))
        {
            ((NormalContainerTileEntity) worldIn.getTileEntity(pos)).prepareForRemove();
            dropItems(worldIn, pos);
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof BambooTrayTileEntity)
        {
            ((BambooTrayTileEntity) te).refreshSeed();
            if (!player.isSneaking())
            {
                if (((BambooTrayTileEntity) te).isDoubleClick())
                {
                    if (!worldIn.isRemote)
                    {
                        dropItems(worldIn, pos);
                    }
                    return true;
                }
                if (!player.getHeldItem(handIn).isEmpty())
                {
                    if (!((BambooTrayTileEntity) te).isWorking())
                    {
                        if (!worldIn.isRemote)
                        {
                            dropItems(worldIn, pos);
                        }
                        te.markDirty();
                    }
                    te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
                    {
                        BambooTaryRecipe recipe = BambooTrayTileEntity.getRecipeManager(BambooTrayMode.getMode(worldIn, pos)).getRecipe(player.getHeldItem(handIn));
                        if (!recipe.getOutput().isEmpty())
                        {
                            player.setHeldItem(handIn, inv.insertItem(0, player.getHeldItem(handIn), false));
                            te.markDirty();
                        }
                        else ((BambooTrayTileEntity) te).singleClickStart();
                    });
                    return true;
                }
                else
                {
                    if (!((BambooTrayTileEntity) te).isWorking())
                    {
                        if (!worldIn.isRemote)
                        {
                            dropItems(worldIn, pos);
                        }
                        te.markDirty();
                        return true;
                    }
                    else ((BambooTrayTileEntity) te).singleClickStart();
                }
            }
            else
            {
                if (!worldIn.isRemote)
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return TileEntityTypeRegistry.BAMBOO_TRAY.create();
    }

    static
    {
        VoxelShape side_north = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 2.0D);
        VoxelShape side_south = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 14.0D, 16.0D, 3.0D, 2.0D);
        VoxelShape side_west = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 2.0D, 3.0D, 16.0D);
        VoxelShape side_east = VoxelShapeHelper.createVoxelShape(14.0D, 0.0D, 0.0D, 2.0D, 3.0D, 16.0D);
        VoxelShape bottom = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
        SHAPE = VoxelShapes.or(side_north, side_south, side_east, side_west, bottom);
    }
}
