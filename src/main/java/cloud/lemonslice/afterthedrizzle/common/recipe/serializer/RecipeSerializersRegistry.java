package cloud.lemonslice.afterthedrizzle.common.recipe.serializer;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayBakeRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayInRainRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayIndoorsRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTrayOutdoorsRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.special.FlowerDyeRecipe;
import cloud.lemonslice.afterthedrizzle.common.recipe.special.TemperatureResistanceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class RecipeSerializersRegistry
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, AfterTheDrizzle.MODID);

    public final static RegistryObject<SpecialRecipeSerializer<FlowerDyeRecipe>> CRAFTING_SPECIAL_FLOWERDYE = RECIPE_SERIALIZERS.register("crafting_special_flowerdye", () -> new SpecialRecipeSerializer<>(FlowerDyeRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<TemperatureResistanceRecipe>> CRAFTING_TEMP_RESISTANCE = RECIPE_SERIALIZERS.register("crafting_temp_resistance", () -> new SpecialRecipeSerializer<>(TemperatureResistanceRecipe::new));

    public final static RegistryObject<BambooTraySingleInRecipeSerializer<BambooTrayOutdoorsRecipe>> BAMBOO_TRAY_OUTDOORS = RECIPE_SERIALIZERS.register("bamboo_tray_outdoors", () -> new BambooTraySingleInRecipeSerializer<>(BambooTrayOutdoorsRecipe::new, 200));
    public final static RegistryObject<BambooTraySingleInRecipeSerializer<BambooTrayIndoorsRecipe>> BAMBOO_TRAY_INDOORS = RECIPE_SERIALIZERS.register("bamboo_tray_indoors", () -> new BambooTraySingleInRecipeSerializer<>(BambooTrayIndoorsRecipe::new, 200));
    public final static RegistryObject<BambooTraySingleInRecipeSerializer<BambooTrayInRainRecipe>> BAMBOO_TRAY_IN_RAIN = RECIPE_SERIALIZERS.register("bamboo_tray_in_rain", () -> new BambooTraySingleInRecipeSerializer<>(BambooTrayInRainRecipe::new, 0));
    public final static RegistryObject<BambooTraySingleInRecipeSerializer<BambooTrayBakeRecipe>> BAMBOO_TRAY_BAKE = RECIPE_SERIALIZERS.register("bamboo_tray_bake", () -> new BambooTraySingleInRecipeSerializer<>(BambooTrayBakeRecipe::new, 200));

}