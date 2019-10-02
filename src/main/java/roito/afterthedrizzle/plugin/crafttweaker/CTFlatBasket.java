package roito.afterthedrizzle.plugin.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roito.afterthedrizzle.helper.CraftTweakerHelper;
import roito.afterthedrizzle.registry.RecipesRegistry;
import roito.silveroakoutpost.recipe.ISingleInRecipeManager;
import roito.silveroakoutpost.recipe.SingleInRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.AtD.FlatBasket")
@ZenRegister
public class CTFlatBasket
{
    @ZenMethod
    public static void addOutdoorRecipe(IIngredient itemInput, IItemStack itemsOutput)
    {
        NonNullList<ItemStack> input = CraftTweakerHelper.getItemStacks(itemInput);
        ItemStack output = CraftTweakerMC.getItemStack(itemsOutput);
        RecipesRegistry.addAction(new Addition(RecipesRegistry.MANAGER_BASKET_OUTDOORS, input, output));
    }

    @ZenMethod
    public static void addIndoorRecipe(IIngredient itemInput, IItemStack itemsOutput)
    {
        NonNullList<ItemStack> input = CraftTweakerHelper.getItemStacks(itemInput);
        ItemStack output = CraftTweakerMC.getItemStack(itemsOutput);
        RecipesRegistry.addAction(new Addition(RecipesRegistry.MANAGER_BASKET_INDOORS, input, output));
    }

    @ZenMethod
    public static void addWetRecipe(IIngredient itemInput, IItemStack itemsOutput)
    {
        NonNullList<ItemStack> input = CraftTweakerHelper.getItemStacks(itemInput);
        ItemStack output = CraftTweakerMC.getItemStack(itemsOutput);
        RecipesRegistry.addAction(new Addition(RecipesRegistry.MANAGER_BASKET_IN_RAIN, input, output));
    }

    @ZenMethod
    public static void addBakeRecipe(IIngredient itemInput, IItemStack itemsOutput)
    {
        NonNullList<ItemStack> input = CraftTweakerHelper.getItemStacks(itemInput);
        ItemStack output = CraftTweakerMC.getItemStack(itemsOutput);
        RecipesRegistry.addAction(new Addition(RecipesRegistry.MANAGER_BASKET_BAKE, input, output));
    }

    private static final class Addition implements IAction
    {
        private final NonNullList<ItemStack> itemInput;
        private final ItemStack itemOutput;
        private final ISingleInRecipeManager manager;

        private Addition(ISingleInRecipeManager manager, NonNullList<ItemStack> itemInput, ItemStack itemOutput)
        {
            this.manager = manager;
            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
        }

        @Override
        public void apply()
        {
            manager.add(new SingleInRecipe(itemInput, itemOutput));
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
        private final ISingleInRecipeManager manager;

        private Removal(ISingleInRecipeManager manager, NonNullList<ItemStack> itemInput)
        {
            this.manager = manager;
            this.itemInput = itemInput;
        }

        @Override
        public void apply()
        {
            manager.remove(itemInput);
        }

        @Override
        public String describe()
        {
            return null;
        }
    }

    private static final class Clear implements IAction
    {
        private final ISingleInRecipeManager manager;

        private Clear(ISingleInRecipeManager manager)
        {
            this.manager = manager;
        }

        @Override
        public void apply()
        {
            manager.removeAll();
        }

        @Override
        public String describe()
        {
            return null;
        }
    }
}
