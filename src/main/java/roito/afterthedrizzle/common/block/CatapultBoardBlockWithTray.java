package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
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
import roito.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;
import roito.afterthedrizzle.helper.VoxelShapeHelper;

import java.util.Random;

public class CatapultBoardBlockWithTray extends NormalHorizontalBlock
{
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 5, 16);
    private static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public CatapultBoardBlockWithTray(String name, Properties properties)
    {
        super(properties, name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(ENABLED, false));
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
        return hasSolidSideOnTop(worldIn, pos.down());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, ENABLED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
    {
        worldIn.setBlockState(pos, state.with(ENABLED, false), 2);
    }

    private static void dropItems(World worldIn, BlockPos pos)
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
            if (worldIn.isRemote)
            {
                ((BambooTrayTileEntity) te).refreshSeed();
                return true;
            }
            if (!player.isSneaking())
            {
                if (((BambooTrayTileEntity) te).isDoubleClick())
                {
                    dropItems(worldIn, pos);
                    return true;
                }
                if (!((BambooTrayTileEntity) te).isWorking())
                {
                    dropItems(worldIn, pos);
                    te.markDirty();
                }
                if (!player.getHeldItem(handIn).isEmpty())
                {
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
                    if (((BambooTrayTileEntity) te).isWorking())
                    {
                        ((BambooTrayTileEntity) te).singleClickStart();
                    }
                }
            }
            else
            {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
            }
        }
        return false;
    }

    public static void shoot(World worldIn, BlockPos pos)
    {
        Direction d = worldIn.getBlockState(pos).get(HORIZONTAL_FACING).getOpposite();
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null)
        {
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i)
                {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY)
                    {
                        Block.spawnAsEntity(worldIn, pos.offset(d), inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return TileEntityTypesRegistry.BAMBOO_TRAY.create();
    }
}
