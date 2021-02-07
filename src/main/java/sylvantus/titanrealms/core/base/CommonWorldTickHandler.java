package sylvantus.titanrealms.core.base;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sylvantus.titanrealms.TitanRealms;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class CommonWorldTickHandler {

    private static final long maximumDeltaTimeNanoSecs = 16_000_000; // 16 milliseconds

    private Map<ResourceLocation, Queue<ChunkPos>> chunkRegenMap;
    public static boolean flushTagAndRecipeCaches;

    public void addRegenChunk(RegistryKey<World> dimension, ChunkPos chunkCoord) {
        if (chunkRegenMap == null) {
            chunkRegenMap = new Object2ObjectArrayMap<>();
        }
        ResourceLocation dimensionName = dimension.getLocation();
        if (!chunkRegenMap.containsKey(dimensionName)) {
            LinkedList<ChunkPos> list = new LinkedList<>();
            list.add(chunkCoord);
            chunkRegenMap.put(dimensionName, list);
        } else {
            Queue<ChunkPos> regenPositions = chunkRegenMap.get(dimensionName);
            if (!regenPositions.contains(chunkCoord)) {
                regenPositions.add(chunkCoord);
            }
        }
    }

    public void resetRegenChunks() {
        if (chunkRegenMap != null) {
            chunkRegenMap.clear();
        }
    }

    @SubscribeEvent
    public void onTick(ServerTickEvent event) {
        if (event.side.isServer() && event.phase == Phase.END) {
            serverTick();
        }
    }

    @SubscribeEvent
    public void onTick(WorldTickEvent event) {
        if (event.side.isServer() && event.phase == Phase.END) {
            tickEnd((ServerWorld) event.world);
        }
    }

    // Server Tick and Server World tick are not the same.
    private void serverTick() {
        // TODO: Trigger a tick for any Titan Realms services that process on server ticks. (Like the spread of infusion)
    }

    private void tickEnd(ServerWorld world) {
        if (!world.isRemote()) {
            ResourceLocation dimensionName = world.getDimensionKey().getLocation();

            /**
             * Example of how to retroactively generate ores. This most likely won't be useful to me
             * but I wanted it here anyways just in case
             */

//            //Credit to E. Beef
//            if (chunkRegenMap.containsKey(dimensionName)) {
//                Queue<ChunkPos> chunksToGen = chunkRegenMap.get(dimensionName);
//                long startTime = System.nanoTime();
//                while (System.nanoTime() - startTime < maximumDeltaTimeNanoSecs && !chunksToGen.isEmpty()) {
//                    ChunkPos nextChunk = chunksToGen.poll();
//                    if (nextChunk == null) {
//                        break;
//                    }
//
//                    Random fmlRandom = new Random(world.getSeed());
//                    long xSeed = fmlRandom.nextLong() >> 2 + 1L;
//                    long zSeed = fmlRandom.nextLong() >> 2 + 1L;
//                    fmlRandom.setSeed((xSeed * nextChunk.x + zSeed * nextChunk.z) ^ world.getSeed());
//                    if (GenHandler.generate(world, fmlRandom, nextChunk.x, nextChunk.z)) {
//                        TitanRealms.LOGGER.info("Regenerating ores at chunk {}", nextChunk);
//                    }
//                }
//                if (chunksToGen.isEmpty()) {
//                    chunkRegenMap.remove(dimensionName);
//                }
//            }
        }
    }
}
