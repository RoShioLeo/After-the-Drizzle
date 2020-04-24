package roito.afterthedrizzle.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;

public class NormalHorizontalBlock extends HorizontalBlock
{
    protected NormalHorizontalBlock(Properties properties, String name)
    {
        super(properties);
        this.setRegistryName(name);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (context.getPlayer() != null)
        {
            return getDefaultState().with(HORIZONTAL_FACING, context.getPlayer().getHorizontalFacing().getOpposite());
        }
        return getDefaultState();
    }
}
