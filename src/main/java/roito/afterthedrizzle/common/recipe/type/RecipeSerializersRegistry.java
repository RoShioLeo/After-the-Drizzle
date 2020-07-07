package roito.afterthedrizzle.common.recipe.type;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static roito.afterthedrizzle.AfterTheDrizzle.MODID;

public final class RecipeSerializersRegistry
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public final static RegistryObject<SpecialRecipeSerializer<FlowerDyeRecipe>> CRAFTING_SPECIAL_FLOWERDYE = RECIPE_SERIALIZERS.register("crafting_special_flowerdye", () -> new SpecialRecipeSerializer<>(FlowerDyeRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<TemperatureResistanceRecipe>> CRAFTING_TEMP_RESISTANCE = RECIPE_SERIALIZERS.register("crafting_temp_resistance", () -> new SpecialRecipeSerializer<>(TemperatureResistanceRecipe::new));
}