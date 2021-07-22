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

public class GuiLoadedRematicsList extends GuiListBase<Rematic, WidgetRematicEntry, WidgetListLoadedRematics> {
    private static String searchQuery;
    private static Rematic currentRematic;

    public GuiLoadedRematicsList() {
        super(12, 30);
        this.title = "Loaded rematics";
    }

    @Override
    public void initGui() {
        if (searchQuery != null && currentRematic != null && !searchQuery.isEmpty()) {
            GuiBase.openGui(new GuiSearchResults(searchQuery, currentRematic));
            searchQuery = null;
            currentRematic = null;
        } else {
            super.initGui();

            ButtonGeneric button;
            int posY = this.height - 26;

            String label = StringUtils.translate("rematica.gui.add_repo");
            button = new ButtonGeneric(12, posY, this.getStringWidth(label) + 20,
                    20, label);
            this.addButton(button, new ButtonListener(this));

            GuiMainMenu.ButtonListenerChangeMenu.ButtonType type = GuiMainMenu.ButtonListenerChangeMenu.ButtonType.MAIN_MENU;
            label = StringUtils.translate(type.getLabelKey());
            int buttonWidth = this.getStringWidth(label) + 20;
            button = new ButtonGeneric(this.width - buttonWidth - 10, posY, buttonWidth, 20, label);
            this.addButton(button, new GuiMainMenu.ButtonListenerChangeMenu(type, this.getParent()));
        }
    }

    @Override
    protected WidgetListLoadedRematics createListWidget(int listX, int listY) {
        return new WidgetListLoadedRematics(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), this);
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
        private final GuiLoadedRematicsList gui;

        ButtonListener(final GuiLoadedRematicsList gui) {
            this.gui = gui;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int mouseButton) {
            GuiTextInput gui = new GuiTextInput(2048, StringUtils.translate("rematica.gui.new_repo"), "",
                    GuiUtils.getCurrentScreen(), new AddRepository(this.gui));
            GuiBase.openGui(gui);
        }
    }

    public static void setSearchQuery(String searchQuery) {
        GuiLoadedRematicsList.searchQuery = searchQuery;
    }

    public static void setCurrentRematic(Rematic currentRematic) {
        GuiLoadedRematicsList.currentRematic = currentRematic;
    }
}