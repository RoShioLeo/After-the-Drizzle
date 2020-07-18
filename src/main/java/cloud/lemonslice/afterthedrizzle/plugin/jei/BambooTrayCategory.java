package cloud.lemonslice.afterthedrizzle.plugin.jei;

import cloud.lemonslice.afterthedrizzle.AfterTheDrizzle;
import cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray.BambooTaryRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.config.Constants;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class BambooTrayCategory implements IRecipeCategory<BambooTaryRecipe>
{
    private final String uid;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;
    protected final IDrawable arrow;

    public BambooTrayCategory(IGuiHelper guiHelper, String uid, ResourceLocation resourceLocation, int i)
    {
        this.guiHelper = guiHelper;
        this.uid = uid;
        icon = guiHelper.createDrawable(resourceLocation, i * 20, 0, 20, 20);
        this.arrow = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).build();
    }

    @Override
    public ResourceLocation getUid()
    {
        return new ResourceLocation(AfterTheDrizzle.MODID, uid);
    }

    @Override
    public Class<? extends BambooTaryRecipe> getRecipeClass()
    {
        return BambooTaryRecipe.class;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("info.afterthedrizzle." + uid);
    }

    @Override
    public IDrawable getBackground()
    {
        return guiHelper.createBlankDrawable(80, 20);
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(BambooTaryRecipe bambooTaryRecipe, IIngredients iIngredients)
    {
        List<List<ItemStack>> itemIn = Collections.singletonList(bambooTaryRecipe.getInputs());
        ItemStack itemOut = bambooTaryRecipe.getOutput();
        iIngredients.setInputLists(VanillaTypes.ITEM, itemIn);
        iIngredients.setOutput(VanillaTypes.ITEM, itemOut);
    }

    @Override
    public void draw(BambooTaryRecipe recipe, double mouseX, double mouseY)
    {
        this.arrow.draw(28, 2);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, BambooTaryRecipe bambooTaryRecipe, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 5, 1);
        guiItemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
        guiItemStacks.setBackground(0, guiHelper.getSlotDrawable());
        guiItemStacks.init(1, true, 56, 1);
        guiItemStacks.set(1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
        guiItemStacks.setBackground(1, guiHelper.getSlotDrawable());
    }
}
