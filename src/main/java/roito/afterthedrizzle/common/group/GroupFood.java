package roito.afterthedrizzle.common.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.item.ItemsRegistry;

public class GroupFood extends ItemGroup
{
    public GroupFood()
    {
        super(AfterTheDrizzle.MODID + ".food");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ItemsRegistry.BEEF_JERKY);
    }
}
