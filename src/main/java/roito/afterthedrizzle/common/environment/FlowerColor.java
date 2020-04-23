package roito.afterthedrizzle.common.environment;

import roito.afterthedrizzle.common.block.HybridizableFlowerBlock;

public enum FlowerColor {
    //一级花:自然生成
    YELLOW("yellow",0xFFD82B),
    RED("red",0xFF5151),
    WHITE("white",0xFFFFFF),

    //二级花: Orange=yellow+red; Pink=white+red; Gold=yellow+white
    ORANGE("orange",0xFF9047),
    PINK("pink",0xFF9B9B),
    GOLD("gold",0xFFE993),

    //三级花: Black=2+2; Blue=2+1
    BLACK("black",0x606060),
    BLUE("blue",0x5AAAE2);
    private final String name;
    private final int color;
    FlowerColor(String name,int colorValue){
        this.name = name;
        this.color = colorValue;
    }

    public String getName() {
        return name;
    }

    public int getColorValue() {
        return color;
    }

    public static FlowerColor getColorFromName(String name) {
        for (FlowerColor color : FlowerColor.values()) {
            if (color.getName().equals(name)) {
                return color;
            }
        }
        return WHITE;
    }
}
