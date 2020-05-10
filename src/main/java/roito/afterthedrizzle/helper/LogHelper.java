package roito.afterthedrizzle.helper;

import org.apache.logging.log4j.Level;
import roito.afterthedrizzle.AfterTheDrizzle;

public final class LogHelper
{
    public static void error(String format, Object... data)
    {
        AfterTheDrizzle.LOGGER.log(Level.ERROR, String.format(format, data));
    }

    public static void warn(String format, Object... data)
    {
        AfterTheDrizzle.LOGGER.log(Level.WARN, String.format(format, data));
    }

    public static void info(String format, Object... data)
    {
        AfterTheDrizzle.LOGGER.log(Level.INFO, String.format(format, data));
    }
}
