package cloud.lemonslice.afterthedrizzle.common.item;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.config.CommonConfig;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;

public class FertilizerItem extends NormalItem
{
    public FertilizerItem(String name)
    {
        super(name, new Properties().group(AfterTheDrizzle.GROUP_CORE));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        if (CommonConfig.Agriculture.useAshAsBoneMeal.get())
        {
            return Items.BONE_MEAL.onItemUse(context);
        }
        return ActionResultType.PASS;
    }
}
