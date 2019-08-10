package roito.afterthedrizzle.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityStoneMill extends TileEntity implements ITickable
{
    private float angel = 0;

    @Override
    public void update()
    {
        angel += 3.0F;
        angel %= 360;
    }

    public float getAngel()
    {
        return angel;
    }
}
