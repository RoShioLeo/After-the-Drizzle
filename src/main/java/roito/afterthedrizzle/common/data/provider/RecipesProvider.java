package roito.afterthedrizzle.common.data.provider;

import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
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
        // Special Custom Recipes 自定义特殊配方
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_SPECIAL_FLOWERDYE.get()).build(consumer, "afterthedrizzle:flower_dye");
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_TEMP_RESISTANCE.get()).build(consumer, "afterthedrizzle:armor_temp_resistance");

        // Decoration Recipes 装饰品配方
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.BAMBOO_PLANK).key('x', Items.BAMBOO).patternLine("xx").patternLine("xx").setGroup("bamboo_plank").addCriterion("has_bamboo", this.hasItem(Items.BAMBOO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_DOOR, 3).key('x', ItemsRegistry.BAMBOO_PLANK).patternLine("xx").patternLine("xx").patternLine("xx").setGroup("bamboo_door").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_GLASS_DOOR, 3).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Tags.Items.GLASS_COLORLESS).patternLine("##").patternLine("xx").patternLine("xx").setGroup("bamboo_glass_door").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_CHAIR).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Items.BAMBOO).patternLine("  #").patternLine("xxx").patternLine("# #").setGroup("bamboo_chair").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_LANTERN).key('x', Blocks.TORCH).key('#', Items.BAMBOO).patternLine("###").patternLine("#x#").patternLine("###").setGroup("bamboo_lantern").addCriterion("has_bamboo", this.hasItem(Items.BAMBOO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_TABLE).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Items.BAMBOO).patternLine("xxx").patternLine("# #").patternLine("# #").setGroup("bamboo_table").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_TRAY).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Items.BAMBOO).patternLine("# #").patternLine("#x#").setGroup("bamboo_tray").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.WOODEN_FRAME).key('x', Tags.Items.RODS_WOODEN).key('#', ItemTags.PLANKS).patternLine("#x#").patternLine("x#x").patternLine("x x").setGroup("wooden_frame").addCriterion("has_plank", this.hasItem(ItemTags.PLANKS)).build(consumer);

        // Drink Ingredient Recipes 茶饮配料配方
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.EMPTY_TEA_BAG, 3).key('/', Items.STRING).key('x', Items.PAPER).patternLine(" / ").patternLine("xxx").patternLine("xxx").setGroup("empty_tea_bag").addCriterion("has_paper", this.hasItem(Items.PAPER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.BLACK_TEA_BAG).addIngredient(ItemsRegistry.EMPTY_TEA_BAG).addIngredient(Ingredient.fromTag(NormalTags.Items.CROPS_BLACK_TEA_LEAF), 3).setGroup("tea_bag").addCriterion("has_tea_bag", this.hasItem(ItemsRegistry.EMPTY_TEA_BAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.GREEN_TEA_BAG).addIngredient(ItemsRegistry.EMPTY_TEA_BAG).addIngredient(Ingredient.fromTag(NormalTags.Items.CROPS_GREEN_TEA_LEAF), 3).setGroup("tea_bag").addCriterion("has_tea_bag", this.hasItem(ItemsRegistry.EMPTY_TEA_BAG)).build(consumer);

        // Tea Set Recipes 茶具配方
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.BOTTLE).key('x', Tags.Items.NUGGETS_IRON).key('#', Tags.Items.GLASS_PANES_COLORLESS).patternLine(" x ").patternLine("# #").patternLine("###").setGroup("bottle").addCriterion("has_glass_pane", this.hasItem(Tags.Items.GLASS_PANES_COLORLESS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.CLAY_CUP).key('x', Items.CLAY_BALL).patternLine("x x").patternLine(" x ").setGroup("clay_cup").addCriterion("has_clay_ball", this.hasItem(Items.CLAY_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.CLAY_TEAPOT).key('x', Blocks.CLAY).patternLine("x x").patternLine(" x ").setGroup("clay_teapot").addCriterion("has_clay_ball", this.hasItem(Items.CLAY_BALL)).build(consumer);

        // Craft Block Recipes 工艺方块配方
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.DIRT_STOVE).key('x', NormalTags.Items.DIRT).patternLine("xxx").patternLine("x x").patternLine("xxx").setGroup("stove").addCriterion("has_dirt", this.hasItem(NormalTags.Items.DIRT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.STONE_STOVE).key('x', Tags.Items.STONE).key('#', BlocksRegistry.DIRT_STOVE).patternLine("xxx").patternLine("x#x").patternLine("xxx").setGroup("stove").addCriterion("has_dirt", this.hasItem(NormalTags.Items.DIRT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.DRINK_MAKER).key('x', ItemTags.PLANKS).key('#', Tags.Items.RODS_WOODEN).patternLine("# #").patternLine("xxx").setGroup("drink_maker").addCriterion("has_planks", this.hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.FILTER_SCREEN).key('x', Tags.Items.RODS_WOODEN).key('#', Tags.Items.STRING).key('*', Tags.Items.DUSTS_REDSTONE).patternLine("x#x").patternLine("#*#").patternLine("x#x").setGroup("filter_screen").addCriterion("has_string", this.hasItem(Tags.Items.STRING)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BAMBOO_CATAPULT_BOARD).key('x', ItemsRegistry.BAMBOO_PLANK).key('#', Tags.Items.STONE).key('*', Tags.Items.DUSTS_REDSTONE).patternLine("xxx").patternLine("xxx").patternLine("#*#").setGroup("catapult_board").addCriterion("has_bamboo_plank", this.hasItem(ItemsRegistry.BAMBOO_PLANK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.IRON_CATAPULT_BOARD).key('x', Tags.Items.INGOTS_IRON).key('#', Tags.Items.STONE).key('*', Tags.Items.DUSTS_REDSTONE).patternLine("xxx").patternLine("xxx").patternLine("#*#").setGroup("catapult_board").addCriterion("has_iron_ingot", this.hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.STONE_CATAPULT_BOARD).key('x', Blocks.STONE).key('#', Tags.Items.STONE).key('*', Tags.Items.DUSTS_REDSTONE).patternLine("xxx").patternLine("xxx").patternLine("#*#").setGroup("catapult_board").addCriterion("has_stone", this.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.OAK_TRELLIS, 2).key('#', Tags.Items.RODS_WOODEN).key('*', Blocks.OAK_FENCE).patternLine("#*#").patternLine(" # ").setGroup("trellis").addCriterion("has_planks", this.hasItem(Blocks.OAK_FENCE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.BIRCH_TRELLIS, 2).key('#', Tags.Items.RODS_WOODEN).key('*', Blocks.BIRCH_FENCE).patternLine("#*#").patternLine(" # ").setGroup("trellis").addCriterion("has_planks", this.hasItem(Blocks.BIRCH_FENCE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.SPRUCE_TRELLIS, 2).key('#', Tags.Items.RODS_WOODEN).key('*', Blocks.SPRUCE_FENCE).patternLine("#*#").patternLine(" # ").setGroup("trellis").addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_FENCE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.JUNGLE_TRELLIS, 2).key('#', Tags.Items.RODS_WOODEN).key('*', Blocks.JUNGLE_FENCE).patternLine("#*#").patternLine(" # ").setGroup("trellis").addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_FENCE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.DARK_OAK_TRELLIS, 2).key('#', Tags.Items.RODS_WOODEN).key('*', Blocks.DARK_OAK_FENCE).patternLine("#*#").patternLine(" # ").setGroup("trellis").addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_FENCE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlocksRegistry.ACACIA_TRELLIS, 2).key('#', Tags.Items.RODS_WOODEN).key('*', Blocks.ACACIA_FENCE).patternLine("#*#").patternLine(" # ").setGroup("trellis").addCriterion("has_planks", this.hasItem(Blocks.ACACIA_FENCE)).build(consumer);

        // Survival Ingredient Recipes 生存用品配方
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.GAUZE).key('#', Tags.Items.STRING).patternLine("# #").patternLine("###").patternLine("# #").setGroup("gauze").addCriterion("has_string", this.hasItem(Tags.Items.STRING)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.INSULATING_LAYER).key('x', Tags.Items.LEATHER).key('#', ItemTags.WOOL).key('*', Tags.Items.FEATHERS).patternLine("xxx").patternLine("###").patternLine("***").setGroup("insulating_layer").addCriterion("has_leather", this.hasItem(Tags.Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.WATER_BAG).key('x', Tags.Items.LEATHER).key('#', Items.WATER_BUCKET).key('*', Tags.Items.NUGGETS_IRON).patternLine("x*x").patternLine("x#x").patternLine("xxx").setGroup("water_bag").addCriterion("has_leather", this.hasItem(Tags.Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.HANDWARMER).key('x', Tags.Items.NUGGETS_IRON).key('#', Tags.Items.INGOTS_IRON).patternLine(" x ").patternLine("# #").patternLine("###").setGroup("handwarmer").addCriterion("has_iron_ingot", this.hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.LIT_HANDWARMER).key('*', ItemsRegistry.HANDWARMER).key('#', NormalTags.Items.DUSTS_ASH).patternLine("###").patternLine("#*#").patternLine("###").setGroup("lit_handwarmer").addCriterion("has_handwarmer", this.hasItem(ItemsRegistry.HANDWARMER)).build(consumer, new ResourceLocation("afterthedrizzle:lit_handwarmer_ash"));
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.LIT_HANDWARMER).addIngredient(ItemsRegistry.HANDWARMER).addIngredient(Ingredient.fromTag(ItemTags.COALS), 2).setGroup("lit_handwarmer").addCriterion("has_handwarmer", this.hasItem(ItemsRegistry.HANDWARMER)).build(consumer, new ResourceLocation("afterthedrizzle:lit_handwarmer_coal"));
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.ICE_WATER_BAG).addIngredient(ItemsRegistry.WATER_BAG).addIngredient(Blocks.ICE).setGroup("ice_water_bag").addCriterion("has_ice", this.hasItem(Blocks.ICE)).build(consumer, new ResourceLocation("afterthedrizzle:ice_water_bag_ice"));
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.ICE_WATER_BAG).addIngredient(ItemsRegistry.WATER_BAG).addIngredient(Items.SNOWBALL, 3).setGroup("ice_water_bag").addCriterion("has_snowball", this.hasItem(Items.SNOWBALL)).build(consumer, new ResourceLocation("afterthedrizzle:ice_water_bag_snow"));
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.ICE_WATER_BAG).addIngredient(ItemsRegistry.WATER_BAG).addIngredient(Blocks.SNOW_BLOCK).setGroup("ice_water_bag").addCriterion("has_snowball", this.hasItem(Items.SNOWBALL)).build(consumer, new ResourceLocation("afterthedrizzle:ice_water_bag_snow_block"));
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.THERMOMETER).key('x', Items.WATER_BUCKET).key('#', Tags.Items.GLASS_COLORLESS).patternLine("###").patternLine("# #").patternLine("#x#").setGroup("thermometer").addCriterion("has_water_bucket", this.hasItem(Items.WATER_BUCKET)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemsRegistry.RAIN_GAUGE).key('#', Tags.Items.GLASS_COLORLESS).key('*', Items.BUCKET).patternLine("# #").patternLine("# #").patternLine("#*#").setGroup("rain_gauge").addCriterion("has_bucket", this.hasItem(Items.BUCKET)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemsRegistry.HYGROMETER).addIngredient(ItemsRegistry.THERMOMETER).addIngredient(ItemsRegistry.RAIN_GAUGE).setGroup("hygrometer").addCriterion("has_thermometer", this.hasItem(ItemsRegistry.THERMOMETER)).build(consumer);

        // Smelting Recipes 熔炼配方
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.WATER_BUCKET), FluidsRegistry.BOILING_WATER_BUCKET.get(), 0.2F, 200).addCriterion("has_water_bucket", this.hasItem(Items.WATER_BUCKET)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemsRegistry.CLAY_CUP), ItemsRegistry.PORCELAIN_CUP, 0.2F, 200).addCriterion("has_clay_cup", this.hasItem(ItemsRegistry.CLAY_CUP)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemsRegistry.CLAY_TEAPOT), ItemsRegistry.PORCELAIN_TEAPOT, 0.2F, 200).addCriterion("has_clay_teapot", this.hasItem(ItemsRegistry.CLAY_TEAPOT)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemsRegistry.WATER_BAG), ItemsRegistry.HOT_WATER_BAG, 0.2F, 200).addCriterion("has_water_bag", this.hasItem(ItemsRegistry.WATER_BAG)).build(consumer);
    }

    @Override
    public String getName()
    {
        return "After the Drizzle Recipes";
    }
}
