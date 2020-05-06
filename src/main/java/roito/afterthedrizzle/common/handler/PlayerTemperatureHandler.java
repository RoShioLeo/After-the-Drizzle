package roito.afterthedrizzle.common.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.PacketDistributor;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.environment.ApparentTemperature;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.environment.Temperature;
import roito.afterthedrizzle.common.network.PlayerTemperatureMessage;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;

public final class PlayerTemperatureHandler
{
    public static final DamageSource FROST = new DamageSource("frostbite").setDamageBypassesArmor();
    public static final DamageSource HEAT = new DamageSource("heatstroke").setDamageBypassesArmor().setFireDamage();

    public static void adjustPlayerTemperature(ServerPlayerEntity player, World world, BlockPos pos)
    {
        player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t ->
        {
            Biome biome = world.getBiomeBody(pos);
            float temp = biome.getTemperature(pos);
            Humidity humidity = Humidity.getHumid(biome.getDownfall(), temp);
            Fluid f = world.getFluidState(pos).getFluid();

            // 在雨中时，湿度等级升1。
            if (world.isRainingAt(pos))
            {
                int id = humidity.ordinal() == 4 ? 4 : humidity.getId();
                humidity = Humidity.values()[id];
            }

            if (f != Fluids.EMPTY)
            {
                // 浸没于水中时，初步体感倾向温度等于水温。
                int ATemp = getFluidApparentTemp(f.getAttributes().getTemperature());
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
                if (player.posY < 45 && !world.canBlockSeeSky(pos))
                {
                    // Y轴高度低于45，环境处于恒温状态。
                    ATemp = ApparentTemperature.COOL;
                }
                else
                {
                    // 获取初步体感倾向温度，末地与下界禁用气温变化。
                    float apparent = temp;
                    if (world.getDimension().getType().hasSkyLight())
                    {
                        apparent = getEnvOriginTemp(temp, humidity, world.getDayTime(), world.isRaining());
                    }
                    ATemp = ApparentTemperature.getApparentTemp(Temperature.getTemperatureLevel(apparent));
                }

                if (world.getLightFor(LightType.BLOCK, pos) >= 8)
                {
                    // 方块光照大于等于8时，如果初步体感倾向温度最低为WARM。
                    if (ATemp.ordinal() <= 3)
                    {
                        ATemp = ApparentTemperature.WARM;
                    }
                }

                // 根据实际体感倾向温度，调节玩家体感温度。
                if (ATemp.getMiddle() * humidity.getCoefficient() > t.getTemperature())
                {
                    t.addPlayerTemperature(1);
                }
                else
                {
                    t.addPlayerTemperature(-1);
                }
            }
            applyEffects(player, world, t.getApparentTemperature());
            // 发包更新
            SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PlayerTemperatureMessage(t.getTemperature()));
        });
    }

    /*
     *计算玩家所处的环境随时间变化的原始气温。
     */
    public static float getEnvOriginTemp(float biomeTemp, Humidity humidity, long ticks, boolean isRaining)
    {
        int t = Math.toIntExact(ticks / 100 % 240);
        int id = humidity.getId();
        // 雨天波动减半
        double sd = (6 - id) * (6 - id) * 0.02D * Math.abs((double) biomeTemp) * (isRaining ? 0.5D : 1);
        double standard = (double) biomeTemp - sd * sd;
        double actual = -4.0706867802E-14 * t * t * t * t * t * t - 6.6491872724E-11 * t * t * t * t * t + 5.5361084324E-08 * t * t * t * t - 1.2584165083E-05 * t * t * t + 8.4482896013E-04 * t * t + 9.6706499996E-03 * t - 7.3983503027E-01;
        return (float) (sd * actual + standard);
    }

    public static int getFluidApparentTemp(int k)
    {
        if (k < 273)
        {
            return ApparentTemperature.FREEZING.getMiddle();
        }
        else if (k < 288)
        {
            return ApparentTemperature.COLD.getMiddle();
        }
        else if (k < 303)
        {
            return ApparentTemperature.COOL.getMiddle();
        }
        else if (k < 318)
        {
            return ApparentTemperature.WARM.getMiddle();
        }
        else if (k < 333)
        {
            return ApparentTemperature.HOT.getMiddle();
        }
        else
        {
            return ApparentTemperature.HEAT.getMiddle();
        }
    }

    public static void applyEffects(PlayerEntity player, World world, ApparentTemperature temp)
    {
        if (!player.isCreative() && !player.isSpectator())
        {
            int ticks = Math.toIntExact(world.getDayTime() % 24000);
            if (temp.ordinal() == 2)
            {
                if (player.getActivePotionEffect(Effects.HUNGER) == null)
                {
                    player.addPotionEffect(new EffectInstance(Effects.HUNGER, 400, 1));
                }
                if (player.getActivePotionEffect(Effects.SLOWNESS) == null)
                {
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, 1));
                }
                if (temp.ordinal() <= 1)
                {
                    if (ticks % 100 == 0)
                    {
                        player.attackEntityFrom(FROST, 4.0F);
                    }
                }
            }
            else if (temp.ordinal() >= 5 && player.getActivePotionEffect(Effects.FIRE_RESISTANCE) == null)
            {
                if (ticks % 600 == 0)
                {
                    player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 400));
                }
                if (player.getActivePotionEffect(Effects.SLOWNESS) == null)
                {
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, 1));
                }
                if (temp.ordinal() >= 6)
                {
                    if (ticks % 100 == 0)
                    {
                        player.attackEntityFrom(HEAT, 4.0F);
                    }
                }
            }
        }
    }
}
