package cloud.lemonslice.afterthedrizzle.client.color.block;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.tileentity.TeaCupTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class TeaCupBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable ILightReader reader, @Nullable BlockPos pos, int tintindex)
    {
        if (pos != null)
        {
            World world = AfterTheDrizzle.proxy.getClientWorld();
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TeaCupTileEntity)
            {
                int drink = 0;
                int t = tintindex;
                int color;
                do
                {
                    color = ((TeaCupTileEntity) te).getFluid(drink).getAttributes().getColor();
                    drink++;
                    if (color != 0)
                    {
                        t--;
                    }
                }
                while (t != 0 && drink < 3);
                return color;
            }
        }
        return -1;
    }
}
