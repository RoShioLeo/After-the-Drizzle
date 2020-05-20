package roito.afterthedrizzle.common;

import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.TeaPlantBlock;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.config.CommonConfig;
import roito.afterthedrizzle.common.environment.temperature.PlayerTemperatureHandler;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.network.PlayerTemperatureMessage;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.potion.EffectsRegistry;

import static roito.afterthedrizzle.common.block.TeaPlantBlock.AGE;

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
        if (!CommonConfig.Agriculture.canUseBoneMeal.get())
        {
            if (event.getBlock().getBlock() instanceof TeaPlantBlock)
            {
                if (event.getBlock().get(AGE) == 7 || event.getBlock().get(AGE) == 6)
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
        if (CommonConfig.Others.woodDropsAshWhenBurning.get())
            event.getNotifiedSides().forEach(direction ->
            {
                if (event.getWorld().getBlockState(event.getPos()).isFlammable(event.getWorld(), event.getPos(), direction) && event.getWorld().getBlockState(event.getPos().offset(direction)).getBlock() instanceof FireBlock)
                {
                    Block.spawnAsEntity(event.getWorld().getWorld(), event.getPos(), new ItemStack(ItemsRegistry.ASH));
                }
            });
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if (CommonConfig.Temperature.enable.get() && event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
        {
            event.addCapability(new ResourceLocation(AfterTheDrizzle.MODID, "player_temperature"), new CapabilityPlayerTemperature.Provider());
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (CommonConfig.Temperature.enable.get() && event.getPlayer() instanceof ServerPlayerEntity && !(event.getPlayer() instanceof FakePlayer))
        {
            event.getPlayer().getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerTemperatureMessage(t.getTemperature(), t.getHotterOrColder())));
        }
    }

    @SubscribeEvent
    public static void onPlayTick(TickEvent.PlayerTickEvent event)
    {
        PlayerEntity player = event.player;
        if (CommonConfig.Temperature.enable.get() && event.phase == TickEvent.Phase.START && player instanceof ServerPlayerEntity && !(player instanceof FakePlayer) && player.getEntityWorld().getDayTime() % 50 == 0)
        {
            PlayerTemperatureHandler.adjustPlayerTemperature((ServerPlayerEntity) player, player.getEntityWorld(), player.getPosition());
        }
    }
}
