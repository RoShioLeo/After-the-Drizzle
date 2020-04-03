package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DirtStoveBlock extends StoveBlock
{
    public DirtStoveBlock(String name, int light)
    {
        super(Properties.create(Material.GOURD).lightValue(light).sound(SoundType.STONE).hardnessAndResistance(0.5F), name, 1);
    }

    @Override
    public Block getLit()
    {
        return BlocksRegistry.LIT_DIRT_STOVE;
    }

    @Override
    public Block getUnlit()
    {
        return BlocksRegistry.DIRT_STOVE;
    }
}
