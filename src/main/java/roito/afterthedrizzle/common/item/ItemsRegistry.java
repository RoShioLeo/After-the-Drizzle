package roito.afterthedrizzle.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.item.food.FoodItem;
import roito.afterthedrizzle.common.item.food.NormalFoods;
import roito.afterthedrizzle.registry.RegistryModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static roito.afterthedrizzle.common.item.NormalItem.getNormalItemProperties;
import static roito.afterthedrizzle.common.item.NormalItem.getTeaLeavesItemProperties;

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

    // ENVIRONMENT 环境
    public static final Item THERMOMETER = new NormalItem("thermometer", getNormalItemProperties());
    public static final Item RAIN_GAUGE = new NormalItem("rain_gauge");
    public static final Item HYGROMETER = new NormalItem("hygrometer");

    // MISC 杂项
    public static final Item BAMBOO_PLANK = new NormalItem("bamboo_plank");
    public static final Item ASH = new FertilizerItem("ash");
    public static final Item TEA_RESIDUES = new NormalItem("tea_residues");
    public static final Item INSULATING_LAYER = new NormalItem("insulating_layer");
    public static final Item WATER_BAG = new NormalItem("water_bag", getNormalItemProperties().maxStackSize(1));
    public static final Item ICE_WATER_BAG = new WaterBagItem("ice_water_bag", getNormalItemProperties().containerItem(WATER_BAG).maxDamage(60), 4, "Heat");
    public static final Item HOT_WATER_BAG = new WaterBagItem("hot_water_bag", getNormalItemProperties().containerItem(WATER_BAG).maxDamage(60), 4, "Cold");
    public static final Item HANDWARMER = new NormalItem("handwarmer", getNormalItemProperties().maxStackSize(1))
    {
        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
        {
            tooltip.add(new TranslationTextComponent("info.afterthedrizzle.tooltip.handwarmer").applyTextStyle(TextFormatting.GRAY));
        }
    };
    public static final Item LIT_HANDWARMER = new LitHandWarmerItem();


    // INGREDIENTS 原料
    public static final Item TEA_SEEDS = new BlockNamedItem(BlocksRegistry.TEA_PLANT, getNormalItemProperties()).setRegistryName("tea_seeds");
    public static final Item TEA_LEAVES = new NormalItem("tea_leaves");
    public static final Item GREEN_TEA_LEAVES = new NormalItem("green_tea_leaves", getTeaLeavesItemProperties());
    public static final Item BLACK_TEA_LEAVES = new NormalItem("black_tea_leaves", getTeaLeavesItemProperties());
    public static final Item WHITE_TEA_LEAVES = new NormalItem("white_tea_leaves", getTeaLeavesItemProperties());
    public static final Item EMPTY_TEA_BAG = new NormalItem("empty_tea_bag", getTeaLeavesItemProperties());
    public static final Item GREEN_TEA_BAG = new NormalItem("green_tea_bag", getTeaLeavesItemProperties());
    public static final Item BLACK_TEA_BAG = new NormalItem("black_tea_bag", getTeaLeavesItemProperties());

    // DRINK 饮品
    public static final Item CLAY_CUP = new NormalItem("clay_cup");
    public static final Item PORCELAIN_CUP = new ItemFluidContainer(getNormalItemProperties(), 250)
    {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
        {
            return super.initCapabilities(new ItemStack(ItemsRegistry.PORCELAIN_CUP_DRINK), nbt);
        }
    }.setRegistryName("porcelain_cup");
    public static final Item BOTTLE = new ItemFluidContainer(getNormalItemProperties(), 500)
    {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
        {
            return super.initCapabilities(new ItemStack(ItemsRegistry.BOTTLE_DRINK), nbt);
        }
    }.setRegistryName("bottle");
    public static final Item CLAY_TEAPOT = new NormalItem("clay_teapot");
    public static final Item PORCELAIN_TEAPOT = new TeapotItem();
    public static final Item PORCELAIN_CUP_DRINK = new CupDrinkItem(250, PORCELAIN_CUP, "porcelain_cup_drink");
    public static final Item BOTTLE_DRINK = new CupDrinkItem(500, BOTTLE, "bottle_drink");
}
