package sylvantus.titanrealms.client;

import sylvantus.titanrealms.client.render.lib.ColorAtlas;
import sylvantus.titanrealms.client.render.lib.ColorAtlas.ColorRegistryObject;

public class SpecialColors {

    private SpecialColors() {
    }

    public static final ColorAtlas GUI_OBJECTS = new ColorAtlas("gui_objects");
    public static final ColorAtlas GUI_TEXT = new ColorAtlas("gui_text");

    public static final ColorRegistryObject TAB_ITEM_CONFIG = GUI_OBJECTS.register();
    public static final ColorRegistryObject TAB_CONTAINER_EDIT_MODE = GUI_OBJECTS.register();
    public static final ColorRegistryObject TAB_MULTIBLOCK_MAIN = GUI_OBJECTS.register();
    public static final ColorRegistryObject TAB_MULTIBLOCK_STATS = GUI_OBJECTS.register();

    public static final ColorRegistryObject TEXT_TITLE = GUI_TEXT.register();
    public static final ColorRegistryObject TEXT_HEADING = GUI_TEXT.register();
    public static final ColorRegistryObject TEXT_SUBHEADING = GUI_TEXT.register();
    public static final ColorRegistryObject TEXT_SCREEN = GUI_TEXT.register();
}
