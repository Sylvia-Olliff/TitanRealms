package sylvantus.titanrealms.core.base;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.recipes.TitanRealmsRecipeType;
import sylvantus.titanrealms.core.network.PacketClearRecipeCache;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ReloadListener implements IFutureReloadListener {

    @Nonnull
    @Override
    public CompletableFuture<Void> reload(@Nonnull IStage stage, @Nonnull IResourceManager resourceManager, @Nonnull IProfiler preparationsProfiler,
                                          @Nonnull IProfiler reloadProfiler, @Nonnull Executor backgroundExecutor, @Nonnull Executor gameExecutor) {
        return CompletableFuture.runAsync(() -> {
            TitanRealmsRecipeType.clearCache();
            TitanRealms.packetHandler.sendToAllIfLoaded(new PacketClearRecipeCache());
            CommonWorldTickHandler.flushTagAndRecipeCaches = true;
        }, gameExecutor).thenCompose(stage::markCompleteAwaitingOthers);
    }
}
