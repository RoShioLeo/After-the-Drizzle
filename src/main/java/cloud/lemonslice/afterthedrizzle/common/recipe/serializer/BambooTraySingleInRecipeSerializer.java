package cloud.lemonslice.afterthedrizzle.common.recipe.serializer;

import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTraySingleInRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class BambooTraySingleInRecipeSerializer<T extends BambooTraySingleInRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T>
{
    private final int workingTime;
    private final IFactory<T> factory;

    public BambooTraySingleInRecipeSerializer(IFactory<T> factory, int timeIn)
    {
        this.workingTime = timeIn;
        this.factory = factory;
    }

    public T read(ResourceLocation recipeId, JsonObject json)
    {
        String group = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
        if (!json.has("result"))
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack result;
        if (json.get("result").isJsonObject())
            result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        else
        {
            result = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(json, "result"))));
        }
        int i = JSONUtils.getInt(json, "workingtime", this.workingTime);
        return this.factory.create(recipeId, group, ingredient, result, i);
    }

    public T read(ResourceLocation recipeId, PacketBuffer buffer)
    {
        String s = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        int i = buffer.readVarInt();
        return this.factory.create(recipeId, s, ingredient, itemstack, i);
    }

    public void write(PacketBuffer buffer, T recipe)
    {
        buffer.writeString(recipe.getGroup());
        recipe.getIngredient().write(buffer);
        buffer.writeItemStack(recipe.getRecipeOutput());
        buffer.writeVarInt(recipe.getWorkTime());
    }

    interface IFactory<T extends BambooTraySingleInRecipe>
    {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, int workingTime);
    }
}
