package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class StoneStoveBlock extends StoveBlock
{
    public StoneStoveBlock(String name)
    {
        super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), name, 2);
    }
}
