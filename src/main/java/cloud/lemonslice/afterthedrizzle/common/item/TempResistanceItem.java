package cloud.lemonslice.afterthedrizzle.common.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TempResistanceItem extends NormalItem
{
    public TempResistanceItem(String name)
    {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (Screen.hasShiftDown())
        {
            tooltip.add(new TranslationTextComponent("info.afterthedrizzle.tooltip.temp_resistance").applyTextStyle(TextFormatting.GRAY));
        }
        else
        {
            tooltip.add(new TranslationTextComponent("info.afterthedrizzle.tooltip.shift").applyTextStyle(TextFormatting.GRAY));
        }
    }
}
