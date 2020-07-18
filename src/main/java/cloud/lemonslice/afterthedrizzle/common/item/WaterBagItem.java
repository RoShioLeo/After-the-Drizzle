package cloud.lemonslice.afterthedrizzle.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class WaterBagItem extends NormalItem implements IItemWithTemperature
{
    private final int resistancePoint;
    private final String resistanceType;

    public WaterBagItem(String name, Properties properties, int point, String type)
    {
        super(name, properties);
        this.resistancePoint = point;
        this.resistanceType = type;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (stack.isDamageable() && entityIn instanceof PlayerEntity && ((PlayerInventory.isHotbar(itemSlot) || ((PlayerEntity) entityIn).getHeldItemOffhand().equals(stack))))
        {
            if (!((PlayerEntity) entityIn).getCooldownTracker().hasCooldown(this))
            {
                ((PlayerEntity) entityIn).getCooldownTracker().setCooldown(this, 100);
                stack.setDamage(stack.getDamage() + 1);
                if (stack.getDamage() + 1 >= stack.getMaxDamage())
                {
                    ItemHandlerHelper.giveItemToPlayer((PlayerEntity) entityIn, stack.getContainerItem());
                    stack.shrink(1);
                }
            }
        }
    }

    @Override
    public String getResistanceType()
    {
        return resistanceType;
    }

    @Override
    public int getResistancePoint()
    {
        return resistancePoint;
    }

    @Override
    public boolean shouldHeld()
    {
        return false;
    }
}
