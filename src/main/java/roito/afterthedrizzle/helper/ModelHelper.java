package roito.afterthedrizzle.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
public final class ModelHelper
{
    private static HashMap<Item, String[]> modelNames = new HashMap<>();

    public static void registerItemVariants(String modid, Item item)
    {
        if (item.getHasSubtypes())
        {
            NonNullList<ItemStack> subItems = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), subItems);
            NonNullList<ResourceLocation> rl = NonNullList.create();
            String[] names = new String[16];
            for (ItemStack subItem : subItems)
            {
                String subItemName = item.getTranslationKey(subItem);
                subItemName = subItemName.substring(subItemName.indexOf(".") + 1);
                subItemName = subItemName.substring(subItemName.indexOf(".") + 1);

                names[subItem.getMetadata()] = subItemName;
                rl.add(new ResourceLocation(modid, subItemName));
            }
            modelNames.put(item, names);

            ResourceLocation[] rL = new ResourceLocation[rl.size()];
            ModelBakery.registerItemVariants(item, rl.toArray(rL));
        }
        else
        {
            String itemName = item.getTranslationKey();
            itemName = itemName.substring(itemName.indexOf(".") + 1);
            itemName = itemName.substring(itemName.indexOf(".") + 1);
            modelNames.put(item, new String[]{itemName});
        }
    }

    public static void registerRender(String modid, Item item)
    {
        if (item.getHasSubtypes())
        {
            NonNullList<ItemStack> subItems = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), subItems);
            for (ItemStack subItem : subItems)
            {
                ModelLoader.setCustomModelResourceLocation(item, subItem.getMetadata(), new ModelResourceLocation(modid + ":" + modelNames.get(item)[subItem.getMetadata()], "inventory"));
            }
        }
        else
        {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modid + ":" + modelNames.get(item)[0], "inventory"));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRender(BlockFluidBase blockFluid, ResourceLocation res)
    {
        Item itemFluid = Item.getItemFromBlock(blockFluid);
        ModelLoader.setCustomMeshDefinition(itemFluid, stack -> new ModelResourceLocation(res, blockFluid.getFluid().getName()));
        ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(res, blockFluid.getFluid().getName());
            }
        });
    }

    public static void clear()
    {
        modelNames = null;
    }
}
