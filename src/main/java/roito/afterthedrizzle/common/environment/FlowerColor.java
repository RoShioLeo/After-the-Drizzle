package roito.afterthedrizzle.common.environment;

import javafx.util.Pair;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IStringSerializable;

import java.util.HashMap;
import java.util.Map;

public enum FlowerColor implements IStringSerializable
{
    //一级花:自然生成
    YELLOW(16701501, Items.YELLOW_DYE),
    RED(11546150, Items.RED_DYE),
    BLUE(3949738, Items.BLUE_DYE),
    WHITE(16383998, Items.WHITE_DYE),
    ORANGE(16351261, Items.ORANGE_DYE),
    PINK(15961002, Items.PINK_DYE),
    MAGENTA(13061821, Items.MAGENTA_DYE),
    LIGHT_BLUE(3847130, Items.LIGHT_BLUE_DYE),
    LIME(8439583, Items.LIME_DYE),
    GRAY(4673362, Items.GRAY_DYE),
    LIGHT_GRAY(10329495, Items.LIGHT_GRAY_DYE),
    CYAN(1481884, Items.CYAN_DYE),
    PURPLE(8991416, Items.PURPLE_DYE),
    BROWN(8606770, Items.BROWN_DYE),
    GREEN(61921500, Items.GREEN_DYE),
    BLACK(1908001, Items.BLACK_DYE);

    private static final Map<Pair<FlowerColor, FlowerColor>, FlowerColor> COLOR_MAP;

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
        return I18n.format("item.minecraft.firework_star." + getName());
    }

    public static FlowerColor getHybColor(FlowerColor color1, FlowerColor color2)
    {
        if (color1 == color2)
        {
            return color1;
        }
        return COLOR_MAP.getOrDefault(new Pair<>(color1, color2), BLACK);
    }

    private static void registerColorRecipe(FlowerColor in1, FlowerColor in2, FlowerColor out)
    {
        COLOR_MAP.put(new Pair<>(in1, in2), out);
        COLOR_MAP.put(new Pair<>(in2, in1), out);
    }

    static
    {
        COLOR_MAP = new HashMap<>();
        registerColorRecipe(RED, YELLOW, ORANGE);
        registerColorRecipe(RED, BLUE, PURPLE);
        registerColorRecipe(RED, WHITE, PINK);
        registerColorRecipe(YELLOW, BLUE, GREEN);
        registerColorRecipe(BLUE, WHITE, LIGHT_BLUE);
        registerColorRecipe(RED, ORANGE, BROWN);
        registerColorRecipe(PINK, PURPLE, MAGENTA);
        registerColorRecipe(YELLOW, GREEN, LIME);
        registerColorRecipe(WHITE, GREEN, LIME);
        registerColorRecipe(BLACK, WHITE, GRAY);
        registerColorRecipe(GRAY, WHITE, LIGHT_GRAY);
        registerColorRecipe(BLUE, GREEN, CYAN);
    }
}
