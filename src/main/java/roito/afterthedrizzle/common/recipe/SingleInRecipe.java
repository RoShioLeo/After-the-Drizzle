package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import roito.afterthedrizzle.helper.MatchHelper;

import javax.annotation.Nonnull;

public class SingleInRecipe
{
    private String TAG_INPUT;
    private ItemStack ITEM_INPUT;
    private final ItemStack OUTPUT;
    public final InputType TYPE;

    public SingleInRecipe(ItemStack input, ItemStack output)
    {
        this.ITEM_INPUT = input;
        this.OUTPUT = output;
        this.TYPE = InputType.ITEM_STACK;
    }

    public SingleInRecipe(String tag, ItemStack output)
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
