package roito.afterthedrizzle.common.item;

import net.minecraft.item.Item;
import roito.afterthedrizzle.AfterTheDrizzle;

public class NormalItem extends Item
{
    public NormalItem(String name, Properties properties)
    {
        super(properties);
        this.setRegistryName(name);
    }

    public NormalItem(String name)
    {
        super(getCoreItemProperties());
        this.setRegistryName(name);
    }

    public static Item.Properties getCoreItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CORE);
    }
}
