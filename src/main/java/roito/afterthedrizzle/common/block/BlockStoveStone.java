package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import roito.afterthedrizzle.registry.BlocksRegistry;

public class BlockStoveStone extends BlockStove
{
    public BlockStoveStone(boolean light)
    {
        super(light, 2, Material.ROCK);
        this.setHardness(3.5F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public Block getLit()
    {
        return BlocksRegistry.LIT_STOVE_STONE;
    }

    @Override
    public Block getUnlit()
    {
        return BlocksRegistry.STOVE_STONE;
    }
}
