package roito.afterthedrizzle.common.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.PacketDistributor;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.environment.ApparentTemperature;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.environment.Temperature;
import roito.afterthedrizzle.common.item.IItemWithTemperature;
import roito.afterthedrizzle.common.network.PlayerTemperatureMessage;
import roito.afterthedrizzle.common.network.SimpleNetworkHandler;
import roito.afterthedrizzle.common.potion.EffectsRegistry;

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
            Humidity humidity = getActualHumidity(Humidity.getHumid(biome.getDownfall(), temp), world, pos);
            Fluid f = world.getFluidState(pos).getFluid();

            if (f != Fluids.EMPTY)
            {
                // 浸没于水中时，初步体感倾向温度等于水温。
                adjust(getFluidApparentTemp(f.getAttributes().getTemperature()), t, player);
            }
            else
            {
                ApparentTemperature ATemp = getTemperatureWithPoint(getTemperatureByLight(getTemperatureByHeight(temp, humidity, world, pos), world, pos), player);
                adjust((int) (ATemp.getMiddle() * humidity.getCoefficient()), t, player);
            }
            applyEffects(player, world, t.getApparentTemperature());
        });
    }

    public static ApparentTemperature getTemperatureWithPoint(ApparentTemperature temp, PlayerEntity player)
    {
        int id = temp.getIndex();
        if (id <= 2)
        {
            int point = getResistancePoint(player, "Cold");
            return ApparentTemperature.values()[Math.min(3, id + point)];
        }
        else if (id >= 5)
        {
            int point = getResistancePoint(player, "Heat");
            return ApparentTemperature.values()[Math.max(4, id - point)];
        }
        else return temp;
    }

    public static ApparentTemperature getTemperatureByLight(ApparentTemperature temp, World world, BlockPos pos)
    {
        int light = world.getLightFor(LightType.BLOCK, pos);
        if (light >= 15)
        {
            if (temp.ordinal() <= 4)
            {
                return ApparentTemperature.HOT;
            }
        }
        else if (light >= 11)
        {
            if (temp.ordinal() <= 3)
            {
                return ApparentTemperature.WARM;
            }
        }
        else if (light >= 8)
        {
            if (temp.ordinal() <= 2)
            {
                return ApparentTemperature.COOL;
            }
        }
        return temp;
    }

    /*
     * 根据玩家所处的高度决定温度（主世界地底挖矿）。
     */
    public static ApparentTemperature getTemperatureByHeight(float temp, Humidity humidity, World world, BlockPos pos)
    {
        if (world.getDimension().getType() == DimensionType.OVERWORLD && !world.canBlockSeeSky(pos))
        {
            if (pos.getY() <= 25)
            {
                return ApparentTemperature.WARM;
            }
            else if (pos.getY() <= 45)
            {
                return ApparentTemperature.COOL;
            }
        }
        float apparent = temp;
        if (world.getDimension().getType().hasSkyLight())
        {
            apparent = getEnvOriginTemp(temp, humidity, world.getDayTime(), world.isRaining());
        }
        return ApparentTemperature.getApparentTemp(Temperature.getTemperatureLevel(apparent));
    }

    /*
     * 计算玩家所处的环境的实际湿度，若在雨中，+1。
     */
    public static Humidity getActualHumidity(Humidity in, World world, BlockPos pos)
    {
        if (world.isRainingAt(pos))
        {
            int id = in.ordinal() == 4 ? 4 : in.getId();
            return Humidity.values()[id];
        }
        return in;
    }

    /*
     * 计算玩家所处的环境随时间变化的原始气温。
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

    public static int getResistancePoint(PlayerEntity player, String type)
    {
        int point = 0;
        for (ItemStack armor : player.getArmorInventoryList())
        {
            if (armor.getOrCreateTag().getString("Resistance").equals(type))
            {
                point++;
            }
        }
        for (int i = 0; i < 9; i++)
        {
            Item hotbar = player.inventory.getStackInSlot(i).getItem();
            if (hotbar instanceof IItemWithTemperature && ((IItemWithTemperature) hotbar).getResistanceType().equals(type))
            {
                if (!((IItemWithTemperature) hotbar).shouldHeld())
                {
                    point += ((IItemWithTemperature) hotbar).getResistancePoint();
                }
                else if (player.getHeldItemMainhand().getItem().equals(hotbar))
                {
                    point += ((IItemWithTemperature) hotbar).getResistancePoint();
                }
            }
        }
        Item hotbar = player.getHeldItemOffhand().getItem();
        if (hotbar instanceof IItemWithTemperature && ((IItemWithTemperature) hotbar).getResistanceType().equals(type))
        {
            point += ((IItemWithTemperature) hotbar).getResistancePoint();
        }

        if (type.equals("Cold") && player.getActivePotionEffect(EffectsRegistry.EXCITEMENT) != null)
        {
            point += 2;
        }
        if (type.equals("Heat") && player.getActivePotionEffect(Effects.FIRE_RESISTANCE) != null)
        {
            point += 4;
        }
        return Math.min(2, point / 2);
    }

    public static void adjust(int env, CapabilityPlayerTemperature.Data t, ServerPlayerEntity player)
    {
        if (env > t.getTemperature())
        {
            t.addPlayerTemperature(1);
        }
        else
        {
            t.addPlayerTemperature(-1);
        }
        SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PlayerTemperatureMessage(t.getTemperature()));
    }

    public static void applyEffects(PlayerEntity player, World world, ApparentTemperature temp)
    {
        if (!player.isCreative() && !player.isSpectator())
        {
            int ticks = Math.toIntExact(world.getDayTime() % 24000);
            int id = temp.getIndex();
            if (id <= 2)
            {
                int tier = 2 - id;
                if (player.getActivePotionEffect(Effects.HUNGER) == null)
                {
                    player.addPotionEffect(new EffectInstance(Effects.HUNGER, 400, tier));
                }
                if (player.getActivePotionEffect(Effects.SLOWNESS) == null)
                {
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, tier));
                }
                if (id <= 1)
                {
                    if (ticks % 80 == 0)
                    {
                        player.attackEntityFrom(FROST, 4.0F);
                    }
                }
            }
            else if (id >= 5)
            {
                int tier = id - 5;
                if (ticks % 600 == 0)
                {
                    player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 400));
                }
                if (player.getActivePotionEffect(Effects.SLOWNESS) == null)
                {
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, tier));
                }
                if (id >= 6)
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
