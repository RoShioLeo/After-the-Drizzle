package roito.afterthedrizzle.registry;

import roito.afterthedrizzle.common.block.inter.INormalRegister;
import roito.afterthedrizzle.common.block.inter.IOreDictRegister;

import java.lang.reflect.Field;

public class RegistryModule
{
    public RegistryModule(boolean oreDict)
    {
        for (Field field : getClass().getFields())
        {
            Object o;
            try
            {
                o = field.get(null);
                if (o instanceof INormalRegister)
                {
                    RegisterManager.addToRegister((INormalRegister) o);
                }
                if (oreDict && o instanceof IOreDictRegister && ((IOreDictRegister) o).getOreDicts() != null)
                {
                    RegisterManager.addOreDicts((IOreDictRegister) o);
                }
            }
            catch (Exception ignored)
            {
            }
        }
    }
}
