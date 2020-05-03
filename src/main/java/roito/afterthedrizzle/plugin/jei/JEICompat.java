package roito.afterthedrizzle.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.system.CallbackI;
import roito.afterthedrizzle.client.gui.BambooTrayGuiContainer;
import roito.afterthedrizzle.common.block.BlocksRegistry;
import roito.afterthedrizzle.common.environment.flower.FlowerColor;
import roito.afterthedrizzle.common.environment.flower.FlowerColorPair;
import roito.afterthedrizzle.common.recipe.RecipesRegistry;

import java.util.ArrayList;
import java.util.List;

import static roito.afterthedrizzle.AfterTheDrizzle.MODID;

@JeiPlugin
public class JEICompat implements IModPlugin
{
    private static final String IN_RAIN = "bamboo_tray.mode.in_rain";
    private static final String OUTDOORS = "bamboo_tray.mode.outdoors";
    private static final String INDOORS = "bamboo_tray.mode.indoors";
    private static final String BAKE = "bamboo_tray.mode.bake";
    public static final String DRINK_MAKER = "drink_maker";
    public static final String FLOWER = "flower_hybridization";

    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(MODID, "recipe");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(
                new BambooTrayCategory(registry.getJeiHelpers().getGuiHelper(), IN_RAIN, new ResourceLocation(MODID,"textures/gui/jei/bamboo_tray.png"), 0),
                new BambooTrayCategory(registry.getJeiHelpers().getGuiHelper(), OUTDOORS, new ResourceLocation(MODID,"textures/gui/jei/bamboo_tray.png"), 1),
                new BambooTrayCategory(registry.getJeiHelpers().getGuiHelper(), INDOORS, new ResourceLocation(MODID,"textures/gui/jei/bamboo_tray.png"), 2),
                new BambooTrayCategory(registry.getJeiHelpers().getGuiHelper(), BAKE, new ResourceLocation(MODID,"textures/gui/jei/bamboo_tray.png"), 3)
        );
        registry.addRecipeCategories(
                new DrinkMakerCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.useNbtForSubtypes(BlocksRegistry.CHRYSANTHEMUM_ITEM, BlocksRegistry.HYACINTH_ITEM, BlocksRegistry.ZINNIA_ITEM);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(BlocksRegistry.BAMBOO_TRAY), new ResourceLocation(MODID, IN_RAIN), new ResourceLocation(MODID, OUTDOORS), new ResourceLocation(MODID, INDOORS), new ResourceLocation(MODID, BAKE));
        registration.addRecipeCatalyst(new ItemStack(BlocksRegistry.DRINK_MAKER), new ResourceLocation(MODID, DRINK_MAKER));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(BambooTrayGuiContainer.class, 77, 32, 22, 17, new ResourceLocation(MODID, IN_RAIN), new ResourceLocation(MODID, OUTDOORS), new ResourceLocation(MODID, INDOORS), new ResourceLocation(MODID, BAKE));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        registration.addRecipes(RecipesRegistry.MANAGER_BAMBOO_TRAY_IN_RAIN.getRecipes(), new ResourceLocation(MODID, IN_RAIN));
        registration.addRecipes(RecipesRegistry.MANAGER_BAMBOO_TRAY_OUTDOORS.getRecipes(), new ResourceLocation(MODID, OUTDOORS));
        registration.addRecipes(RecipesRegistry.MANAGER_BAMBOO_TRAY_INDOORS.getRecipes(), new ResourceLocation(MODID, INDOORS));
        registration.addRecipes(RecipesRegistry.MANAGER_BAMBOO_TRAY_BAKE.getRecipes(), new ResourceLocation(MODID, BAKE));
        registration.addRecipes(RecipesRegistry.MANAGER_DRINK_MAKER.getSet(), new ResourceLocation(MODID, DRINK_MAKER));
    }
}
