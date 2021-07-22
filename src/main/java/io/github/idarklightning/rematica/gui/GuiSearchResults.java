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
import io.github.idarklightning.rematica.gui.widgets.WidgetListSearchResults;
import io.github.idarklightning.rematica.gui.widgets.WidgetSearchResultEntry;
import io.github.idarklightning.rematica.util.AddRepository;
import io.github.idarklightning.rematica.util.SearchResult;

public class GuiSearchResults extends GuiListBase<SearchResult, WidgetSearchResultEntry, WidgetListSearchResults> {
    private final String query;
    private final Rematic rematic;

    public GuiSearchResults(String query, Rematic rematic) {
        super(12, 30);
        this.query = query;
        this.rematic = rematic;
        this.title = "Search Results for Query: " + this.query;
    }

    @Override
    public void initGui() {
        super.initGui();

        ButtonGeneric button;
        int posY = this.height - 26;
        String label;

        label = StringUtils.translate("rematica.gui.loaded_rematics");
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
    protected WidgetListSearchResults createListWidget(int listX, int listY) {
        return new WidgetListSearchResults(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), this.query, this.rematic, this);
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
            GuiLoadedRematicsList gui = new GuiLoadedRematicsList();
            GuiBase.openGui(gui);
        }
    }

}