package roito.afterthedrizzle.common.handler;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.config.CommonConfig;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = AfterTheDrizzle.MODID)
public final class CustomRandomTickHandler
{
    private static final CustomRandomTick SNOW_MELT = (state, world, pos) ->
    {
        if (world.getBiome(pos).getTemperature(pos) >= 0.15F)
        {
            if (state.getBlock().equals(Blocks.SNOW))
            {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            else if (state.getBlock().equals(Blocks.ICE))
            {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
            }
        }
    };

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.END) && CommonConfig.Temperature.iceMelt.get() && !event.world.isRemote)
        {
            ServerWorld world = (ServerWorld) event.world;
            int randomTickSpeed = world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
            if (randomTickSpeed > 0)
            {
                world.getChunkProvider().chunkManager.getLoadedChunksIterable().forEach(chunkHolder ->
                {
                    Optional<Chunk> optional = chunkHolder.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left();
                    if (optional.isPresent())
                    {
                        Chunk chunk = optional.get();
                        for (ChunkSection chunksection : chunk.getSections())
                        {
                            if (chunksection != Chunk.EMPTY_SECTION && chunksection.needsRandomTickAny())
                            {
                                int x = chunk.getPos().getXStart();
                                int y = chunksection.getYLocation();
                                int z = chunk.getPos().getZStart();

                                for (int l = 0; l < randomTickSpeed; ++l)
                                {
                                    BlockPos blockpos = world.getBlockRandomPos(x, y, z, 15);
                                    BlockState blockstate = chunksection.getBlockState(blockpos.getX() - x, blockpos.getY() - y, blockpos.getZ() - z);
                                    doCustomRandomTick(blockstate, world, blockpos);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private static void doCustomRandomTick(BlockState state, ServerWorld worldIn, BlockPos pos)
    {
        if (CommonConfig.Temperature.iceMelt.get())
        {
            SNOW_MELT.tick(state, worldIn, pos);
        }
    }
}
