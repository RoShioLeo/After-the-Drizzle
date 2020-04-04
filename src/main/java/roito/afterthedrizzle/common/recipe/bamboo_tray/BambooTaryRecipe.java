package roito.afterthedrizzle.common.recipe.bamboo_tray;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.common.recipe.InputType;
import roito.afterthedrizzle.helper.MatchHelper;

import javax.annotation.Nonnull;

public class BambooTaryRecipe
{
    private String TAG_INPUT;
    private ItemStack ITEM_INPUT;
    private final ItemStack OUTPUT;
    public final InputType TYPE;

    public BambooTaryRecipe(ItemStack input, ItemStack output)
    {
        this.ITEM_INPUT = input;
        this.OUTPUT = output;
        this.TYPE = InputType.ITEM_STACK;
    }

    public BambooTaryRecipe(String tag, ItemStack output)
    {
        this.TAG_INPUT = tag;
        this.OUTPUT = output;
        this.TYPE = InputType.TAG;
    }

    public ItemStack getOutput()
    {
        return OUTPUT.copy();
    }

    public boolean isTheSameInput(@Nonnull ItemStack input)
    {
        if (this.TYPE == InputType.ITEM_STACK)
        {
            return MatchHelper.containsMatch(input, this.ITEM_INPUT);
        }
        else
        {
            return MatchHelper.containsMatch(input, new ResourceLocation(this.TAG_INPUT));
        }
    }
}
