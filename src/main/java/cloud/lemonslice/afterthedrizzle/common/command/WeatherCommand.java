package cloud.lemonslice.afterthedrizzle.common.command;

import cloud.lemonslice.afterthedrizzle.common.capability.CapabilityWorldWeather;
import cloud.lemonslice.afterthedrizzle.common.environment.weather.WeatherType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class WeatherCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("instantweather").requires((source) -> source.hasPermissionLevel(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("clear").executes((commandContext) -> setWeather(commandContext.getSource(), 1)))
                        .then(Commands.literal("overcast").executes((commandContext) -> setWeather(commandContext.getSource(), 2)))
                        .then(Commands.literal("light_rain").executes((commandContext) -> setWeather(commandContext.getSource(), 3)))
                        .then(Commands.literal("normal_rain").executes((commandContext) -> setWeather(commandContext.getSource(), 4)))
                        .then(Commands.literal("heavy_rain").executes((commandContext) -> setWeather(commandContext.getSource(), 5)))
                        .then(Commands.literal("storm").executes((commandContext) -> setWeather(commandContext.getSource(), 6)))
                        .then(Commands.literal("foggy").executes((commandContext) -> setWeather(commandContext.getSource(), 7)))
                ));
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


        source.sendFeedback(new TranslationTextComponent("commands.afterthedrizzle.weather.set", WeatherType.values()[weather].getTranslation()), true);
        return getWeather(source.getWorld());
    }
}
