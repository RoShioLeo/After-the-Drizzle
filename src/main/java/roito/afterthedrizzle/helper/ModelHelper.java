package roito.afterthedrizzle.helper;

import net.minecraft.item.Item;

import java.util.HashMap;

public final class ModelHelper
{
    private static HashMap<Item, String[]> modelNames = new HashMap<>();


    public static void clear()
    {
        modelNames = null;
    }
}
