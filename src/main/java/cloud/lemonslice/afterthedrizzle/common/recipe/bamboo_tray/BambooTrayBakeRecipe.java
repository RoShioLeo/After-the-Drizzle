package cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray;

import cloud.lemonslice.afterthedrizzle.common.recipe.serializer.RecipeSerializersRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.type.NormalRecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class BambooTrayBakeRecipe extends BambooTraySingleInRecipe
{
    public BambooTrayBakeRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime)
    {
        super(idIn, groupIn, ingredientIn, resultIn, workTime);
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return RecipeSerializersRegistry.BAMBOO_TRAY_BAKE.get();
    }

    @Override
    public IRecipeType<?> getType()
    {
        return NormalRecipeTypes.BT_BAKE;
    }
}
