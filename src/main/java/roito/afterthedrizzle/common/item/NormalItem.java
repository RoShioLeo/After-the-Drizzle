package roito.afterthedrizzle.common.item;

import net.minecraft.item.Item;

public class NormalItem extends Item
{
    public NormalItem(String name, Properties properties)
    {
        super(properties);
        this.setRegistryName(name);
    }
}
