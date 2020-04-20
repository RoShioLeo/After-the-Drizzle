package roito.afterthedrizzle.client.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import roito.afterthedrizzle.common.block.HybridizableFlowerBlock;
import roito.afterthedrizzle.common.environment.FlowerColor;

public class HybridizableFlowerItemColor implements IItemColor {

    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 1){
            if (itemStack.hasTag() && itemStack.getTag().contains("color")){
                return FlowerColor.getColorFromName(itemStack.getTag().getString("color")).getColorValue();
            }
        }
        return -1;
    }
}
