package roito.afterthedrizzle.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class HandwarmerItem extends NormalItem
{
    public HandwarmerItem()
    {
        super("handwarmer", getNormalItemProperties().maxStackSize(1));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new TranslationTextComponent("info.afterthedrizzle.tooltip.handwarmer").applyTextStyle(TextFormatting.GRAY));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        int ash = 0;
        int coal = 0;
        for (ItemStack itemStack : playerIn.inventory.mainInventory)
        {
            if (itemStack.getItem().getTags().contains(new ResourceLocation("forge:dusts/ash")))
            {
                ash += itemStack.getCount();
            }
            if (itemStack.getItem().getTags().contains(new ResourceLocation("minecraft:coals")))
            {
                coal += itemStack.getCount();
            }
            if (ash >= 8)
            {
                playerIn.inventory.clearMatchingItems(item -> (item.getItem().getTags().contains(new ResourceLocation("forge:dusts/ash"))), 8);
                return ActionResult.newResult(ActionResultType.SUCCESS, new ItemStack(ItemsRegistry.LIT_HANDWARMER));
            }
            else if (coal >= 2)
            {
                playerIn.inventory.clearMatchingItems(item -> (item.getItem().getTags().contains(new ResourceLocation("minecraft:coals"))), 2);
                return ActionResult.newResult(ActionResultType.SUCCESS, new ItemStack(ItemsRegistry.LIT_HANDWARMER));
            }
        }
        playerIn.sendStatusMessage(new TranslationTextComponent("info.afterthedrizzle.message.handwarmer"), true);
        return ActionResult.newResult(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
}
