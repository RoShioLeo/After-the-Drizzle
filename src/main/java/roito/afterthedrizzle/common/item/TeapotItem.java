package roito.afterthedrizzle.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.AfterTheDrizzle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

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
                if (blockstate1.getBlock() instanceof IBucketPickupHandler)
                {
                    Fluid fluid = ((IBucketPickupHandler) blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
                    if (fluid != Fluids.EMPTY && fluid.isIn(FluidTags.WATER))
                    {
                        playerIn.addStat(Stats.ITEM_USED.get(this));

                        SoundEvent soundevent = SoundEvents.ITEM_BOTTLE_FILL;
                        playerIn.playSound(soundevent, 1.0F, 1.0F);

                        if (!playerIn.isCreative())
                        {
                            ItemStack itemStack1 = new ItemStack(ItemsRegistry.PORCELAIN_TEAPOT_DRINK);
                            CompoundNBT fluidTag = new CompoundNBT();
                            new FluidStack(fluid, capacity).writeToNBT(fluidTag);
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
}
