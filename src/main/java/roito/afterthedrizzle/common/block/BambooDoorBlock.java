package roito.afterthedrizzle.common.block;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import roito.afterthedrizzle.AfterTheDrizzle;

public class BambooDoorBlock extends DoorBlock {
    public BambooDoorBlock(String name) {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.5F));
        this.setRegistryName(name);
    }
    public static Item.Properties getItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT);
    }
}
