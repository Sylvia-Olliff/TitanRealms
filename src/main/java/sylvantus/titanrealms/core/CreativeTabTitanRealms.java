package sylvantus.titanrealms.core;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import sylvantus.titanrealms.TitanRealms;

public class CreativeTabTitanRealms extends ItemGroup {

    public CreativeTabTitanRealms() {
        super(TitanRealms.MODID);
    }


    @Override
    public ItemStack createIcon() {
        return null; //TODO: Set this to an iconic item once it exists
    }

    @Override
    public ITextComponent getGroupName() {
        return TitanRealmsLang.TITANREALMS.translate();
    }
}
