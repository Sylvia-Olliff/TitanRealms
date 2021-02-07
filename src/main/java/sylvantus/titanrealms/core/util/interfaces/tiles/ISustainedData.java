package sylvantus.titanrealms.core.util.interfaces.tiles;

import net.minecraft.item.ItemStack;

import java.util.Map;

public interface ISustainedData {

    void writeSustainedData(ItemStack itemStack);

    void readSustainedData(ItemStack itemStack);

    //Key is tile save string, value is sustained data string
    Map<String, String> getTileDataRemap();
}
