package roito.afterthedrizzle.common;

import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
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
import roito.afterthedrizzle.common.config.NormalConfig;
import roito.afterthedrizzle.common.environment.ApparentTemperature;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.environment.Temperature;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.network.PlayerTemperatureMessage;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.potion.EffectsRegistry;
import roito.afterthedrizzle.helper.EnvHelper;

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
            effect = event.getEntityLiving().getActivePotionEffect(EffectsRegistry.DEFENCE);
            if (effect != null)
            {
                event.getEntityLiving().playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 1.0F);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event)
    {
        EffectInstance effect2 = event.getEntityLiving().getActivePotionEffect(EffectsRegistry.DEFENCE);
        if (effect2 != null)
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
        if (NormalConfig.canUseBoneMeal.get())
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
            else event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onNeighborChanged(BlockEvent.NeighborNotifyEvent event)
    {
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
        if (event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(new ResourceLocation(AfterTheDrizzle.MODID, "player_temperature"), new CapabilityPlayerTemperature.Provider());
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            event.getPlayer().getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerTemperatureMessage(t.getPlayerTemperature())));
        }
    }

    @SubscribeEvent
    public static void onPlayTick(TickEvent.PlayerTickEvent event)
    {
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.START && player instanceof ServerPlayerEntity && player.getEntityWorld().getDayTime() % 50 == 0)
        {
            player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t ->
            {
                Biome biome = player.getEntityWorld().getBiomeBody(player.getPosition());
                float temp = biome.getTemperature(player.getPosition());
                Humidity humidity = Humidity.getHumid(biome.getDownfall(), temp);
                Fluid f = player.getEntityWorld().getFluidState(player.getPosition()).getFluid();
                if (player.getEntityWorld().isRainingAt(player.getPosition()))
                {
                    int id = humidity.ordinal() == 4 ? 4 : humidity.getId();
                    humidity = Humidity.values()[id];
                }
                if (f != Fluids.EMPTY)
                {
                    int ATemp = EnvHelper.getFluidApparentTemp(f.getAttributes().getTemperature());

                    if (ATemp > t.getTemperature())
                    {
                        t.addPlayerTemperature(1);
                    }
                    else
                    {
                        t.addPlayerTemperature(-1);
                    }
                }
                else
                {
                    ApparentTemperature ATemp;
                    if (player.posY < 40 && player.getEntityWorld().canBlockSeeSky(player.getPosition()))
                    {
                        ATemp = ApparentTemperature.COOL;
                    }
                    else
                    {
                        float apparent = EnvHelper.getEnvDailyTemp(temp, humidity, player.getEntityWorld().getDayTime(), player.getEntityWorld().isRaining());
                        ATemp = ApparentTemperature.getApparentTemp(Temperature.getTemperatureLevel(apparent));
                    }
                    if (player.getEntityWorld().getLightFor(LightType.BLOCK, player.getPosition()) >= 8)
                    {
                        if (ATemp.ordinal() <= 3)
                        {
                            ATemp = ApparentTemperature.WARM;
                        }
                    }
                    if (ATemp.getMiddle() > t.getTemperature())
                    {
                        t.addPlayerTemperature(1);
                    }
                    else
                    {
                        t.addPlayerTemperature(-1);
                    }
                }
                SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerTemperatureMessage(t.getPlayerTemperature()));
            });
        }
    }
}
