package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemBlock extends BlockItem
{
    public ItemBlock(Block block, Properties properties)
    {
        super(block, properties);
        this.setRegistryName(block.getRegistryName());
    }
}
