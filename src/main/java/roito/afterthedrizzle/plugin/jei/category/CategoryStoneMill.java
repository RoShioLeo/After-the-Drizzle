package roito.afterthedrizzle.plugin.jei.category;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.plugin.jei.recipe.RecipeStoneMill;

public class CategoryStoneMill extends CategoryMod
{
    protected final IDrawable background;
    protected final IDrawable overlay;
    protected final IDrawableAnimated progressBar;

    public CategoryStoneMill(IGuiHelper helper, String uid)
    {
        super(uid);
        ResourceLocation backgroundTexture = new ResourceLocation(AfterTheDrizzle.MODID, "textures/gui/jei/gui_recipe.png");
        background = helper.createDrawable(backgroundTexture, 0, 0, 148, 60);
        IDrawableStatic progressBarOverlay = helper.createDrawable(backgroundTexture, 148, 0, 22, 16);
        progressBar = helper.createAnimatedDrawable(progressBarOverlay, 200, IDrawableAnimated.StartDirection.LEFT, false);
        overlay = helper.createDrawable(backgroundTexture, 148, 16, 18, 48);
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft)
    {
        progressBar.draw(minecraft, 63, 21);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, IRecipeWrapper iRecipeWrapper, IIngredients iIngredients)
    {
        IGuiItemStackGroup items = iRecipeLayout.getItemStacks();
        items.init(0, true, 40, 21);
        items.set(0, ((RecipeStoneMill) iRecipeWrapper).getItemInputs());
        items.init(1, false, 90, 3);
        items.init(2, false, 90, 21);
        items.init(3, false, 90, 39);
        int i = 1;
        for (ItemStack item : ((RecipeStoneMill) iRecipeWrapper).getItemOutputs())
        {
            items.set(i, item);
            i++;
        }
        IGuiFluidStackGroup fluids = iRecipeLayout.getFluidStacks();
        fluids.init(0, true, 4, 6, 16, 48, 2000, true, overlay);
        fluids.set(0, ((RecipeStoneMill) iRecipeWrapper).getFluidInput());
        fluids.init(1, false, 128, 6, 16, 48, 2000, false, overlay);
        fluids.set(1, ((RecipeStoneMill) iRecipeWrapper).getFluidOutput());
    }
}
