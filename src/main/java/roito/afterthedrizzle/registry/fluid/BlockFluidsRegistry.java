package roito.afterthedrizzle.registry.fluid;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidBase;
import roito.afterthedrizzle.common.block.BlockFluidNormal;
import roito.afterthedrizzle.registry.RegistryModule;

public final class BlockFluidsRegistry extends RegistryModule
{
    public static final BlockFluidBase BLOCK_SUGARY_WATER = new BlockFluidNormal("sugary_water", FluidsRegistry.SUGARY_WATER, Material.WATER);
    public static final BlockFluidBase BLOCK_APPLE_JUICE = new BlockFluidNormal("apple_juice", FluidsRegistry.APPLE_JUICE, Material.WATER);
    public static final BlockFluidBase BLOCK_BEET_JUICE = new BlockFluidNormal("beet_juice", FluidsRegistry.BEET_JUICE, Material.WATER);
    public static final BlockFluidBase BLOCK_CARROT_JUICE = new BlockFluidNormal("carrot_juice", FluidsRegistry.CARROT_JUICE, Material.WATER);
    public static final BlockFluidBase BLOCK_SUGAR_CANE_JUICE = new BlockFluidNormal("sugar_cane_juice", FluidsRegistry.SUGAR_CANE_JUICE, Material.WATER);

    public BlockFluidsRegistry()
    {
        super(false);
    }
}
