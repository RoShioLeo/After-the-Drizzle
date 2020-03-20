package roito.afterthedrizzle.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.block.inter.INormalRegister;
import roito.afterthedrizzle.common.block.inter.IOreDictRegister;
import roito.afterthedrizzle.helper.LogHelper;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class RegisterManager
{
    public static NonNullList<Item> ITEMS = NonNullList.create();
    public static NonNullList<Block> BLOCKS = NonNullList.create();

    public static HashMap<ItemStack, String> ORE_DICT = new HashMap<>();
    public static NonNullList<Item> MODEL_ITEMS = NonNullList.create();
    public static NonNullList<Fluid> FLUID = NonNullList.create();
    public static NonNullList<BlockFluidBase> FLUID_BLOCK = NonNullList.create();
    public static NonNullList<Potion> POTION = NonNullList.create();

    public static void addToRegister(INormalRegister o)
    {
        String name = o.getRegisterInfo();
        if (o instanceof Item)
        {
            ((Item) o).setRegistryName(AfterTheDrizzle.MODID, name);
            ((Item) o).setTranslationKey(AfterTheDrizzle.MODID + "." + name);
            ITEMS.add((Item) o);
            if (o.shouldRegisterModel())
            {
                MODEL_ITEMS.add((Item) o);
            }
        }
        else if (o instanceof BlockFluidBase)
        {
            ((BlockFluidBase) o).setRegistryName(AfterTheDrizzle.MODID, name);
            ((BlockFluidBase) o).setTranslationKey(AfterTheDrizzle.MODID + "." + name);
            RegisterManager.BLOCKS.add((BlockFluidBase) o);
            RegisterManager.FLUID_BLOCK.add((BlockFluidBase) o);
        }
        else if (o instanceof Block)
        {
            ((Block) o).setRegistryName(AfterTheDrizzle.MODID, name);
            ((Block) o).setTranslationKey(AfterTheDrizzle.MODID + "." + name);
            BLOCKS.add((Block) o);
            if (o.shouldRegisterItem())
            {
                Item itemBlock = new ItemBlock((Block) o);
                itemBlock.setRegistryName(AfterTheDrizzle.MODID, name);
                itemBlock.setTranslationKey(AfterTheDrizzle.MODID + "." + name);
                ITEMS.add(itemBlock);
                if (o.shouldRegisterModel())
                {
                    MODEL_ITEMS.add(itemBlock);
                }
            }
        }
        else if (o instanceof Potion)
        {
            ((Potion) o).setPotionName(AfterTheDrizzle.MODID + ".potion." + name);
            ((Potion) o).setRegistryName(AfterTheDrizzle.MODID, name);
            POTION.add((Potion) o);
        }
    }

    public static void addOreDicts(IOreDictRegister o)
    {
        String[] oreDictOrigin = o.getOreDicts();
        for (int i = 0; i < oreDictOrigin.length; i++)
        {
            if (!oreDictOrigin[i].matches("\\d+ *(, *\\d+)* *:.+(,.+)*"))
            {
                if (oreDictOrigin[i].equals(""))
                {
                    continue;
                }
                oreDictOrigin[i] = i + ":" + oreDictOrigin[i];
            }
            String[] dict = oreDictOrigin[i].split(":");
            String[] ids = dict[0].split(",");
            String[] names = dict[1].split(",");

            for (String name : names)
            {
                for (String idS : ids)
                {
                    try
                    {
                        int meta = Integer.parseInt(idS);
                        String nameNew = name.trim();
                        if (!nameNew.equals(""))
                        {
                            if (o instanceof Item)
                            {
                                ORE_DICT.put(new ItemStack((Item) o, 1, meta), name.trim());
                            }
                            else
                            {
                                ORE_DICT.put(new ItemStack((Block) o, 1, meta), name.trim());
                            }
                            LogHelper.info("Added %s (meta: %s) to OreDictionary \"%s\".", ((IForgeRegistryEntry) o).getRegistryName(), meta, name.trim());
                        }
                    }
                    catch (Exception ignored)
                    {
                    }
                }
            }
        }
    }

    public static void registerFluids(Fluid fluid)
    {
        if (!net.minecraftforge.fluids.FluidRegistry.isFluidRegistered(fluid))
        {
            net.minecraftforge.fluids.FluidRegistry.registerFluid(fluid);
            net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(fluid);
            RegisterManager.FLUID.add(fluid);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        LogHelper.info("Successfully registered %d items.", ITEMS.size());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
        LogHelper.info("Successfully registered %d blocks.", BLOCKS.size());
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().registerAll(POTION.toArray(new Potion[0]));
        if (POTION.size() != 0)
            LogHelper.info("Successfully registered %d potions.", POTION.size());
    }

    public static void clearAll()
    {
        ITEMS = null;
        BLOCKS = null;
        MODEL_ITEMS = null;
        ORE_DICT = null;
        FLUID = null;
        FLUID_BLOCK = null;
        POTION = null;
    }
}
