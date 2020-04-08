package roito.afterthedrizzle.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.common.drink.DrinkEffectAttribute;
import roito.afterthedrizzle.common.drink.DrinkEffectsManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PorcelainCupDrinkItem extends ItemFluidContainer
{

    public PorcelainCupDrinkItem(int capacity)
    {
        super(new Properties().containerItem(ItemsRegistry.PORCELAIN_CUP).maxStackSize(1), capacity);
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
                    tooltip.add(f.getFluidInTank(0).getDisplayName().appendText(String.format(": %d / 250mB", f.getFluidInTank(0).getAmount())).applyTextStyle(TextFormatting.GRAY)));
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
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (canDrink(stack))
        {
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.getEatSound(stack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.4F);
            for (EffectInstance effct : getEffects(stack))
                entityLiving.addPotionEffect(effct);
            stack.shrink(1);
            if (entityLiving instanceof PlayerEntity)
            {
                ItemHandlerHelper.giveItemToPlayer((PlayerEntity) entityLiving, new ItemStack(ItemsRegistry.PORCELAIN_CUP));
            }
        }
        return stack;
    }

    public static boolean canDrink(ItemStack stack)
    {
        if (stack.getChildTag(FluidHandlerItemStack.FLUID_NBT_KEY) != null)
        {
            return FluidUtil.getFluidContained(stack).map(f -> FluidTags.getCollection().getOrCreate(new ResourceLocation("afterthedrizzle:drink")).contains(f.getFluid())).orElse(false);
        }
        return false;
    }

    public static List<EffectInstance> getEffects(ItemStack stack)
    {
        if (stack.getChildTag(FluidHandlerItemStack.FLUID_NBT_KEY) != null)
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
