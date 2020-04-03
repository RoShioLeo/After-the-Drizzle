package roito.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;

import java.lang.reflect.Field;

public class RegistryModule
{
    public RegistryModule()
    {
        for (Field field : getClass().getFields())
        {
            try
            {
                Object o = field.get(null);
                if (o instanceof Item)
                {
                    RegisterManager.ITEMS.add((Item) o);
                }
                else if (o instanceof Block)
                {
                    RegisterManager.BLOCKS.add((Block) o);
                }
                else if (o instanceof TileEntityType<?>)
                {
                    RegisterManager.TILE_ENTITY.add((TileEntityType<?>) o);
                }
                else if (o instanceof ContainerType<?>)
                {
                    RegisterManager.CONTAINER_TYPE.add((ContainerType<?>) o);
                }
            }
            catch (Exception ignored)
            {
            }
        }
    }
}
