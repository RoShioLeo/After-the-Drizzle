package roito.afterthedrizzle.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class NormalHorizontalBlock extends HorizontalBlock
{
    protected NormalHorizontalBlock(Properties properties, String name)
    {
        super(properties);
        this.setRegistryName(name);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (placer != null)
        {
            worldIn.setBlockState(pos, state.with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()), 2);
        }
    }
}
