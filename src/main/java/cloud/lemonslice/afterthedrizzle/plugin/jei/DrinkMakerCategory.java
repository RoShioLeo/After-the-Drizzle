package cloud.lemonslice.afterthedrizzle.plugin.jei;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.block.BlocksRegistry;
import cloud.lemonslice.afterthedrizzle.common.drink.DrinkIngredientsManager;
import cloud.lemonslice.afterthedrizzle.common.recipe.RecipesRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cloud.lemonslice.afterthedrizzle.plugin.jei.JEICompat.DRINK_MAKER;

public class DrinkMakerCategory implements IRecipeCategory<DrinkRecipeInput>
{
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public DrinkMakerCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
        icon = guiHelper.createDrawableIngredient(BlocksRegistry.DRINK_MAKER_ITEM.getDefaultInstance());
    }

    @Override
    public ResourceLocation getUid()
    {
        return new ResourceLocation(AfterTheDrizzle.MODID, DRINK_MAKER);
    }

    @Override
    public Class<? extends DrinkRecipeInput> getRecipeClass()
    {
        return DrinkRecipeInput.class;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("info.afterthedrizzle.drink_maker");
    }

    @Override
    public IDrawable getBackground()
    {
        return guiHelper.createDrawable(new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/jei/recipes.png"), 0, 0, 149, 75);
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(DrinkRecipeInput recipe, IIngredients iIngredients)
    {
        List<List<ItemStack>> itemsIn = new ArrayList<>();
        for (String name : recipe.TAG_MAP.keySet())
        {
            int i = recipe.TAG_MAP.get(name);
            while (i-- > 0)
            {
                itemsIn.add(DrinkIngredientsManager.getIngredientItems(name));
            }
            while (itemsIn.size() < 4)
            {
                itemsIn.add(Collections.emptyList());
            }
        }
        FluidStack fluidOut = RecipesRegistry.MANAGER_DRINK_MAKER.getOutput(recipe);
        FluidStack fluidIn = new FluidStack(recipe.INPUT, fluidOut.getAmount());
        iIngredients.setInputLists(VanillaTypes.ITEM, itemsIn);
        iIngredients.setInput(VanillaTypes.FLUID, fluidIn);
        iIngredients.setOutput(VanillaTypes.FLUID, fluidOut);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, DrinkRecipeInput recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 39, 29);
        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        guiItemStacks.init(1, true, 57, 29);
        guiItemStacks.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
        guiItemStacks.init(2, true, 75, 29);
        guiItemStacks.set(2, ingredients.getInputs(VanillaTypes.ITEM).get(2));
        guiItemStacks.init(3, true, 93, 29);
        guiItemStacks.set(3, ingredients.getInputs(VanillaTypes.ITEM).get(3));
        IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
        fluidStackGroup.init(4, true, new FluidStackRenderer(1000, false, 16, 64, (IDrawable) null), 6, 6, 16, 64, 0, 0);
        fluidStackGroup.set(4, ingredients.getInputs(VanillaTypes.FLUID).get(0));
        fluidStackGroup.init(5, true, new FluidStackRenderer(1000, false, 16, 64, (IDrawable) null), 127, 6, 16, 64, 0, 0);
        fluidStackGroup.set(5, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
    }
}
