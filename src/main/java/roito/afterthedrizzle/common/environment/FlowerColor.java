package roito.afterthedrizzle.common.environment;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IStringSerializable;

public enum FlowerColor implements IStringSerializable
{
    //一级花:自然生成
    YELLOW(0xFFD82B, Items.YELLOW_DYE),
    RED(0xFF5151, Items.RED_DYE),
    WHITE(0xFFFFFF, Items.WHITE_DYE),

    //二级花: Orange=yellow+red; Pink=white+red; Gold=yellow+white
    ORANGE(0xFF9047, Items.ORANGE_DYE),
    PINK(0xFF9B9B, Items.PINK_DYE),
    GOLD(0xFFE993, null),

    //三级花: Black=2+2; Blue=2+1
    BLACK(0x606060, Items.BLACK_DYE),
    BLUE(0x5AAAE2, Items.BLUE_DYE);

    private final int color;
    private final Item dye;

    FlowerColor(int colorValue, Item dye)
    {
        this.color = colorValue;
        this.dye = dye;
    }

    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public int getColorValue()
    {
        return color;
    }

    public static FlowerColor getFlowerColor(String name)
    {
        return FlowerColor.valueOf(name.toUpperCase());
    }

    public Item getDye()
    {
        return dye;
    }

    public String getTranslation()
    {
        return I18n.format("info.afterthedrizzle.hyb.flower.color." + getName());
    }

    public static FlowerColor getHybColor(FlowerColor color1, FlowerColor color2)
    {
        switch (color1)
        {
            case RED:
                switch (color2)
                {
                    case WHITE:
                        return PINK;
                    case YELLOW:
                        return ORANGE;

                    case GOLD:
                    case PINK:
                    case ORANGE:
                        return BLUE;

                    default:
                        return RED;
                }
            case WHITE:
                switch (color2)
                {
                    case RED:
                        return PINK;
                    case YELLOW:
                        return GOLD;

                    case GOLD:
                    case PINK:
                    case ORANGE:
                        return BLUE;

                    default:
                        return WHITE;
                }
            case YELLOW:
                switch (color2)
                {
                    case RED:
                        return ORANGE;
                    case WHITE:
                        return GOLD;

                    case GOLD:
                    case PINK:
                    case ORANGE:
                        return BLUE;

                    default:
                        return YELLOW;
                }

            case GOLD:
                switch (color2)
                {
                    case RED:
                    case WHITE:
                    case YELLOW:
                        return BLUE;

                    case PINK:
                    case ORANGE:
                        return BLACK;

                    default:
                        return GOLD;
                }
            case PINK:
                switch (color2)
                {
                    case RED:
                    case WHITE:
                    case YELLOW:
                        return BLUE;

                    case GOLD:
                    case ORANGE:
                        return BLACK;

                    default:
                        return PINK;
                }
            case ORANGE:
                switch (color2)
                {
                    case RED:
                    case WHITE:
                    case YELLOW:
                        return BLUE;

                    case GOLD:
                    case PINK:
                        return BLACK;

                    default:
                        return ORANGE;
                }

            case BLUE:
                return BLUE;
            case BLACK:
                return BLACK;

            default:
                return WHITE;
        }
    }
}
