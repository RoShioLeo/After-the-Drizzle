package roito.afterthedrizzle.common.item;

import net.minecraft.item.ItemFood;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.inter.INormalRegister;
import roito.afterthedrizzle.common.block.inter.IOreDictRegister;

public class ItemCAFood extends ItemFood implements INormalRegister, IOreDictRegister
{
    private final String name;

    public ItemCAFood(String name, int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(AfterTheDrizzle.TAB_FOOD);
        this.name = name;
    }

    public ItemCAFood(String name, int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
        this.setCreativeTab(AfterTheDrizzle.TAB_FOOD);
        this.name = name;
    }

    @Override
    public String getRegisterInfo()
    {
        return name;
    }
}
