package roito.afterthedrizzle.common.fluid;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.BlocksRegistry;

public class FluidsRegistry
{
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, AfterTheDrizzle.MODID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AfterTheDrizzle.MODID);

    public static final ResourceLocation WATER_STILL_TEXTURE = new ResourceLocation("minecraft:block/water_still");
    public static final ResourceLocation WATER_FLOW_TEXTURE = new ResourceLocation("minecraft:block/water_flow");

    public static final RegistryObject<FlowingFluid> BOILING_WATER_STILL = FLUIDS.register("boiling_water", () -> new ForgeFlowingFluid.Source(FluidsRegistry.BOILING_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BOILING_WATER_FLOW = FLUIDS.register("boiling_water_flowing", () -> new ForgeFlowingFluid.Flowing(FluidsRegistry.BOILING_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HOT_WATER_80_STILL = FLUIDS.register("hot_water_80", () -> new ForgeFlowingFluid.Source(FluidsRegistry.HOT_WATER_80_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HOT_WATER_80_FLOW = FLUIDS.register("hot_water_80_flowing", () -> new ForgeFlowingFluid.Flowing(FluidsRegistry.HOT_WATER_80_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HOT_WATER_60_STILL = FLUIDS.register("hot_water_60", () -> new ForgeFlowingFluid.Source(FluidsRegistry.HOT_WATER_60_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HOT_WATER_60_FLOW = FLUIDS.register("hot_water_60_flowing", () -> new ForgeFlowingFluid.Flowing(FluidsRegistry.HOT_WATER_60_PROPERTIES));
    public static final RegistryObject<FlowingFluid> WARM_WATER_STILL = FLUIDS.register("warm_water", () -> new ForgeFlowingFluid.Source(FluidsRegistry.WARM_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> WARM_WATER_FLOW = FLUIDS.register("warm_water_flowing", () -> new ForgeFlowingFluid.Flowing(FluidsRegistry.WARM_WATER_PROPERTIES));

    public static final RegistryObject<BucketItem> BOILING_WATER_BUCKET = ITEMS.register("boiling_water_bucket", () -> new BucketItem(FluidsRegistry.BOILING_WATER_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(AfterTheDrizzle.GROUP_CRAFT)));
    public static final RegistryObject<BucketItem> HOT_WATER_80_BUCKET = ITEMS.register("hot_water_80_bucket", () -> new BucketItem(FluidsRegistry.HOT_WATER_80_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(AfterTheDrizzle.GROUP_CRAFT)));
    public static final RegistryObject<BucketItem> HOT_WATER_60_BUCKET = ITEMS.register("hot_water_60_bucket", () -> new BucketItem(FluidsRegistry.HOT_WATER_60_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(AfterTheDrizzle.GROUP_CRAFT)));
    public static final RegistryObject<BucketItem> WARM_WATER_BUCKET = ITEMS.register("warm_water_bucket", () -> new BucketItem(FluidsRegistry.WARM_WATER_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(AfterTheDrizzle.GROUP_CRAFT)));

    public static final ForgeFlowingFluid.Properties BOILING_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(BOILING_WATER_STILL, BOILING_WATER_FLOW, FluidAttributes.builder(WATER_STILL_TEXTURE, WATER_FLOW_TEXTURE).color(0xFF4989E3).temperature(373)).bucket(BOILING_WATER_BUCKET).block(() -> BlocksRegistry.BOILING_WATER).explosionResistance(100F);
    public static final ForgeFlowingFluid.Properties HOT_WATER_80_PROPERTIES = new ForgeFlowingFluid.Properties(HOT_WATER_80_STILL, HOT_WATER_80_FLOW, FluidAttributes.builder(WATER_STILL_TEXTURE, WATER_FLOW_TEXTURE).color(0xFF4989E3).temperature(353)).bucket(HOT_WATER_80_BUCKET).block(() -> BlocksRegistry.HOT_WATER_80).explosionResistance(100F);
    public static final ForgeFlowingFluid.Properties HOT_WATER_60_PROPERTIES = new ForgeFlowingFluid.Properties(HOT_WATER_60_STILL, HOT_WATER_60_FLOW, FluidAttributes.builder(WATER_STILL_TEXTURE, WATER_FLOW_TEXTURE).color(0xFF4989E3).temperature(333)).bucket(HOT_WATER_60_BUCKET).block(() -> BlocksRegistry.HOT_WATER_60).explosionResistance(100F);
    public static final ForgeFlowingFluid.Properties WARM_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(WARM_WATER_STILL, WARM_WATER_FLOW, FluidAttributes.builder(WATER_STILL_TEXTURE, WATER_FLOW_TEXTURE).color(0xFF4989E3).temperature(308)).bucket(WARM_WATER_BUCKET).block(() -> BlocksRegistry.WARM_WATER).explosionResistance(100F);
}
