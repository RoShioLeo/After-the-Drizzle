package cloud.lemonslice.afterthedrizzle.common.block;

import cloud.lemonslice.afterthedrizzle.common.item.DrinkMakerItem;
import cloud.lemonslice.afterthedrizzle.common.item.HybridizableFlowerBlockItem;
import cloud.lemonslice.afterthedrizzle.common.item.NormalBlockItem;
import cloud.lemonslice.afterthedrizzle.registry.RegistryModule;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public final class BlocksRegistry extends RegistryModule
{
    // CRAFT 工艺
    public static final Block BAMBOO_TRAY = new BambooTrayBlock();
    public static final Block DRINK_MAKER = new DrinkMakerBlock();
    public static final Block DIRT_STOVE = new StoveBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), "dirt_stove", 1);
    public static final Block STONE_STOVE = new StoveBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), "stone_stove", 2);
    public static final Block WOODEN_FRAME = new WoodenFrameBlock();
    public static final Block TEAPOT = new TeapotBlock();
    public static final Block STONE_CATAPULT_BOARD = new CatapultBoardBlock(0.2F, "stone_catapult_board", Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F).notSolid());
    public static final Block STONE_CATAPULT_BOARD_WITH_TRAY = new CatapultBoardBlockWithTray("stone_catapult_board_with_tray", Block.Properties.create(Material.ROCK).sound(SoundType.BAMBOO).notSolid());
    public static final Block BAMBOO_CATAPULT_BOARD = new CatapultBoardBlock(0.4F, "bamboo_catapult_board", Block.Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).notSolid());
    public static final Block IRON_CATAPULT_BOARD = new CatapultBoardBlock(0.6F, "iron_catapult_board", Block.Properties.create(Material.IRON).sound(SoundType.STONE).hardnessAndResistance(3.5F).notSolid());
    public static final Block FILTER_SCREEN = new FilterScreenBlock();
    public static final Block INSTRUMENT_SHELTER = new InstrumentShelterBlock();
    public static final Block WOODEN_TRAY = new WoodenTrayBlock();

    public static final Block OAK_TRELLIS = new TrellisBlock("oak_trellis", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block BIRCH_TRELLIS = new TrellisBlock("birch_trellis", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block JUNGLE_TRELLIS = new TrellisBlock("jungle_trellis", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block SPRUCE_TRELLIS = new TrellisBlock("spruce_trellis", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block DARK_OAK_TRELLIS = new TrellisBlock("dark_oak_trellis", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block ACACIA_TRELLIS = new TrellisBlock("acacia_trellis", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block DIRT_AQUEDUCT = new AqueductBlock("dirt_aqueduct", Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND).notSolid());
    public static final Block DIRT_AQUEDUCT_POOL = new AqueductOutputBlock("dirt_aqueduct_pool", Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND).notSolid());

    public static final BlockItem BAMBOO_TRAY_ITEM = new NormalBlockItem(BAMBOO_TRAY);
    public static final BlockItem DRINK_MAKER_ITEM = new DrinkMakerItem();
    public static final BlockItem DIRT_STOVE_ITEM = new NormalBlockItem(DIRT_STOVE);
    public static final BlockItem STONE_STOVE_ITEM = new NormalBlockItem(STONE_STOVE);
    public static final BlockItem WOODEN_FRAME_ITEM = new NormalBlockItem(WOODEN_FRAME);
    public static final BlockItem STONE_CATAPULT_BOARD_ITEM = new NormalBlockItem(STONE_CATAPULT_BOARD);
    public static final BlockItem BAMBOO_CATAPULT_BOARD_ITEM = new NormalBlockItem(BAMBOO_CATAPULT_BOARD);
    public static final BlockItem IRON_CATAPULT_BOARD_ITEM = new NormalBlockItem(IRON_CATAPULT_BOARD);
    public static final BlockItem FILTER_SCREEN_ITEM = new NormalBlockItem(FILTER_SCREEN);
    public static final BlockItem INSTRUMENT_SHELTER_ITEM = new NormalBlockItem(INSTRUMENT_SHELTER);
    public static final BlockItem OAK_TRELLIS_ITEM = new NormalBlockItem(OAK_TRELLIS);
    public static final BlockItem BIRCH_TRELLIS_ITEM = new NormalBlockItem(BIRCH_TRELLIS);
    public static final BlockItem JUNGLE_TRELLIS_ITEM = new NormalBlockItem(JUNGLE_TRELLIS);
    public static final BlockItem SPRUCE_TRELLIS_ITEM = new NormalBlockItem(SPRUCE_TRELLIS);
    public static final BlockItem DARK_OAK_TRELLIS_ITEM = new NormalBlockItem(DARK_OAK_TRELLIS);
    public static final BlockItem ACACIA_TRELLIS_ITEM = new NormalBlockItem(ACACIA_TRELLIS);
    public static final BlockItem DIRT_AQUEDUCT_ITEM = new NormalBlockItem(DIRT_AQUEDUCT);
    public static final BlockItem DIRT_AQUEDUCT_POOL_ITEM = new NormalBlockItem(DIRT_AQUEDUCT_POOL);
    public static final BlockItem WOODEN_TRAY_ITEM = new NormalBlockItem(WOODEN_TRAY);

    // DECORATIONS 装饰
    public static final Block WOODEN_TABLE = new TableBlock("wooden_table", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block WOODEN_CHAIR = new ChairBlock("wooden_chair", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.6F).notSolid());
    public static final Block STONE_TABLE = new TableBlock("stone_table", Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F).notSolid());
    public static final Block STONE_CHAIR = new StoneChairBlock("stone_chair", Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F).notSolid());
    public static final Block BAMBOO_TABLE = new TableBlock("bamboo_table", Block.Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F).notSolid());
    public static final Block BAMBOO_CHAIR = new ChairBlock("bamboo_chair", Block.Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F).notSolid());
    public static final Block BAMBOO_LANTERN = new LanternBlock(Block.Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F).lightValue(15).notSolid())
    {
        @OnlyIn(Dist.CLIENT)
        public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
        {
            double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double) pos.getY() + 0.35D;
            double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }.setRegistryName("bamboo_lantern");
    public static final Block BAMBOO_DOOR = new BambooDoorBlock("bamboo_door");
    public static final Block BAMBOO_GLASS_DOOR = new BambooDoorBlock("bamboo_glass_door");
    public static final Block BAMBOO_LATTICE = new BambooLatticeBlock();
//    public static final Block BAMBOO_GLASS_WINDOW = new BambooGlassWindow();

    public static final BlockItem WOODEN_TABLE_ITEM = new NormalBlockItem(WOODEN_TABLE);
    public static final BlockItem WOODEN_CHAIR_ITEM = new NormalBlockItem(WOODEN_CHAIR);
    public static final BlockItem BAMBOO_TABLE_ITEM = new NormalBlockItem(BAMBOO_TABLE);
    public static final BlockItem BAMBOO_CHAIR_ITEM = new NormalBlockItem(BAMBOO_CHAIR);
    public static final BlockItem STONE_TABLE_ITEM = new NormalBlockItem(STONE_TABLE);
    public static final BlockItem STONE_CHAIR_ITEM = new NormalBlockItem(STONE_CHAIR);
    public static final BlockItem BAMBOO_LANTERN_ITEM = new NormalBlockItem(BAMBOO_LANTERN);
    public static final BlockItem BAMBOO_DOOR_ITEM = new NormalBlockItem(BAMBOO_DOOR);
    public static final BlockItem BAMBOO_GLASS_DOOR_ITEM = new NormalBlockItem(BAMBOO_GLASS_DOOR);
    public static final BlockItem BAMBOO_LATTICE_ITEM = new NormalBlockItem(BAMBOO_LATTICE);
//    public static final BlockItem BAMBOO_GLASS_WINDOW_ITEM = new NormalBlockItem(BAMBOO_GLASS_WINDOW);

    // CROPS 作物
    public static final Block PADDY_FIELD = new PaddyFieldBlock();
    public static final Block TEA_PLANT = new TeaPlantBlock();
    public static final Block WILD_TEA_PLANT = new WildTeaPlantBlock();
    public static final Block RICE_SEEDLING = new RiceSeedlingBlock("rice_seedling");
    public static final Block RICE_PLANT = new RicePlantBlock("rice_plant");
    public static final Block GRAPE = new StemFruitBlock("grape_plant", Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP), TrellisBlock.VineType.GRAPE);

    public static final BlockItem WILD_TEA_PLANT_ITEM = new NormalBlockItem(WILD_TEA_PLANT);

    // FLOWERS 花朵
    public static final Block CHRYSANTHEMUM = new HybridizableFlowerBlock("chrysanthemum");
    public static final Block HYACINTH = new HybridizableFlowerBlock("hyacinth");
    public static final Block ZINNIA = new HybridizableFlowerBlock("zinnia");

    public static final BlockItem CHRYSANTHEMUM_ITEM = new HybridizableFlowerBlockItem(CHRYSANTHEMUM);
    public static final BlockItem HYACINTH_ITEM = new HybridizableFlowerBlockItem(HYACINTH);
    public static final BlockItem ZINNIA_ITEM = new HybridizableFlowerBlockItem(ZINNIA);

    // MISC 杂项
    public static final Block GRASS_BLOCK_WITH_HOLE = new GrassBlock(Block.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT)).setRegistryName("grass_block_with_hole");

    public static final BlockItem GRASS_BLOCK_WITH_HOLE_ITEM = new NormalBlockItem(GRASS_BLOCK_WITH_HOLE);
}
