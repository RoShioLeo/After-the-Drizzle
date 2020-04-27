package roito.afterthedrizzle.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.BlocksRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class TeapotItem extends NormalBlockItem
{
    private static final int CAPACITY = 1000;

    public TeapotItem()
    {
        super(BlocksRegistry.TEAPOT, new Properties().group(AfterTheDrizzle.GROUP_CORE).maxStackSize(1));
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new FluidHandlerItemStack(stack, CAPACITY);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() != ActionResultType.SUCCESS ? this.tryPlace(new BlockItemUseContext(context)) : ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS)
        {
            return new ActionResult<>(ActionResultType.PASS, itemStack);
        }
        else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<>(ActionResultType.PASS, itemStack);
        }
        else
        {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemStack))
            {
                BlockState blockstate1 = worldIn.getBlockState(blockpos);
                if (blockstate1.getBlock() instanceof FlowingFluidBlock)
                {
                    Fluid fluid = ((FlowingFluidBlock) blockstate1.getBlock()).getFluid();
                    if (fluid != Fluids.EMPTY && fluid.isIn(FluidTags.WATER))
                    {
                        ((FlowingFluidBlock) blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
                        playerIn.addStat(Stats.ITEM_USED.get(this));

                        SoundEvent soundevent = SoundEvents.ITEM_BOTTLE_FILL;
                        playerIn.playSound(soundevent, 1.0F, 1.0F);

                        if (!playerIn.isCreative())
                        {
                            ItemStack itemStack1 = new ItemStack(this);
                            CompoundNBT fluidTag = new CompoundNBT();
                            new FluidStack(fluid, CAPACITY).writeToNBT(fluidTag);
                            itemStack1.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
                            ItemHandlerHelper.giveItemToPlayer(playerIn, itemStack1);

                            itemStack.shrink(1);
                        }

                        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
                    }
                }
                return new ActionResult<>(ActionResultType.FAIL, itemStack);
            }
            else
            {
                return new ActionResult<>(ActionResultType.FAIL, itemStack);
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
    {
        if (group == AfterTheDrizzle.GROUP_DRINK)
        {
            for (Fluid fluid : FluidTags.getCollection().getOrCreate(new ResourceLocation("afterthedrizzle:drink")).getAllElements())
            {
                ItemStack itemStack = new ItemStack(this);
                CompoundNBT fluidTag = new CompoundNBT();
                new FluidStack(fluid, CAPACITY).writeToNBT(fluidTag);
                itemStack.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
                items.add(itemStack);
            }
        }
        else if (group == AfterTheDrizzle.GROUP_CORE)
        {
            items.add(new ItemStack(this));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getChildTag(FLUID_NBT_KEY) != null)
        {
            FluidUtil.getFluidHandler(stack).ifPresent(f ->
                    tooltip.add(f.getFluidInTank(0).getDisplayName().appendText(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), CAPACITY)).applyTextStyle(TextFormatting.GRAY)));
        }
    }
}
