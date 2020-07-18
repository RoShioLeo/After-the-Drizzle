package cloud.lemonslice.afterthedrizzle.common.block;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BambooDoorBlock extends DoorBlock
{
    public BambooDoorBlock(String name)
    {
        super(Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F));
        this.setRegistryName(name);
    }
}
