package roito.afterthedrizzle.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.environment.flower.FlowerColor;

import javax.annotation.Nullable;
import java.util.List;

public class HybridizableFlowerBlockItem extends NormalBlockItem
{
    public HybridizableFlowerBlockItem(Block blockIn)
    {
        super(blockIn);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
    {
        if (group == AfterTheDrizzle.GROUP_CORE)
        {
            for (FlowerColor c : FlowerColor.values())
            {
                ItemStack stack = new ItemStack(this.getItem());
                CompoundNBT nbt = new CompoundNBT();
                nbt.put("color", new StringNBT(c.getName()));
                stack.setTag(nbt);
                items.add(stack);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (stack.getOrCreateTag().contains("color"))
        {
            tooltip.add(new StringTextComponent(FlowerColor.getFlowerColor(stack.getOrCreateTag().getString("color")).getTranslation()).applyTextStyle(TextFormatting.ITALIC).applyTextStyle(TextFormatting.GRAY));
        }
    }

}
