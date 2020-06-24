package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.config.CommonConfig;
import roito.afterthedrizzle.common.environment.weather.DailyWeatherData;
import roito.afterthedrizzle.common.environment.weather.WeatherType;
import roito.afterthedrizzle.common.item.ItemsRegistry;

import java.util.List;

public class InstrumentShelterBlock extends NormalBlock
{
    private static final BooleanProperty THERMOMETER = BooleanProperty.create("thermometer");
    private static final BooleanProperty RAIN_GAUGE = BooleanProperty.create("rain_gauge");
    private static final BooleanProperty HYGROMETER = BooleanProperty.create("hygrometer");

    public InstrumentShelterBlock()
    {
        super("instrument_shelter", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(THERMOMETER, false).with(RAIN_GAUGE, false).with(HYGROMETER, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (player.getHeldItem(handIn).getItem() == ItemsRegistry.THERMOMETER && !state.get(THERMOMETER))
        {
            player.getHeldItem(handIn).shrink(1);
            worldIn.setBlockState(pos, state.with(THERMOMETER, true));
            return ActionResultType.SUCCESS;
        }
        else if (player.getHeldItem(handIn).getItem() == ItemsRegistry.RAIN_GAUGE && !state.get(RAIN_GAUGE))
        {
            player.getHeldItem(handIn).shrink(1);
            worldIn.setBlockState(pos, state.with(RAIN_GAUGE, true));
            return ActionResultType.SUCCESS;
        }
        else if (player.getHeldItem(handIn).getItem() == ItemsRegistry.HYGROMETER && !state.get(HYGROMETER))
        {
            player.getHeldItem(handIn).shrink(1);
            worldIn.setBlockState(pos, state.with(HYGROMETER, true));
            return ActionResultType.SUCCESS;
        }
        else if (!worldIn.isRemote)
        {
            if (!CommonConfig.Weather.enable.get())
            {
                player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.disable"));
                return ActionResultType.SUCCESS;
            }
            boolean forecast = state.get(THERMOMETER) && state.get(HYGROMETER);
            boolean rain = state.get(RAIN_GAUGE);
            return worldIn.getCapability(CapabilityWorldWeather.WORLD_WEATHER).map(data ->
            {
                if (forecast)
                {
                    player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.start"));
                    List<WeatherType> today = data.getCurrentDay().getWeatherList();
                    TranslationTextComponent current = DailyWeatherData.getMainWeather(today, Math.toIntExact(worldIn.getDayTime() % 24000) / 1000).getTranslation();
                    player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.current", current));

                    TranslationTextComponent morning = DailyWeatherData.getMainWeather(today, 0).getTranslation();
                    TranslationTextComponent afternoon = DailyWeatherData.getMainWeather(today, 6).getTranslation();
                    TranslationTextComponent night = DailyWeatherData.getMainWeather(today, 12).getTranslation();
                    player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.today", morning, afternoon, night));

                    List<WeatherType> tomorrow = data.getWeatherData(1).getWeatherList();
                    morning = DailyWeatherData.getMainWeather(tomorrow, 0).getTranslation();
                    afternoon = DailyWeatherData.getMainWeather(tomorrow, 6).getTranslation();
                    night = DailyWeatherData.getMainWeather(tomorrow, 12).getTranslation();
                    player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.tomorrow", morning, afternoon, night));
                    player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.end"));
                }
                if (rain && data.getCurrentWeather().isRainy())
                {
                    int index = Math.toIntExact(worldIn.getDayTime() + 1000 % 24000) / 1000;

                }
                if (!forecast && !rain)
                {
                    player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.not_enough"));
                }
                return ActionResultType.SUCCESS;
            }).orElseGet(() ->
            {
                player.sendMessage(new TranslationTextComponent("info.afterthedrizzle.environment.weather.forecast.disable"));
                return ActionResultType.SUCCESS;
            });
        }
        else return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(THERMOMETER, RAIN_GAUGE, HYGROMETER);
    }
}
