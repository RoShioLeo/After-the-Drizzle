package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import roito.afterthedrizzle.common.block.HybridizableFlowerBlock;

import javax.annotation.Nullable;

import static roito.afterthedrizzle.client.color.HybridizableFlowerItemColor.*;

public class HybridizableFlowerBlockColor implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        //if (p_getColor_4_ == 1){
            int color = WHITE;
            switch (state.get(HybridizableFlowerBlock.FLOWER_COLOR).getName()){
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
            return color;
        //}
        //return -1;
    }
}
