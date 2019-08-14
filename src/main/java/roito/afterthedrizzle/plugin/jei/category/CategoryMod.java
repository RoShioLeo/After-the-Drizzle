package roito.afterthedrizzle.plugin.jei.category;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import roito.afterthedrizzle.AfterTheDrizzle;

public abstract class CategoryMod implements IRecipeCategory<IRecipeWrapper>
{
    protected final String uid;

    public CategoryMod(String uid)
    {
        this.uid = uid;
    }

    @Override
    public String getUid()
    {
        return uid;
    }

    @Override
    public String getTitle()
    {
        return I18n.format(uid);
    }

    @Override
    public String getModName()
    {
        return AfterTheDrizzle.NAME;
    }
}
