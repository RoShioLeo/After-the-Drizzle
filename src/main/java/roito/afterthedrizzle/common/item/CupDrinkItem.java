package roito.afterthedrizzle.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.drink.DrinkEffectAttribute;
import roito.afterthedrizzle.common.drink.DrinkEffectsManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class CupDrinkItem extends ItemFluidContainer
{

    public CupDrinkItem(int capacity, Item container, String name)
    {
        super(new Properties().containerItem(container).maxStackSize(1), capacity);
        this.setRegistryName(name);
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new FluidHandlerItemStack(stack, capacity)
        {
            @Nonnull
            @Override
            @SuppressWarnings("deprecation")
            public ItemStack getContainer()
            {
                return getFluid().isEmpty() ? new ItemStack(CupDrinkItem.this.getContainerItem()) : this.container;
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack)
            {
                return stack.getFluid().getAttributes().getTemperature() <= 423 && !stack.getFluid().getAttributes().isLighterThanAir();
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getChildTag(FLUID_NBT_KEY) != null)
        {
            FluidUtil.getFluidHandler(stack).ifPresent(f ->
                    tooltip.add(f.getFluidInTank(0).getDisplayName().appendText(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).applyTextStyle(TextFormatting.GRAY)));
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
    {
        if (group == AfterTheDrizzle.GROUP_CORE)
        {
            for (Fluid fluid : FluidTags.getCollection().getOrCreate(new ResourceLocation("afterthedrizzle:drink")).getAllElements())
            {
                ItemStack itemStack = new ItemStack(this);
                CompoundNBT fluidTag = new CompoundNBT();
                new FluidStack(fluid, capacity).writeToNBT(fluidTag);
                itemStack.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
                items.add(itemStack);
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return canDrink(stack) ? UseAction.DRINK : UseAction.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return canDrink(stack) ? 32 : 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (canDrink(stack))
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
        else
        {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (canDrink(stack))
        {
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.getEatSound(stack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.4F);
            for (EffectInstance effct : getEffects(stack))
                entityLiving.addPotionEffect(effct);
            if (entityLiving instanceof PlayerEntity)
            {
                ItemHandlerHelper.giveItemToPlayer((PlayerEntity) entityLiving, new ItemStack(this.getContainerItem()));
            }
            stack.shrink(1);
        }
        return stack;
    }

    public static boolean canDrink(ItemStack stack)
    {
        if (stack.getChildTag(FLUID_NBT_KEY) != null)
        {
            return FluidUtil.getFluidContained(stack).map(f -> FluidTags.getCollection().getOrCreate(new ResourceLocation("afterthedrizzle:drink")).contains(f.getFluid())).orElse(false);
        }
        return false;
    }

    public static List<EffectInstance> getEffects(ItemStack stack)
    {
        if (stack.getChildTag(FLUID_NBT_KEY) != null)
        {
            return FluidUtil.getFluidContained(stack).map(f ->
            {
                DrinkEffectAttribute[] effectAttributes = DrinkEffectsManager.getDrinkEffects(f.getFluid());
                List<EffectInstance> list = new ArrayList<>();
                for (DrinkEffectAttribute effect : effectAttributes)
                {
                    list.add(new EffectInstance(effect.getPotion(), f.getAmount() * effect.getDuration(), effect.getLevel()));
                }
                return list;
            }).orElse(new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
