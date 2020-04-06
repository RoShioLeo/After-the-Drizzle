package roito.afterthedrizzle.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getChildTag(FluidHandlerItemStack.FLUID_NBT_KEY) != null)
        {
            FluidUtil.getFluidHandler(stack).ifPresent(f ->
                    tooltip.add(f.getFluidInTank(0).getDisplayName().appendText(String.format(": %d / 500mB", f.getFluidInTank(0).getAmount())).applyTextStyle(TextFormatting.GRAY)));
        }
    }
}
