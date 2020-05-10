package roito.afterthedrizzle.common.data.provider;

import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.common.data.ForgeRecipeProvider;
import roito.afterthedrizzle.common.recipe.type.RecipeSerializersRegistry;

import java.util.function.Consumer;

public class RecipesProvider extends ForgeRecipeProvider
{
    public RecipesProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_SPECIAL_FLOWERDYE.get()).build(consumer, "afterthedrizzle:flower_dye");
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_TEMP_RESISTANCE.get()).build(consumer, "afterthedrizzle:armor_temp_resistance");
    }
}
