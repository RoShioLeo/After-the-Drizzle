package roito.afterthedrizzle.client.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class HybridizableFlowerItemColor implements IItemColor
{
    public static final int WHITE = 0xFFFFFF;
    public static final int RED = 0xFF5151;
    public static final int YELLOW = 0xFFD800;
    public static final int PINK = 0xFF9B9B;
    public static final int GOLD = 0xFFE884;
    public static final int ORANGE = 0xFF9047;
    public static final int BLUE = 0x379CE5;
    public static final int BLACK = 0x606060;

    @Override
    public int getColor(ItemStack itemStack, int tintIndex)
    {
        int color = 0xFFFFFF;
        if (tintIndex == 1)
        {
            if (itemStack.hasTag() && itemStack.getTag().contains("color"))
            {
                switch (itemStack.getTag().getString("color"))
                {
                    case "white":
                        color = WHITE;
                        break;
                    case "red":
                        color = RED;
                        break;
                    case "yellow":
                        color = YELLOW;
                        break;
                    case "orange":
                        color = ORANGE;
                        break;
                    case "pink":
                        color = PINK;
                        break;
                    case "gold":
                        color = GOLD;
                        break;
                    case "blue":
                        color = BLUE;
                        break;
                    case "black":
                        color = BLACK;
                }
            }
            return color;
        }
        return -1;
    }
}
