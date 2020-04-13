package roito.afterthedrizzle.common.item;

import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.item.food.FoodItem;
import roito.afterthedrizzle.common.item.food.NormalFoods;
import roito.afterthedrizzle.registry.RegistryModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ItemsRegistry extends RegistryModule
{
    // FOOD 食物
    public static final Item DRIED_BEETROOT = new FoodItem("dried_beetroot", NormalFoods.DRIED_BEETROOT);
    public static final Item DRIED_CARROT = new FoodItem("dried_carrot", NormalFoods.DRIED_CARROT);
    public static final Item BEEF_JERKY = new FoodItem("beef_jerky", NormalFoods.BEEF_JERKY);
    public static final Item PORK_JERKY = new FoodItem("pork_jerky", NormalFoods.PORK_JERKY);
    public static final Item CHICKEN_JERKY = new FoodItem("chicken_jerky", NormalFoods.CHICKEN_JERKY);
    public static final Item RABBIT_JERKY = new FoodItem("rabbit_jerky", NormalFoods.RABBIT_JERKY);
    public static final Item MUTTON_JERKY = new FoodItem("mutton_jerky", NormalFoods.MUTTON_JERKY);

    // DRINK & INGREDIENTS 饮品与原料
    public static final Item PORCELAIN_CUP = new ItemFluidContainer(new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK), 250)
    {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
        {
            return super.initCapabilities(new ItemStack(ItemsRegistry.PORCELAIN_CUP_DRINK), nbt);
        }
    }.setRegistryName("porcelain_cup");
    public static final Item BOTTLE = new ItemFluidContainer(new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK), 500)
    {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
        {
            return super.initCapabilities(new ItemStack(ItemsRegistry.BOTTLE_DRINK), nbt);
        }
    }.setRegistryName("bottle");
    public static final Item PORCELAIN_CUP_DRINK = new CupDrinkItem(250, PORCELAIN_CUP, "porcelain_cup_drink");
    public static final Item BOTTLE_DRINK = new CupDrinkItem(500, BOTTLE, "bottle_drink");

    public static final Item TEA_SEEDS = new BlockNamedItem(BlocksRegistry.TEA_PLANT, new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK)).setRegistryName("tea_seeds");
    public static final Item TEA_LEAVES = new NormalItem("tea_leaves", new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK));
    public static final Item GREEN_TEA_LEAVES = new NormalItem("green_tea_leaves", new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK));
    public static final Item BLACK_TEA_LEAVES = new NormalItem("black_tea_leaves", new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK));
    public static final Item EMPTY_TEA_BAG = new NormalItem("empty_tea_bag", new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK));
    public static final Item GREEN_TEA_BAG = new NormalItem("green_tea_bag", new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK));
    public static final Item BLACK_TEA_BAG = new NormalItem("black_tea_bag", new Item.Properties().group(AfterTheDrizzle.GROUP_DRINK));

    // ENVIRONMENT 环境
    public static final Item THERMOMETER = new NormalItem("thermometer", new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));
    public static final Item RAIN_GAUGE = new NormalItem("rain_gauge", new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));
    public static final Item HYGROMETER = new NormalItem("hygrometer", new Item.Properties().group(AfterTheDrizzle.GROUP_CRAFT));

    // MISC 杂项
    public static final Item ASH = new AshItem();
}
