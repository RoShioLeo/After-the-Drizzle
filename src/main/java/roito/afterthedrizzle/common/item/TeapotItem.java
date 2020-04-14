package roito.afterthedrizzle.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import roito.afterthedrizzle.AfterTheDrizzle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TeapotItem extends ItemFluidContainer
{
    public TeapotItem()
    {
        super(new Properties().group(AfterTheDrizzle.GROUP_CORE), 1000);
        this.setRegistryName("porcelain_teapot");
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return super.initCapabilities(new ItemStack(ItemsRegistry.PORCELAIN_TEAPOT_DRINK), nbt);
    }
}
