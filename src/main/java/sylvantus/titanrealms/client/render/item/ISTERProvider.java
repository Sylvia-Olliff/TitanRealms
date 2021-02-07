package sylvantus.titanrealms.client.render.item;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

//This class is used to prevent class loading issues on the server without having to use OnlyIn hacks
public class ISTERProvider {

    private ISTERProvider() {
    }

    // TODO: Example reference for implementing a tile entity renderer
//    public static Callable<ItemStackTileEntityRenderer> energyCube() {
//        return RenderEnergyCubeItem::new;
//    }
}