package roito.afterthedrizzle.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
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
import roito.afterthedrizzle.common.block.BlockCapsuleStation;
import roito.afterthedrizzle.common.block.inter.INormalRegister;

public class ItemCapsuleStation extends ItemBlock implements INormalRegister
{
    public ItemCapsuleStation()
    {
        super(null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, null) && player.canPlayerEdit(pos.up(), facing, itemstack) && worldIn.mayPlace(this.block, pos.up(), false, facing, null))
        {
            int index = MathHelper.floor((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing enumfacing = EnumFacing.byHorizontalIndex(index);
            int i = this.getMetadata(itemstack.getMetadata());
            IBlockState iblockstate1 = this.block.getDefaultState().withProperty(BlockCapsuleStation.FACING, enumfacing).withProperty(BlockCapsuleStation.UP, false);

            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            {
                iblockstate1 = worldIn.getBlockState(pos);
                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                pos = pos.up();
                if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1.withProperty(BlockCapsuleStation.UP, true)))
                {
                    iblockstate1 = worldIn.getBlockState(pos);
                    soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    itemstack.shrink(1);
                }
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public String getRegisterInfo()
    {
        return "capsule_station";
    }
}
