package roito.afterthedrizzle.plugin.jei.category;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeFlatBasket;

public class CategoryFlatBasket extends CategoryMod
{
    protected final IDrawable icon;
    protected final IDrawable background;
    protected final IDrawableAnimated progressBar;

    public CategoryFlatBasket(IGuiHelper helper, String uid, int index)
    {
        super(uid);
        ResourceLocation backgroundTexture = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/container/gui_single_recipe.png");
        background = helper.createDrawable(backgroundTexture, 49, 27, 78, 24);
        IDrawableStatic progressBarOverlay = helper.createDrawable(backgroundTexture, 177, 0, 22, 16);
        progressBar = helper.createAnimatedDrawable(progressBarOverlay, 200, IDrawableAnimated.StartDirection.LEFT, false);
        icon = helper.createDrawable(backgroundTexture, 176, 17 + 18 * index, 18, 18);
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft)
    {
        progressBar.draw(minecraft, 28, 4);
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, IRecipeWrapper iRecipeWrapper, IIngredients iIngredients)
    {
        IGuiItemStackGroup items = iRecipeLayout.getItemStacks();
        items.init(0, true, 3, 3);
        items.set(0, ((RecipeFlatBasket) iRecipeWrapper).getInputs());
        items.init(1, false, 57, 3);
        items.set(1, ((RecipeFlatBasket) iRecipeWrapper).getOutputs());
    }
}
