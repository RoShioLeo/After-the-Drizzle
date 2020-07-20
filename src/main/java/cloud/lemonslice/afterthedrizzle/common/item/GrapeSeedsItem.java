package cloud.lemonslice.afterthedrizzle.common.item;

import cloud.lemonslice.afterthedrizzle.common.block.TrellisBlock;
import cloud.lemonslice.afterthedrizzle.common.block.TrellisWithVineBlock;
import cloud.lemonslice.afterthedrizzle.common.block.VineType;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropInfoManager;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class GrapeSeedsItem extends NormalItem
{
    public GrapeSeedsItem()
    {
        super("grapes");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof TrellisBlock && !(state.getBlock() instanceof TrellisWithVineBlock))
        {
            if (world.getBlockState(pos.down()).getBlock().isIn(Tags.Blocks.DIRT))
            {
                world.setBlockState(pos, CropInfoManager.getVineTrellis(VineType.GRAPE, (TrellisBlock) state.getBlock()).getRelevantState(state));
                context.getItem().shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
