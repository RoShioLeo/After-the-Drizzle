package cloud.lemonslice.afterthedrizzle.common.handler;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.block.TeaPlantBlock;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilitySolarTermTime;
import cloud.lemonslice.afterthedrizzle.common.capability.CapabilityWorldWeather;
import cloud.lemonslice.afterthedrizzle.common.config.ServerConfig;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropHumidityInfo;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropInfoManager;
import cloud.lemonslice.afterthedrizzle.common.environment.crop.CropSeasonInfo;
import cloud.lemonslice.afterthedrizzle.common.environment.temperature.PlayerTemperatureHelper;
import cloud.lemonslice.afterthedrizzle.common.item.ItemsRegistry;
import cloud.lemonslice.afterthedrizzle.common.network.PlayerTemperatureMessage;
import cloud.lemonslice.afterthedrizzle.common.network.SimpleNetworkHandler;
import cloud.lemonslice.afterthedrizzle.common.network.SolarTermsMessage;
import cloud.lemonslice.afterthedrizzle.common.network.WeatherChangeMessage;
import cloud.lemonslice.afterthedrizzle.common.potion.EffectsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class CommonEventHandler
{
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        if (event.getEntityLiving().isServerWorld())
        {
            EffectInstance effect = event.getEntityLiving().getActivePotionEffect(EffectsRegistry.AGILITY);
            if (effect != null)
            {
                if (event.getEntityLiving().getRNG().nextFloat() < ((effect.getAmplifier() + 1) * 0.15F + 0.05F))
                {
                    event.setCanceled(true);
                }
            }
            if (event.getEntityLiving().getActivePotionEffect(EffectsRegistry.DEFENCE) != null)
            {
                event.getEntityLiving().playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 1.0F);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        if (event.getEntityLiving().isServerWorld())
        {
            EffectInstance effect = event.getEntityLiving().getActivePotionEffect(EffectsRegistry.LIFE_DRAIN);
            if (effect != null)
            {
                int level = effect.getAmplifier() + 1;
                float r = event.getEntityLiving().getRNG().nextFloat();
                float health = event.getEntityLiving().getHealth();
                if (health < event.getEntityLiving().getMaxHealth() && r <= level * 0.2F)
                {
                    event.getEntityLiving().heal(4.0F - 6.0F / (level + 1.0F));
                }
            }
        }
        if (event.getEntityLiving().getActivePotionEffect(EffectsRegistry.DEFENCE) != null)
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            EffectInstance effect = event.getPlayer().getActivePotionEffect(EffectsRegistry.EXCITEMENT);
            if (effect != null)
            {
                event.setResult(PlayerEntity.SleepResult.OTHER_PROBLEM);
                event.getPlayer().sendStatusMessage(new TranslationTextComponent("info.afterthedrizzle.bed.excited"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onUseBoneMeal(BonemealEvent event)
    {
        if (!ServerConfig.Agriculture.canUseBoneMeal.get())
        {
            if (event.getBlock().getBlock() instanceof TeaPlantBlock)
            {
                if (event.getBlock().get(TeaPlantBlock.AGE) == 7 || event.getBlock().get(TeaPlantBlock.AGE) == 6)
                {
                    event.setResult(Event.Result.DEFAULT);
                }
                else
                {
                    event.setCanceled(true);
                }
            }
            else if (event.getBlock().getBlock() instanceof IPlantable && ((IPlantable) event.getBlock().getBlock()).getPlantType(event.getWorld(), event.getPos()) == PlantType.Crop)
            {
                event.setCanceled(true);
            }
            else
                event.setResult(Event.Result.DEFAULT);
        }
    }

    @SubscribeEvent
    public static void onNeighborChanged(BlockEvent.NeighborNotifyEvent event)
    {
        if (ServerConfig.Others.woodDropsAshWhenBurning.get())
            event.getNotifiedSides().forEach(direction ->
            {
                if (event.getWorld().getBlockState(event.getPos()).isFlammable(event.getWorld(), event.getPos(), direction) && event.getWorld().getBlockState(event.getPos().offset(direction)).getBlock() instanceof FireBlock)
                {
                    Block.spawnAsEntity(event.getWorld().getWorld(), event.getPos(), new ItemStack(ItemsRegistry.ASH));
                }
            });
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
//        PlayerEntity player = event.getPlayer();
//        ItemStack held = player.getHeldItem(event.getHand());
//        if (!held.isEmpty())
//        {
//            BlockState state = event.getWorld().getBlockState(event.getPos());
//            if (state.isIn(Tags.Blocks.DIRT) && held.getItem().getToolTypes(held).contains(ToolType.SHOVEL))
//            {
//                event.getWorld().setBlockState(event.getPos(), BlocksRegistry.DIRT_AQUEDUCT.getDefaultState());
//                event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                held.damageItem(1, player, p -> p.sendBreakAnimation(event.getHand()));
//                event.setUseItem(Event.Result.ALLOW);
//            }
//        }
    }

    @SubscribeEvent
    public static void onHoeUse(UseHoeEvent event)
    {
//        World world = event.getContext().getWorld();
//        BlockPos pos = event.getContext().getPos();
//        if (world.getBlockState(pos).getBlock() instanceof FarmlandBlock)
//        {
//            world.setBlockState(pos, BlocksRegistry.PADDY_FIELD.getDefaultState());
//            world.playSound(event.getPlayer(), pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
//            event.setResult(Event.Result.ALLOW);
//        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if (ServerConfig.Temperature.enable.get() && event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
        {
            event.addCapability(new ResourceLocation(AfterTheDrizzle.MODID, "player_temperature"), new CapabilityPlayerTemperature.Provider());
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<World> event)
    {
        if (ServerConfig.Season.enable.get() && event.getObject().getDimension().getType().equals(DimensionType.OVERWORLD))
        {
            event.addCapability(new ResourceLocation(AfterTheDrizzle.MODID, "world_solar_terms"), new CapabilitySolarTermTime.Provider());
        }
        event.addCapability(new ResourceLocation(AfterTheDrizzle.MODID, "world_weather"), new CapabilityWorldWeather.Provider());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity && !(event.getPlayer() instanceof FakePlayer))
        {
            if (ServerConfig.Temperature.enable.get())
            {
                event.getPlayer().getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerTemperatureMessage(t.getTemperature(), t.getHotterOrColder())));
            }
            if (ServerConfig.Season.enable.get())
            {
                event.getPlayer().getEntityWorld().getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new SolarTermsMessage(t.getSolarTermsDay())));
            }
            if (ServerConfig.Weather.enable.get())
            {
                event.getPlayer().getEntityWorld().getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new WeatherChangeMessage(data.getCurrentDay().getCurrentWeather(Math.toIntExact(((ServerPlayerEntity) event.getPlayer()).getServerWorld().getGameTime() % 24000)))));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayTick(TickEvent.PlayerTickEvent event)
    {
        PlayerEntity player = event.player;
        if (ServerConfig.Temperature.enable.get() && event.phase == TickEvent.Phase.START && player instanceof ServerPlayerEntity && !(player instanceof FakePlayer) && player.getEntityWorld().getGameTime() % 50 == 0)
        {
            PlayerTemperatureHelper.adjustPlayerTemperature((ServerPlayerEntity) player, player.getEntityWorld(), player.getPosition());
        }
    }

    @SubscribeEvent
    public static void addTooltips(ItemTooltipEvent event)
    {
        if (ServerConfig.Season.enable.get())
        {
            addCropTooltips(event);
        }
        addArmorTempTooltips(event);
    }

    public static void addArmorTempTooltips(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof ArmorItem)
        {
            if (event.getItemStack().getOrCreateTag().getString("Resistance").equals("Cold"))
            {
                event.getToolTip().add(1, new TranslationTextComponent("info.afterthedrizzle.environment.temperature.cold_resistance").applyTextStyle(TextFormatting.ITALIC).applyTextStyle(TextFormatting.GRAY));
            }
            else if (event.getItemStack().getOrCreateTag().getString("Resistance").equals("Heat"))
            {
                event.getToolTip().add(1, new TranslationTextComponent("info.afterthedrizzle.environment.temperature.heat_resistance").applyTextStyle(TextFormatting.ITALIC).applyTextStyle(TextFormatting.GRAY));
            }
        }
    }

    public static void addCropTooltips(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof BlockItem)
        {
            if (CropInfoManager.getHumidityCrops().contains(((BlockItem) event.getItemStack().getItem()).getBlock()))
            {
                CropHumidityInfo info = CropInfoManager.getHumidityInfo(((BlockItem) event.getItemStack().getItem()).getBlock());
                event.getToolTip().addAll(info.getTooltip());
            }
            if (CropInfoManager.getSeasonCrops().contains(((BlockItem) event.getItemStack().getItem()).getBlock()))
            {
                CropSeasonInfo info = CropInfoManager.getSeasonInfo(((BlockItem) event.getItemStack().getItem()).getBlock());
                event.getToolTip().addAll(info.getTooltip());
            }
        }
    }
}
