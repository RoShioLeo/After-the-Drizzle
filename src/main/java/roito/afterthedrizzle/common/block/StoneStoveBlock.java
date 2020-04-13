package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class StoneStoveBlock extends StoveBlock
{
    public StoneStoveBlock(String name, int light)
    {
        super(Block.Properties.create(Material.ROCK).lightValue(light).sound(SoundType.STONE).hardnessAndResistance(3.5F), name, 2);
    }

    @Override
    public Block getLit()
    {
        return BlocksRegistry.LIT_STONE_STOVE;
    }

    @Override
    public Block getUnlit()
    {
        return BlocksRegistry.STONE_STOVE;
    }
}
