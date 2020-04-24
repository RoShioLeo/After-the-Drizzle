package roito.afterthedrizzle.common.environment;

import net.minecraft.util.IStringSerializable;

public enum FlowerColor implements IStringSerializable
{
    //一级花:自然生成
    YELLOW(0xFFD82B),
    RED(0xFF5151),
    WHITE(0xFFFFFF),

    //二级花: Orange=yellow+red; Pink=white+red; Gold=yellow+white
    ORANGE(0xFF9047),
    PINK(0xFF9B9B),
    GOLD(0xFFE993),

    //三级花: Black=2+2; Blue=2+1
    BLACK(0x606060),
    BLUE(0x5AAAE2);

    private final int color;

    FlowerColor(int colorValue)
    {
        this.color = colorValue;
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
