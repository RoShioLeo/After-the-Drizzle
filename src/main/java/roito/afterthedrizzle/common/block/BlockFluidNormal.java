package roito.afterthedrizzle.common.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import roito.afterthedrizzle.common.block.inter.INormalRegister;

public class BlockFluidNormal extends BlockFluidClassic implements INormalRegister
{
    private final String name;

    public BlockFluidNormal(String name, Fluid fluid, Material material)
    {
        super(fluid, material);
        this.name = name;
    }

    @Override
    public String getRegisterInfo()
    {
        return name;
    }
}
