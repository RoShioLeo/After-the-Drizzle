package roito.afterthedrizzle.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import roito.afterthedrizzle.common.capability.CapabilityWorldWeather;
import roito.afterthedrizzle.common.environment.weather.WeatherType;

public class WeatherCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("changeweather").requires((source) -> source.hasPermissionLevel(2))
                .then(Commands.literal("set").then(Commands.argument("weather", IntegerArgumentType.integer()).executes(commandContext -> setWeather(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "weather"))))));
    }

    private static int getWeather(ServerWorld worldIn)
    {
        return worldIn.getCapability(CapabilityWorldWeather.WORLD_WEATHER).map(data -> data.getCurrentWeather().ordinal()).orElse(0);
    }

    public static int setWeather(CommandSource source, int weather)
    {
        for (ServerWorld serverworld : source.getServer().getWorlds())
        {
            serverworld.getCapability(CapabilityWorldWeather.WORLD_WEATHER).ifPresent(data -> data.setCurrentWeather(WeatherType.values()[weather]));
        }

        source.sendFeedback(new TranslationTextComponent("commands.afterthedrizzle.solar.set", weather), true);
        return getWeather(source.getWorld());
    }
}
