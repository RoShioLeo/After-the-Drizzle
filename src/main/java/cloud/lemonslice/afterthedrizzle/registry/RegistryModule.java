package cloud.lemonslice.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;

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
                    RegisterManager.TILE_ENTITY_TYPES.add((TileEntityType<?>) o);
                }
                else if (o instanceof ContainerType<?>)
                {
                    RegisterManager.CONTAINER_TYPES.add((ContainerType<?>) o);
                }
                else if (o instanceof EntityType<?>)
                {
                    RegisterManager.ENTITY_TYPES.add((EntityType<?>) o);
                }
                else if (o instanceof Effect)
                {
                    RegisterManager.EFFECTS.add((Effect) o);
                }
                else if (o instanceof Feature<?>)
                {
                    RegisterManager.FEATURES.add((Feature<?>) o);
                }
                else if (o instanceof SoundEvent)
                {
                    RegisterManager.SOUNDS.add((SoundEvent) o);
                }
            }
            catch (Exception ignored)
            {
            }
        }
    }
}
