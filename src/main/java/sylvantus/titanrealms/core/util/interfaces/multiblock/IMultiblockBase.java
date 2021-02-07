package sylvantus.titanrealms.core.util.interfaces.multiblock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import sylvantus.titanrealms.core.lib.multiblock.MultiblockData;
import sylvantus.titanrealms.core.lib.multiblock.MultiblockManager;
import sylvantus.titanrealms.core.lib.multiblock.Structure;
import sylvantus.titanrealms.core.util.interfaces.tiles.ITileWrapper;

public interface IMultiblockBase extends ITileWrapper {

    default MultiblockData getMultiblockData(MultiblockManager<?> manager) {
        MultiblockData data = getStructure(manager).getMultiblockData();
        if (data != null && data.isFormed()) {
            return data;
        }
        return getDefaultData();
    }

    default void setMultiblockData(MultiblockManager<?> manager, MultiblockData multiblockData) {
        getStructure(manager).setMultiblockData(multiblockData);
    }

    MultiblockData getDefaultData();

    ActionResultType onActivate(PlayerEntity player, Hand hand, ItemStack stack);

    Structure getStructure(MultiblockManager<?> manager);

    boolean hasStructure(Structure structure);

    void setStructure(MultiblockManager<?> manager, Structure structure);

    default Structure resetStructure(MultiblockManager<?> manager) {
        Structure structure = new Structure(this);
        setStructure(manager, structure);
        return structure;
    }
}
