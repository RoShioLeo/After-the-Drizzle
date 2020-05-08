package roito.afterthedrizzle.common.recipe.type;

import com.google.common.collect.Lists;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.item.ItemsRegistry;

import java.util.List;

public class TemperatureResistanceRecipe extends SpecialRecipe
{

    public TemperatureResistanceRecipe(ResourceLocation idIn)
    {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn)
    {
        ItemStack armor = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof ArmorItem)
                {
                    if (!armor.isEmpty())
                    {
                        return false;
                    }

                    armor = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != ItemsRegistry.INSULATING_LAYER)
                    {
                        return false;
                    }

                    list.add(itemstack1);
                }
            }
        }

        return !armor.isEmpty() && list.size() == 3;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv)
    {
        ItemStack armor = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof ArmorItem)
                {
                    if (!armor.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    armor = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != ItemsRegistry.INSULATING_LAYER)
                    {
                        return ItemStack.EMPTY;
                    }

                    list.add(itemstack1);
                }
            }
        }
        if (!armor.isEmpty() && list.size() == 3)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("Resistance", "Cold");
            ItemStack stack = armor.copy();
            stack.setTag(nbt);
            return stack;
        }
        else return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 4;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return RecipeSerializersRegistry.CRAFTING_TEMP_RESISTANCE.get();
    }
}
