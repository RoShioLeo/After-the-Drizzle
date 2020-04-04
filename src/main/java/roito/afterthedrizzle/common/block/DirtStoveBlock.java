package roito.afterthedrizzle.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DirtStoveBlock extends StoveBlock
{
    public DirtStoveBlock(String name)
    {
        super(Properties.create(Material.GOURD).sound(SoundType.STONE).hardnessAndResistance(0.5F), name, 1);
    }
}
