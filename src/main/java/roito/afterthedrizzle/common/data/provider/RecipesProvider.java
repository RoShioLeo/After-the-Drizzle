package roito.afterthedrizzle.common.data.provider;

import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.data.tag.NormalTags;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.type.RecipeSerializersRegistry;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider
{
    public RecipesProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_SPECIAL_FLOWERDYE.get()).build(consumer, "afterthedrizzle:flower_dye");
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_TEMP_RESISTANCE.get()).build(consumer, "afterthedrizzle:armor_temp_resistance");

        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.BAMBOO_PLANK).key('x', Items.BAMBOO).patternLine("xx").patternLine("xx").setGroup("bamboo_plank").addCriterion("has_bamboo", this.hasItem(Items.BAMBOO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_CATAPULT_BOARD).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Tags.Items.STONE).key('*', Tags.Items.DUSTS_REDSTONE).patternLine("xxx").patternLine("xxx").patternLine("#*#").setGroup("catapult_board").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_DOOR, 3).key('x', ItemsRegistry.BAMBOO_PLANK).patternLine("xx").patternLine("xx").patternLine("xx").setGroup("bamboo_door").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_GLASS_DOOR, 3).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Tags.Items.GLASS_COLORLESS).patternLine("##").patternLine("xx").patternLine("xx").setGroup("bamboo_glass_door").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_CHAIR).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Items.BAMBOO).patternLine("  #").patternLine("xxx").patternLine("# #").setGroup("bamboo_chair").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_LANTERN).key('x', Blocks.TORCH).key('#', Items.BAMBOO).patternLine("###").patternLine("#x#").patternLine("###").setGroup("bamboo_lantern").addCriterion("has_bamboo", this.hasItem(Items.BAMBOO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_TABLE).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Items.BAMBOO).patternLine("xxx").patternLine("# #").patternLine("# #").setGroup("bamboo_table").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_TRAY).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Items.BAMBOO).patternLine("# #").patternLine("#x#").setGroup("bamboo_tray").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.EMPTY_TEA_BAG, 3).key('/', Items.STRING).key('x', Items.PAPER).patternLine(" / ").patternLine("xxx").patternLine("xxx").setGroup("empty_tea_bag").addCriterion("has_paper", this.hasItem(Items.PAPER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.BLACK_TEA_BAG).addIngredient(ItemsRegistry.EMPTY_TEA_BAG).addIngredient(Ingredient.fromTag(NormalTags.Items.CROPS_BLACK_TEA_LEAF), 3).setGroup("tea_bag").addCriterion("has_tea_bag", this.hasItem(ItemsRegistry.EMPTY_TEA_BAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.GREEN_TEA_BAG).addIngredient(ItemsRegistry.EMPTY_TEA_BAG).addIngredient(Ingredient.fromTag(NormalTags.Items.CROPS_GREEN_TEA_LEAF), 3).setGroup("tea_bag").addCriterion("has_tea_bag", this.hasItem(ItemsRegistry.EMPTY_TEA_BAG)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.BOTTLE).key('x', Tags.Items.NUGGETS_IRON).key('#', Tags.Items.GLASS_PANES_COLORLESS).patternLine(" x ").patternLine("# #").patternLine("###").setGroup("bottle").addCriterion("has_glass_pane", this.hasItem(Tags.Items.GLASS_PANES_COLORLESS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.CLAY_CUP).key('x', Items.CLAY_BALL).patternLine("x x").patternLine(" x ").setGroup("clay_cup").addCriterion("has_clay_ball", this.hasItem(Items.CLAY_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.CLAY_TEAPOT).key('x', Blocks.CLAY).patternLine("x x").patternLine(" x ").setGroup("clay_teapot").addCriterion("has_clay_ball", this.hasItem(Items.CLAY_BALL)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.DIRT_STOVE).key('x', NormalTags.Items.DIRT).patternLine("xxx").patternLine("x x").patternLine("xxx").setGroup("stove").addCriterion("has_dirt", this.hasItem(NormalTags.Items.DIRT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.STONE_STOVE).key('x', Tags.Items.STONE).key('#', BlocksRegistry.DIRT_STOVE).patternLine("xxx").patternLine("x#x").patternLine("xxx").setGroup("stove").addCriterion("has_dirt", this.hasItem(NormalTags.Items.DIRT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.DRINK_MAKER).key('x', ItemTags.PLANKS).key('#', Tags.Items.RODS_WOODEN).patternLine("# #").patternLine("xxx").setGroup("drink_maker").addCriterion("has_planks", this.hasItem(ItemTags.PLANKS)).build(consumer);


        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.WATER_BUCKET), FluidsRegistry.BOILING_WATER_BUCKET.get(), 0.2F, 200).addCriterion("has_water_bucket", this.hasItem(Items.WATER_BUCKET)).build(consumer);
    }

    @Override
    public String getName()
    {
        return "After the Drizzle Recipes";
    }
}
