package roito.afterthedrizzle.client.color.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.common.environment.flower.FlowerColor;

public class HybridizableFlowerItemColor implements IItemColor {

    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 1){
            if (itemStack.hasTag() && itemStack.getTag().contains("color")){
                return FlowerColor.getFlowerColor(itemStack.getTag().getString("color")).getColorValue();
            }
        }
        return -1;
    }
}
