package sylvantus.titanrealms.core.integrations.TOP;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sylvantus.titanrealms.core.util.WorldUtils;

public class ProbeConfigProvider implements IProbeConfigProvider {

    public static final ProbeConfigProvider INSTANCE = new ProbeConfigProvider();

    @Override
    public void getProbeConfig(IProbeConfig config, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {}

    @Override
    public void getProbeConfig(IProbeConfig config, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
        TileEntity tile = WorldUtils.getTileEntity(world, data.getPos());
        // TODO: set config values for appropriate blocks based on Capabilities.
    }
}
