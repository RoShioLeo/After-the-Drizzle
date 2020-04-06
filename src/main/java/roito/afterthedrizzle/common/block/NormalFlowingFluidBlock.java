package roito.afterthedrizzle.common.block;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;

import java.util.function.Supplier;

public class NormalFlowingFluidBlock extends FlowingFluidBlock
{
    public NormalFlowingFluidBlock(String name, Supplier<? extends FlowingFluid> supplier, Properties properties)
    {
        super(supplier, properties);
        this.setRegistryName(name);
    }
}
