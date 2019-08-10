package roito.cultivage.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import roito.cultivage.registry.BlocksRegistry;

public class BlockStoveDirt extends BlockStove
{
    public BlockStoveDirt(boolean light)
    {
        super(light, 1, Material.GROUND);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
    }

    @Override
    public Block getLit()
    {
        return BlocksRegistry.LIT_STOVE_DIRT;
    }

    @Override
    public Block getUnlit()
    {
        return BlocksRegistry.STOVE_DIRT;
    }
}
