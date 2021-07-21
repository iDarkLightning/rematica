package io.github.idarklightning.rematica.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import net.minecraft.client.gui.screen.Screen;

public class ButtonListener implements IButtonActionListener {
    private final MainMenuButtonType type;
    private final Screen parent;

    public ButtonListener(MainMenuButtonType type, Screen parent) {
        this.type = type;
        this.parent = parent;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int arg1) {
        GuiBase gui = null;
        if (this.type == MainMenuButtonType.VIEW_REMATICS) {
            gui = new GuiLoadedRematicsList();
        }

        if (gui != null) {
            gui.setParent(this.parent);
            GuiBase.openGui(gui);
        }
    }
}
