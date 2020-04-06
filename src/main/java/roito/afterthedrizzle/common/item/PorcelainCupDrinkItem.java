package roito.afterthedrizzle.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PorcelainCupDrinkItem extends ItemFluidContainer
{

    public PorcelainCupDrinkItem()
    {
        super(new Properties().maxStackSize(1), 500);
        this.setRegistryName("porcelain_cup_drink");
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new FluidHandlerItemStack(stack, capacity)
        {
            @Nonnull
            @Override
            public ItemStack getContainer()
            {
                return getFluid().isEmpty() ? new ItemStack(ItemsRegistry.PORCELAIN_CUP) : this.container;
            }
        };
    }
}
