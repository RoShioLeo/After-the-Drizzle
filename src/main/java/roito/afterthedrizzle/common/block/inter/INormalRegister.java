package roito.afterthedrizzle.common.block.inter;

import roito.afterthedrizzle.registry.RegisterManager;

public interface INormalRegister
{
    String getRegisterInfo();

    default boolean shouldRegisterModel()
    {
        return true;
    }

    default boolean shouldRegisterItem()
    {
        return true;
    }

    default void addToRegister()
    {
        RegisterManager.addToRegister(this);
    }
}
