package sylvantus.titanrealms.core.integrations.TOP;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import sylvantus.titanrealms.TitanRealms;

import java.util.function.Function;

// Registered via IMC
public class TOPProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {

    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerProvider(this);
        probe.registerProbeConfigProvider(ProbeConfigProvider.INSTANCE);
        // Grab default view settings
        IProbeConfig probeConfig = probe.createProbeConfig();
        // TODO: Set flags here for any configurable information that needs to be optionally displayed by TOP
        return null;
    }

    @Override
    public String getID() {
        // TODO: Update this once I have a set of additional "things" to display
        return TitanRealms.rl("temp").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData iProbeHitData) {
//        BlockPos pos = data.getPos();
//        if (blockState.getBlock() instanceof BlockBounding) {
//            //If we are a bounding block that has a position set, redirect the probe to the main location
//            BlockPos mainPos = BlockBounding.getMainBlockPos(world, pos);
//            if (mainPos != null) {
//                pos = mainPos;
//                //If we end up needing the blockstate at some point lower down, then uncomment this line
//                // until we do though there is no point in bothering to query the world to get it
//                //blockState = world.getBlockState(mainPos);
//            }
//        }
//        TileEntity tile = WorldUtils.getTileEntity(world, pos);
//        if (tile != null) {
//            LookingAtUtils.addInfo(new TOPLookingAtHelper(info), tile, displayTanks(mode), displayFluidTanks);
//        }
    }


}
