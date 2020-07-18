package cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray;

import cloud.lemonslice.afterthedrizzle.common.recipe.InputType;
import cloud.lemonslice.afterthedrizzle.helper.MatchHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BambooTaryRecipe
{
    private String TAG_INPUT;
    private ItemStack ITEM_INPUT;
    private final ItemStack OUTPUT;
    public final InputType TYPE;

    public BambooTaryRecipe(Item input, Item output)
    {
        this.ITEM_INPUT = new ItemStack(input);
        this.OUTPUT = new ItemStack(output);
        this.TYPE = InputType.ITEM_STACK;
    }

    public BambooTaryRecipe(String tag, Item output)
    {
        this.TAG_INPUT = tag;
        this.OUTPUT = new ItemStack(output);
        ;
        this.TYPE = InputType.TAG;
    }

    public BambooTaryRecipe(ItemStack input, ItemStack output)
    {
        this.ITEM_INPUT = input;
        this.OUTPUT = output;
        this.TYPE = InputType.ITEM_STACK;
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

    public List<ItemStack> getInputs()
    {
        if (this.TYPE == InputType.ITEM_STACK)
        {
            return Collections.singletonList(ITEM_INPUT);
        }
        else
        {
            return ItemTags.getCollection().getOrCreate(new ResourceLocation(this.TAG_INPUT)).getAllElements().stream().map(ItemStack::new).collect(Collectors.toList());
        }
    }
}
