package sylvantus.titanrealms.core;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;

public class CreativeTabTitanRealms extends ItemGroup {

    public CreativeTabTitanRealms() {
        super(TitanRealms.MODID);
    }


    @Override
    @NotNull
    public ItemStack createIcon() {
        return TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE).getItemStack();
    }

    @Override
    @NotNull
    public ITextComponent getGroupName() {
        return TitanRealmsLang.TITANREALMS.translate();
    }
}
