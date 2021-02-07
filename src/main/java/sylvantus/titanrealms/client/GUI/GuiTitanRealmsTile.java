package sylvantus.titanrealms.client.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nonnull;
import mekanism.api.inventory.IInventorySlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import sylvantus.titanrealms.common.containers.slot.InventoryContainerSlot;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsTileContainer;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.config.tile.components.DataType;

import java.util.Set;

public abstract class GuiTitanRealmsTile<TILE extends TileEntityTitanRealms, CONTAINER extends TitanRealmsTileContainer<TILE>> extends GuiTitanRealms<CONTAINER> {

    protected final TILE tile;

    protected GuiTitanRealmsTile(CONTAINER container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        tile = container.getTileEntity();
    }

    public TILE getTileEntity() {
        return tile;
    }

    @Override
    protected void drawForegroundText(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
        super.drawForegroundText(matrix, mouseX, mouseY);

    }

    public void renderTitleText(MatrixStack matrix) {
        drawTitleText(matrix, tile.getName(), titleY);
    }

//    private DataType getFromSlot(Slot slot) {
//        if (slot.slotNumber < tile.getSlots() && slot instanceof InventoryContainerSlot) {
//            ISideConfiguration config = (ISideConfiguration) tile;
//            ConfigInfo info = config.getConfig().getConfig(TransmissionType.ITEM);
//            if (info != null) {
//                Set<DataType> supportedDataTypes = info.getSupportedDataTypes();
//                IInventorySlot inventorySlot = ((InventoryContainerSlot) slot).getInventorySlot();
//                for (DataType type : supportedDataTypes) {
//                    ISlotInfo slotInfo = info.getSlotInfo(type);
//                    if (slotInfo instanceof InventorySlotInfo && ((InventorySlotInfo) slotInfo).hasSlot(inventorySlot)) {
//                        return type;
//                    }
//                }
//            }
//        }
//        return null;
//    }
}