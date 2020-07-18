package cloud.lemonslice.afterthedrizzle.common.item;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import net.minecraft.item.Item;

public class NormalItem extends Item
{
    public NormalItem(String name, Properties properties)
    {
        super(properties);
        this.setRegistryName(name);
    }

    public NormalItem(String name)
    {
        super(getNormalItemProperties());
        this.setRegistryName(name);
    }

    public static Item.Properties getNormalItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CORE);
    }

    public static Item.Properties getTeaLeavesItemProperties()
    {
        return new Item.Properties().group(AfterTheDrizzle.GROUP_CORE).containerItem(ItemsRegistry.TEA_RESIDUES);
    }
}
