package sylvantus.titanrealms.core.network;

import java.util.function.Supplier;
import mekanism.api.functions.TriConsumer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.util.WorldUtils;

/**
 * Used for informing the server that an action happened in a GUI
 */
public class PacketGuiInteract {

    private final Type interactionType;

    private GuiInteraction interaction;
//    private GuiInteractionItem itemInteraction;
//    private GuiInteractionEntity entityInteraction;
    private BlockPos tilePosition;
    private ItemStack extraItem;
    private int entityID;
    private int extra;

//    public PacketGuiInteract(GuiInteractionEntity interaction, int entityID) {
//        this.interactionType = Type.ENTITY;
//        this.entityInteraction = interaction;
//        this.entityID = entityID;
//    }

    public PacketGuiInteract(GuiInteraction interaction, TileEntity tile) {
        this(interaction, tile.getPos());
    }

    public PacketGuiInteract(GuiInteraction interaction, TileEntity tile, int extra) {
        this(interaction, tile.getPos(), extra);
    }

    public PacketGuiInteract(GuiInteraction interaction, BlockPos tilePosition) {
        this(interaction, tilePosition, 0);
    }

    public PacketGuiInteract(GuiInteraction interaction, BlockPos tilePosition, int extra) {
        this.interactionType = Type.INT;
        this.interaction = interaction;
        this.tilePosition = tilePosition;
        this.extra = extra;
    }

//    public PacketGuiInteract(GuiInteractionItem interaction, TileEntity tile, ItemStack stack) {
//        this(interaction, tile.getPos(), stack);
//    }
//
//    public PacketGuiInteract(GuiInteractionItem interaction, BlockPos tilePosition, ItemStack stack) {
//        this.interactionType = Type.ITEM;
//        this.itemInteraction = interaction;
//        this.tilePosition = tilePosition;
//        this.extraItem = stack;
//    }

    public static void handle(PacketGuiInteract message, Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> {
            PlayerEntity player = ctx.getSender();
            if (player != null) {
//                if (message.interactionType == Type.ENTITY) {
//                    Entity entity = player.world.getEntityByID(message.entityID);
//                    if (entity != null) {
//                        message.entityInteraction.consume(entity, player);
//                    }
//                } else {
                    TileEntityTitanRealms tile = WorldUtils.getTileEntity(TileEntityTitanRealms.class, player.world, message.tilePosition);
                    if (tile != null) {
                        if (message.interactionType == Type.INT) {
                            message.interaction.consume(tile, player, message.extra);
                        }
//                        else if (message.interactionType == Type.ITEM) {
//                            message.itemInteraction.consume(tile, player, message.extraItem);
//                        }
                    }
//                }
            }
        });
        ctx.setPacketHandled(true);
    }

    public static void encode(PacketGuiInteract pkt, PacketBuffer buf) {
        buf.writeEnumValue(pkt.interactionType);
        if (pkt.interactionType == Type.ENTITY) {
//            buf.writeEnumValue(pkt.entityInteraction);
            buf.writeVarInt(pkt.entityID);
        } else if (pkt.interactionType == Type.INT) {
            buf.writeEnumValue(pkt.interaction);
            buf.writeBlockPos(pkt.tilePosition);
            buf.writeVarInt(pkt.extra);
        } else if (pkt.interactionType == Type.ITEM) {
//            buf.writeEnumValue(pkt.itemInteraction);
            buf.writeBlockPos(pkt.tilePosition);
            buf.writeItemStack(pkt.extraItem);
        }
    }

    public static PacketGuiInteract decode(PacketBuffer buf) {
        Type type = buf.readEnumValue(Type.class);
//        if (type == Type.ENTITY) {
//            return new PacketGuiInteract(buf.readEnumValue(GuiInteractionEntity.class), buf.readVarInt());
        if (type == Type.INT) {
            return new PacketGuiInteract(buf.readEnumValue(GuiInteraction.class), buf.readBlockPos(), buf.readVarInt());
//        } else if (type == Type.ITEM) {
//            return new PacketGuiInteract(buf.readEnumValue(GuiInteractionItem.class), buf.readBlockPos(), buf.readItemStack());
//        }
        }
        TitanRealms.LOGGER.error("Received malformed GUI interaction packet.");
        return null;
    }

//    public enum GuiInteractionItem {
//        QIO_REDSTONE_ADAPTER_STACK((tile, player, stack) -> {
//            if (tile instanceof TileEntityQIORedstoneAdapter) {
//                ((TileEntityQIORedstoneAdapter) tile).handleStackChange(stack);
//            }
//        });
//
//        private final TriConsumer<TileEntityMekanism, PlayerEntity, ItemStack> consumerForTile;
//
//        GuiInteractionItem(TriConsumer<TileEntityMekanism, PlayerEntity, ItemStack> consumerForTile) {
//            this.consumerForTile = consumerForTile;
//        }
//
//        public void consume(TileEntityMekanism tile, PlayerEntity player, ItemStack stack) {
//            consumerForTile.accept(tile, player, stack);
//        }


    public enum GuiInteraction {//TODO: Cleanup this enum/the elements in it as it is rather disorganized order wise currently
        CONTAINER_STOP_TRACKING((tile, player, extra) -> {
            if (player.openContainer instanceof TitanRealmsContainer) {
                ((TitanRealmsContainer) player.openContainer).stopTracking(extra);
            }
        });

        private final TriConsumer<TileEntityTitanRealms, PlayerEntity, Integer> consumerForTile;

        GuiInteraction(TriConsumer<TileEntityTitanRealms, PlayerEntity, Integer> consumerForTile) {
            this.consumerForTile = consumerForTile;
        }

        public void consume(TileEntityTitanRealms tile, PlayerEntity player, int extra) {
            consumerForTile.accept(tile, player, extra);
        }
    }

//    public enum GuiInteractionEntity {
//        NEXT_SECURITY_MODE((entity, player) -> {
//            if (entity instanceof ISecurityObject) {
//                ISecurityObject security = (ISecurityObject) entity;
//                if (security.hasSecurity()) {
//                    UUID owner = security.getOwnerUUID();
//                    if (owner != null && player.getUniqueID().equals(owner)) {
//                        security.setSecurityMode(security.getSecurityMode().getNext());
//                    }
//                }
//            }
//        }),
//        ;
//
//        private final BiConsumer<Entity, PlayerEntity> consumerForEntity;
//
//        GuiInteractionEntity(BiConsumer<Entity, PlayerEntity> consumerForTile) {
//            this.consumerForEntity = consumerForTile;
//        }
//
//        public void consume(Entity entity, PlayerEntity player) {
//            consumerForEntity.accept(entity, player);
//        }
//    }

    private enum Type {
        ENTITY,
        ITEM,
        INT;
    }
}