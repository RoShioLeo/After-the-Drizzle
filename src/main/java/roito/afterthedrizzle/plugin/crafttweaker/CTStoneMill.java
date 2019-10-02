package roito.afterthedrizzle.plugin.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import roito.afterthedrizzle.api.recipe.StoneMillRecipe;
import roito.afterthedrizzle.helper.CraftTweakerHelper;
import roito.afterthedrizzle.registry.RecipesRegistry;
import roito.silveroakoutpost.helper.NonNullListHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.AtD.StoneMill")
@ZenRegister
public class CTStoneMill
{
    @ZenMethod
    public static void addNormalRecipe(IIngredient itemInput, ILiquidStack fluidInput, IItemStack[] itemsOutput, ILiquidStack fluidOutput)
    {
        NonNullList<ItemStack> input1 = CraftTweakerHelper.getItemStacks(itemInput);
        FluidStack input2 = CraftTweakerMC.getLiquidStack(fluidInput);
        NonNullList<ItemStack> output1 = NonNullListHelper.createNonNullList(CraftTweakerMC.getItemStacks(itemsOutput));
        FluidStack output2 = CraftTweakerMC.getLiquidStack(fluidOutput);
        RecipesRegistry.addAction(new Addition(input1, input2, output1, output2));
    }

    private static final class Addition implements IAction
    {
        private final NonNullList<ItemStack> itemInput;
        private final FluidStack fluidInput;
        private final NonNullList<ItemStack> itemOutput;
        private final FluidStack fluidOutput;

        private Addition(NonNullList<ItemStack> itemInput, FluidStack fluidInput, NonNullList<ItemStack> itemOutput, FluidStack fluidOutput)
        {
            this.itemInput = itemInput;
            this.fluidInput = fluidInput;
            this.itemOutput = itemOutput;
            this.fluidOutput = fluidOutput;
        }

        @Override
        public void apply()
        {
            RecipesRegistry.MANAGER_STONE_MILL.add(new StoneMillRecipe(fluidInput, itemInput, itemOutput, fluidOutput));
        }

        @Override
        public String describe()
        {
            return null;
        }
    }

    private static final class Removal implements IAction
    {
        private final NonNullList<ItemStack> itemInput;
        private final FluidStack fluidStack;

        private Removal(NonNullList<ItemStack> itemInput, FluidStack fluidStack)
        {
            this.itemInput = itemInput;
            this.fluidStack = fluidStack;
        }

        @Override
        public void apply()
        {
            RecipesRegistry.MANAGER_STONE_MILL.remove(fluidStack, itemInput);
        }

        @Override
        public String describe()
        {
            return null;
        }
    }

    private static final class Clear implements IAction
    {
        @Override
        public void apply()
        {
            RecipesRegistry.MANAGER_STONE_MILL.removeAll();
        }

        @Override
        public String describe()
        {
            return "Removing all Stone MIll recipes.";
        }
    }
}
