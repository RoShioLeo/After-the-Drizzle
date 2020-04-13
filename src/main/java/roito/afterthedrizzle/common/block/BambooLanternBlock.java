package roito.afterthedrizzle.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import roito.afterthedrizzle.AfterTheDrizzle;

public class BambooLanternBlock extends LanternBlock {
    public BambooLanternBlock() {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.5F).lightValue(15));
        this.setRegistryName("bamboo_lantern");
    }

    @Override
    public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        return 15;
    }

    public static Item.Properties getItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT);
    }
}
