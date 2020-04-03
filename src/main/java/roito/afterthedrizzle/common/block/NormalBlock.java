package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;

public class NormalBlock extends Block
{
    public NormalBlock(String name, Properties properties)
    {
        super(properties);
        this.setRegistryName(name);
    }
}
