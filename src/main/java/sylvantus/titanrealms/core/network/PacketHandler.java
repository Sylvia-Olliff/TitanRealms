package sylvantus.titanrealms.core.network;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.network.container.PacketUpdateContainer;
import sylvantus.titanrealms.core.network.container.PacketUpdateContainerBatch;

public class PacketHandler extends BasePacketHandler {

    private static final SimpleChannel netHandler = createChannel(TitanRealms.rl(TitanRealms.MODID));

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        //Client to server messages
        registerClientToServer(PacketKey.class, PacketKey::encode, PacketKey::decode, PacketKey::handle);
        registerClientToServer(PacketGearStateUpdate.class, PacketGearStateUpdate::encode, PacketGearStateUpdate::decode, PacketGearStateUpdate::handle);
        registerClientToServer(PacketGuiButtonPress.class, PacketGuiButtonPress::encode, PacketGuiButtonPress::decode, PacketGuiButtonPress::handle);
        registerClientToServer(PacketGuiInteract.class, PacketGuiInteract::encode, PacketGuiInteract::decode, PacketGuiInteract::handle);
//        registerClientToServer(PacketOpenGui.class, PacketOpenGui::encode, PacketOpenGui::decode, PacketOpenGui::handle);
        registerClientToServer(PacketUpdateInventorySlot.class, PacketUpdateInventorySlot::encode, PacketUpdateInventorySlot::decode, PacketUpdateInventorySlot::handle);

        //Server to client messages
        registerServerToClient(PacketUpdateTile.class, PacketUpdateTile::encode, PacketUpdateTile::decode, PacketUpdateTile::handle);
        registerServerToClient(PacketPlayerData.class, PacketPlayerData::encode, PacketPlayerData::decode, PacketPlayerData::handle);
        registerServerToClient(PacketClearRecipeCache.class, PacketClearRecipeCache::encode, PacketClearRecipeCache::decode, PacketClearRecipeCache::handle);
        registerServerToClient(PacketResetPlayerClient.class, PacketResetPlayerClient::encode, PacketResetPlayerClient::decode, PacketResetPlayerClient::handle);
        registerServerToClient(PacketFlyingSync.class, PacketFlyingSync::encode, PacketFlyingSync::decode, PacketFlyingSync::handle);
//        registerServerToClient(PacketStepHeightSync.class, PacketStepHeightSync::encode, PacketStepHeightSync::decode, PacketStepHeightSync::handle);

        //Register container sync packet
        registerServerToClient(PacketUpdateContainer.class, PacketUpdateContainer::encode, PacketUpdateContainer::decode, PacketUpdateContainer::handle);
        //Container sync packet that batches multiple changes into one packet
        registerServerToClient(PacketUpdateContainerBatch.class, PacketUpdateContainerBatch::encode, PacketUpdateContainerBatch::decode, PacketUpdateContainerBatch::handle);
    }
}