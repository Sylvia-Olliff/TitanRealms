package sylvantus.titanrealms.client.GUI.element.tab;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import sylvantus.titanrealms.client.render.lib.ColorAtlas.ColorRegistryObject;

public interface TabType<TILE extends TileEntity> {

    ResourceLocation getResource();

    void onClick(TILE tile);

    ITextComponent getDescription();

    default int getYPos() {
        return 6;
    }

    ColorRegistryObject getTabColor();
}