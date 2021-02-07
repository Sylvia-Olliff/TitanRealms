package sylvantus.titanrealms.core.network;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkHooks;
import sylvantus.titanrealms.common.blocks.attributes.AttributeGui;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.util.WorldUtils;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;
import sylvantus.titanrealms.core.util.interfaces.items.IGuiItem;

/**
 * Used for informing the server that a click happened in a GUI and the gui window needs to change
 */
public class PacketGuiButtonPress {

    private final Type type;
    private ClickedItemButton itemButton;
    private ClickedTileButton tileButton;
//    private ClickedEntityButton entityButton;
    private Hand hand;
    private int entityID;
    private int extra;
    private BlockPos tilePosition;

    public PacketGuiButtonPress(ClickedTileButton buttonClicked, TileEntity tile) {
        this(buttonClicked, tile.getPos());
    }

    public PacketGuiButtonPress(ClickedTileButton buttonClicked, TileEntity tile, int extra) {
        this(buttonClicked, tile.getPos(), extra);
    }

    public PacketGuiButtonPress(ClickedTileButton buttonClicked, BlockPos tilePosition) {
        this(buttonClicked, tilePosition, 0);
    }

    public PacketGuiButtonPress(ClickedItemButton buttonClicked, Hand hand) {
        type = Type.ITEM;
        this.itemButton = buttonClicked;
        this.hand = hand;
    }

    public PacketGuiButtonPress(ClickedTileButton buttonClicked, BlockPos tilePosition, int extra) {
        type = Type.TILE;
        this.tileButton = buttonClicked;
        this.tilePosition = tilePosition;
        this.extra = extra;
    }

//    public PacketGuiButtonPress(ClickedEntityButton buttonClicked, int entityID) {
//        type = Type.ENTITY;
//        this.entityButton = buttonClicked;
//        this.entityID = entityID;
//    }

    public static void handle(PacketGuiButtonPress message, Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            if (player == null) {
                return;
            }
            if (message.type == Type.ENTITY) {
//                Entity entity = player.world.getEntityByID(message.entityID);
//                if (entity != null) {
//                    INamedContainerProvider provider = message.entityButton.getProvider(entity);
//                    if (provider != null) {
//                        //Ensure valid data
//                        NetworkHooks.openGui(player, provider, buf -> buf.writeVarInt(message.entityID));
//                    }
//                }
            } else if (message.type == Type.TILE) {
                TileEntityTitanRealms tile = WorldUtils.getTileEntity(TileEntityTitanRealms.class, player.world, message.tilePosition);
                if (tile != null) {
                    INamedContainerProvider provider = message.tileButton.getProvider(tile, message.extra);
                    if (provider != null) {
                        //Ensure valid data
                        NetworkHooks.openGui(player, provider, buf -> {
                            buf.writeBlockPos(message.tilePosition);
                            buf.writeVarInt(message.extra);
                        });
                    }
                }
            } else if (message.type == Type.ITEM) {
                ItemStack stack = player.getHeldItem(message.hand);
                if (stack.getItem() instanceof IGuiItem) {
                    INamedContainerProvider provider = message.itemButton.getProvider(stack, message.hand);
                    if (provider != null) {
                        NetworkHooks.openGui(player, provider, buf -> {
                            buf.writeEnumValue(message.hand);
                            buf.writeItemStack(stack);
                        });
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    public static void encode(PacketGuiButtonPress pkt, PacketBuffer buf) {
        buf.writeEnumValue(pkt.type);
        if (pkt.type == Type.ENTITY) {
//            buf.writeEnumValue(pkt.entityButton);
            buf.writeVarInt(pkt.entityID);
        } else if (pkt.type == Type.TILE) {
            buf.writeEnumValue(pkt.tileButton);
            buf.writeBlockPos(pkt.tilePosition);
            buf.writeVarInt(pkt.extra);
        } else if (pkt.type == Type.ITEM) {
            buf.writeEnumValue(pkt.itemButton);
            buf.writeEnumValue(pkt.hand);
        }
    }

    public static PacketGuiButtonPress decode(PacketBuffer buf) {
        Type type = buf.readEnumValue(Type.class);
        switch (type) {
//            case ENTITY:
//                return new PacketGuiButtonPress(buf.readEnumValue(ClickedEntityButton.class), buf.readVarInt());
            case TILE:
                return new PacketGuiButtonPress(buf.readEnumValue(ClickedTileButton.class), buf.readBlockPos(), buf.readVarInt());
            case ITEM:
                return new PacketGuiButtonPress(buf.readEnumValue(ClickedItemButton.class), buf.readEnumValue(Hand.class));
            default:
                return null;
        }
    }

    public enum ClickedItemButton {
        BACK_BUTTON((stack, hand) -> {
            if (stack.getItem() instanceof IGuiItem) {
                return ((IGuiItem) stack.getItem()).getContainerProvider(stack, hand);
            }
            return null;
        });

        private final BiFunction<ItemStack, Hand, INamedContainerProvider> providerFromItem;

        ClickedItemButton(BiFunction<ItemStack, Hand, INamedContainerProvider> providerFromItem) {
            this.providerFromItem = providerFromItem;
        }

        public INamedContainerProvider getProvider(ItemStack stack, Hand hand) {
            return providerFromItem.apply(stack, hand);
        }
    }

    public enum ClickedTileButton {
        BACK_BUTTON((tile, extra) -> {
            //Special handling to basically reset to the tiles default gui container
            Block block = tile.getBlockType();
            if (IAttribute.has(block, AttributeGui.class)) {
                return IAttribute.get(block, AttributeGui.class).getProvider(tile);
            }
            return null;
        });
//        UPGRADE_MANAGEMENT((tile, extra) -> new ContainerProvider(MekanismLang.UPGRADES, (i, inv, player) -> new UpgradeManagementContainer(i, inv, tile))),

        private final BiFunction<TileEntityTitanRealms, Integer, INamedContainerProvider> providerFromTile;

        ClickedTileButton(BiFunction<TileEntityTitanRealms, Integer, INamedContainerProvider> providerFromTile) {
            this.providerFromTile = providerFromTile;
        }

        public INamedContainerProvider getProvider(TileEntityTitanRealms tile, int extra) {
            return providerFromTile.apply(tile, extra);
        }
    }

//    public enum ClickedEntityButton {
//        //Entities
//        ROBIT_CRAFTING(entity -> {
//            if (entity instanceof EntityRobit) {
//                return new ContainerProvider(MekanismLang.ROBIT_CRAFTING, (i, inv, player) -> new CraftingRobitContainer(i, inv, (EntityRobit) entity));
//            }
//            return null;
//        }),
//        ROBIT_INVENTORY(entity -> {
//            if (entity instanceof EntityRobit) {
//                return new ContainerProvider(MekanismLang.ROBIT_INVENTORY, (i, inv, player) -> new InventoryRobitContainer(i, inv, (EntityRobit) entity));
//            }
//            return null;
//        }),
//        ROBIT_MAIN(entity -> {
//            if (entity instanceof EntityRobit) {
//                return new ContainerProvider(MekanismLang.ROBIT, (i, inv, player) -> new MainRobitContainer(i, inv, (EntityRobit) entity));
//            }
//            return null;
//        }),
//        ROBIT_REPAIR(entity -> {
//            if (entity instanceof EntityRobit) {
//                return new ContainerProvider(MekanismLang.ROBIT_REPAIR, (i, inv, player) -> new RepairRobitContainer(i, inv, (EntityRobit) entity));
//            }
//            return null;
//        }),
//        ROBIT_SMELTING(entity -> {
//            if (entity instanceof EntityRobit) {
//                return new ContainerProvider(MekanismLang.ROBIT_SMELTING, (i, inv, player) -> new SmeltingRobitContainer(i, inv, (EntityRobit) entity));
//            }
//            return null;
//        });
//
//        private final Function<Entity, INamedContainerProvider> providerFromEntity;
//
//        ClickedEntityButton(Function<Entity, INamedContainerProvider> providerFromEntity) {
//            this.providerFromEntity = providerFromEntity;
//        }
//
//        public INamedContainerProvider getProvider(Entity entity) {
//            return providerFromEntity.apply(entity);
//        }
//    }

    public enum Type {
        TILE,
        ITEM,
        ENTITY;
    }
}