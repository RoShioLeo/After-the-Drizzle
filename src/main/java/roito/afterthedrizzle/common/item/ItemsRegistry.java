package roito.afterthedrizzle.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.food.FoodItem;
import roito.afterthedrizzle.common.item.food.NormalFoods;
import roito.afterthedrizzle.registry.RegistryModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ItemsRegistry extends RegistryModule
{
    public static final Item DRIED_BEETROOT = new FoodItem("dried_beetroot", NormalFoods.DRIED_BEETROOT);
    public static final Item DRIED_CARROT = new FoodItem("dried_carrot", NormalFoods.DRIED_CARROT);
    public static final Item BEEF_JERKY = new FoodItem("beef_jerky", NormalFoods.BEEF_JERKY);
    public static final Item PORK_JERKY = new FoodItem("pork_jerky", NormalFoods.PORK_JERKY);
    public static final Item CHICKEN_JERKY = new FoodItem("chicken_jerky", NormalFoods.CHICKEN_JERKY);
    public static final Item RABBIT_JERKY = new FoodItem("rabbit_jerky", NormalFoods.RABBIT_JERKY);
    public static final Item MUTTON_JERKY = new FoodItem("mutton_jerky", NormalFoods.MUTTON_JERKY);

    public static final Item PORCELAIN_CUP_DRINK = new PorcelainCupDrinkItem(250);
    public static final Item PORCELAIN_CUP = new ItemFluidContainer(new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT), 250)
    {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
        {
            return super.initCapabilities(new ItemStack(ItemsRegistry.PORCELAIN_CUP_DRINK), nbt);
        }
    }.setRegistryName("porcelain_cup");

    public static final Item THERMOMETER = new NormalItem("thermometer", new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));
    public static final Item RAIN_GAUGE = new NormalItem("rain_gauge", new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));
    public static final Item HYGROMETER = new NormalItem("hygrometer", new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));

    public static final Item ASH = new AshItem();
}
