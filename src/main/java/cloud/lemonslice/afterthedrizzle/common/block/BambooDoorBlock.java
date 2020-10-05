package cloud.lemonslice.afterthedrizzle.common.block;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.world.storage.loot.LootContext;

import java.util.List;

public class BambooDoorBlock extends DoorBlock
{
    public BambooDoorBlock(String name)
    {
        super(Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F));
        this.setRegistryName(name);
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> list = Lists.newArrayList();
        if (state.get(HALF).equals(DoubleBlockHalf.UPPER)) list.add(new ItemStack(this));
        return list;
    }
}
