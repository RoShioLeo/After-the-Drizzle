package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import roito.afterthedrizzle.common.block.inter.INormalRegister;

public class BlockNormal extends Block implements INormalRegister
{
    private final String name;

    public BlockNormal(String name, Material materialIn)
    {
        super(materialIn);
        this.name = name;
    }

    @Override
    public String getRegisterInfo()
    {
        return name;
    }
}
