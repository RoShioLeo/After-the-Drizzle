package roito.afterthedrizzle.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.BlockDrinkMaker;
import roito.afterthedrizzle.common.block.inter.INormalRegister;
import roito.afterthedrizzle.helper.BlocksHelper;
import roito.afterthedrizzle.registry.BlocksRegistry;

public class ItemDrinkMaker extends ItemBlock implements INormalRegister
{
    public ItemDrinkMaker()
    {
        super(BlocksRegistry.DRINK_MAKER);
        this.setCreativeTab(AfterTheDrizzle.TAB_CRAFT);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(worldIn, pos);

            if (!flag)
            {
                pos = pos.up();
            }

            ItemStack itemstack = player.getHeldItem(hand);
            if (player.canPlayerEdit(pos, facing, itemstack) && (flag || worldIn.isAirBlock(pos)) && worldIn.getBlockState(pos.down()).isTopSolid())
            {
                int i = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                EnumFacing enumfacing = EnumFacing.byHorizontalIndex(i);
                BlockPos blockpos = pos.offset(BlocksHelper.getNextHorizontal(enumfacing));
                boolean left = false, right = false;
                if (player.canPlayerEdit(blockpos, facing, itemstack) && (worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) || worldIn.isAirBlock(blockpos)) && worldIn.getBlockState(blockpos.down()).isTopSolid())
                {
                    left = true;
                }
                else
                {
                    blockpos = pos.offset(BlocksHelper.getPreviousHorizontal(enumfacing));
                    if (player.canPlayerEdit(blockpos, facing, itemstack) && (worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) || worldIn.isAirBlock(blockpos)) && worldIn.getBlockState(blockpos.down()).isTopSolid())
                    {
                        right = true;
                    }
                }
                if (left || right)
                {
                    IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
                    IBlockState iblockstate2 = BlocksRegistry.DRINK_MAKER.getDefaultState().withProperty(BlockDrinkMaker.LEFT, left).withProperty(BlockDrinkMaker.FACING, enumfacing);
                    worldIn.setBlockState(pos, iblockstate2, 10);
                    worldIn.setBlockState(blockpos, iblockstate2.withProperty(BlockDrinkMaker.LEFT, !left), 10);
                    SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    worldIn.notifyNeighborsRespectDebug(pos, block, false);
                    worldIn.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public String getRegisterInfo()
    {
        return "drink_maker";
    }
}
