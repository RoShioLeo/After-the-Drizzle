package roito.afterthedrizzle.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.config.ConfigMain;

import static net.minecraft.item.ItemDye.applyBonemeal;

public class ItemAsh extends ItemNormal
{
    public ItemAsh()
    {
        super("ash", 64, AfterTheDrizzle.TAB_CRAFT);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        if (ConfigMain.others.useAshAsBoneMeal && applyBonemeal(itemstack, worldIn, pos, player, hand))
        {
            if (!worldIn.isRemote)
            {
                worldIn.playEvent(2005, pos, 0);
            }

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
