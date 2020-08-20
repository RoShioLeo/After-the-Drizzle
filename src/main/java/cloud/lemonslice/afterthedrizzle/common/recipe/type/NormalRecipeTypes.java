package cloud.lemonslice.afterthedrizzle.common.recipe.type;

import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayBakeRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayInRainRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayIndoorsRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayOutdoorsRecipe;
import net.minecraft.item.crafting.IRecipeType;

import static cloud.lemonslice.afterthedrizzle.AfterTheDrizzle.MODID;

public class NormalRecipeTypes
{
    public static final IRecipeType<BambooTrayInRainRecipe> BT_IN_RAIN = IRecipeType.register(MODID + ":bamboo_tray_in_rain");
    public static final IRecipeType<BambooTrayOutdoorsRecipe> BT_OUTDOORS = IRecipeType.register(MODID + ":bamboo_tray_outdoors");
    public static final IRecipeType<BambooTrayIndoorsRecipe> BT_INDOORS = IRecipeType.register(MODID + ":bamboo_tray_indoors");
    public static final IRecipeType<BambooTrayBakeRecipe> BT_BAKE = IRecipeType.register(MODID + ":bamboo_tray_bake");
}
