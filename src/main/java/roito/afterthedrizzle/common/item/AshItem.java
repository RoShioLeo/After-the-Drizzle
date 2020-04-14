package roito.afterthedrizzle.common.item;

import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.config.NormalConfig;

public class AshItem extends NormalItem
{
    public AshItem()
    {
        super("ash", new Properties().group(AfterTheDrizzle.GROUP_CORE));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        if (NormalConfig.useAshAsBoneMeal.get())
        {
            return Items.BONE_MEAL.onItemUse(context);
        }
        return ActionResultType.PASS;
    }
}
