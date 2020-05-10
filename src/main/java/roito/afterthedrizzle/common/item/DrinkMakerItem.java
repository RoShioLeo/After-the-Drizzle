package roito.afterthedrizzle.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.block.DrinkMakerBlock;
import roito.afterthedrizzle.helper.BlockHelper;

public class DrinkMakerItem extends NormalBlockItem
{
    public DrinkMakerItem()
    {
        super(BlocksRegistry.DRINK_MAKER, new Properties().group(AfterTheDrizzle.GROUP_CORE));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World worldIn = context.getWorld();
        if (worldIn.isRemote)
        {
            return ActionResultType.SUCCESS;
        }
        else if (context.getFace() != Direction.UP)
        {
            return ActionResultType.FAIL;
        }
        else
        {
            BlockPos pos = context.getPos();
            BlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(iblockstate, new BlockItemUseContext(context));

            if (!flag)
            {
                pos = pos.up();
            }
            PlayerEntity player = context.getPlayer();
            ItemStack itemstack = player.getHeldItem(context.getHand());
            if (player.canPlayerEdit(pos, context.getFace(), itemstack) && (flag || worldIn.isAirBlock(pos)) && worldIn.getBlockState(pos.down()).isTopSolid(worldIn, pos.down(), player))
            {
                int i = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                Direction enumfacing = Direction.byHorizontalIndex(i);
                BlockPos blockpos = pos.offset(BlockHelper.getNextHorizontal(enumfacing));
                boolean left = false, right = false;
                if (player.canPlayerEdit(blockpos, context.getFace(), itemstack) && (worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn.getBlockState(blockpos), new BlockItemUseContext(context)) || worldIn.isAirBlock(blockpos)) && worldIn.getBlockState(blockpos.down()).isTopSolid(worldIn, blockpos.down(), player))
                {
                    left = true;
                }
                else
                {
                    blockpos = pos.offset(BlockHelper.getPreviousHorizontal(enumfacing));
                    if (player.canPlayerEdit(blockpos, context.getFace(), itemstack) && (worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn.getBlockState(blockpos), new BlockItemUseContext(context)) || worldIn.isAirBlock(blockpos)) && worldIn.getBlockState(blockpos.down()).isTopSolid(worldIn, blockpos.down(), player))
                    {
                        right = true;
                    }
                }
                if (left || right)
                {
                    BlockState iblockstate1 = worldIn.getBlockState(blockpos);
                    BlockState iblockstate2 = BlocksRegistry.DRINK_MAKER.getDefaultState().with(DrinkMakerBlock.LEFT, left).with(DrinkMakerBlock.HORIZONTAL_FACING, enumfacing);
                    worldIn.setBlockState(pos, iblockstate2, 10);
                    worldIn.setBlockState(blockpos, iblockstate2.with(DrinkMakerBlock.LEFT, !left), 10);
                    SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    worldIn.notifyNeighborsOfStateChange(pos, block);
                    worldIn.notifyNeighborsOfStateChange(blockpos, iblockstate1.getBlock());

                    itemstack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.FAIL;
    }
}
