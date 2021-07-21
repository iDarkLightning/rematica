package io.github.idarklightning.rematica.gui;

import fi.dy.masa.litematica.gui.GuiMainMenu;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.GuiTextInput;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.gui.widgets.WidgetListLoadedRematics;
import io.github.idarklightning.rematica.gui.widgets.WidgetRematicEntry;
import io.github.idarklightning.rematica.util.AddRepository;
import net.minecraft.client.gui.screen.Screen;

public class GuiLoadedRematicsList extends GuiListBase<Rematic, WidgetRematicEntry, WidgetListLoadedRematics> {

    public GuiLoadedRematicsList() {
        super(12, 30);
        this.title = "Loaded rematics";
    }

    @Override
    public void initGui() {
        super.initGui();

        ButtonGeneric button;
        int posY = this.height - 26;

        String label = StringUtils.translate("rematica.gui.add_repo");
        button = new ButtonGeneric(12, posY, this.getStringWidth(label) + 20,
                20, label);
        this.addButton(button, new ButtonListener());

        GuiMainMenu.ButtonListenerChangeMenu.ButtonType type = GuiMainMenu.ButtonListenerChangeMenu.ButtonType.MAIN_MENU;
        label = StringUtils.translate(type.getLabelKey());
        int buttonWidth = this.getStringWidth(label) + 20;
        button = new ButtonGeneric(this.width - buttonWidth - 10, posY, buttonWidth, 20, label);
        this.addButton(button, new GuiMainMenu.ButtonListenerChangeMenu(type, this.getParent()));
    }

    @Override
    protected WidgetListLoadedRematics createListWidget(int listX, int listY) {
        return new WidgetListLoadedRematics(listX, listY, this.getBrowserWidth(), this.getBrowserHeight());
    }

    @Override
    protected int getBrowserHeight() {
        return this.height-68;
    }

    @Override
    protected int getBrowserWidth() {
        return this.width-20;
    }

    private static class ButtonListener implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int mouseButton) {
            GuiTextInput gui = new GuiTextInput(2048, StringUtils.translate("rematica.gui.new_repo"), "",
                    GuiUtils.getCurrentScreen(), new AddRepository());
            GuiBase.openGui(gui);
        }
    }

}