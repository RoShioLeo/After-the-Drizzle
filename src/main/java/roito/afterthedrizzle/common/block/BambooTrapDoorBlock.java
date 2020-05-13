package roito.afterthedrizzle.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;

public class BambooTrapDoorBlock extends TrapDoorBlock {
    public BambooTrapDoorBlock(String name) {
        super(Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(3.0F));
        setRegistryName(name);

    }
}
