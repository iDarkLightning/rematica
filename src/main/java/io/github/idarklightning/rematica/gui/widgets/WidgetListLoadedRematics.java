package io.github.idarklightning.rematica.gui.widgets;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.Rematica;
import io.github.idarklightning.rematica.gui.GuiLoadedRematicsList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WidgetListLoadedRematics extends WidgetListBase<Rematic, WidgetRematicEntry> {
    GuiLoadedRematicsList gui;

    public WidgetListLoadedRematics(int x, int y, int width, int height, GuiLoadedRematicsList gui) {
        super(x, y, width, height, null);

        this.gui = gui;
        this.browserEntryHeight = 22;
    }

    @Override
    protected Collection<Rematic> getAllEntries() {
        return Rematica.REMATICS;
    }

    @Override
    protected List<String> getEntryStringsForFilter(Rematic entry) {
        return ImmutableList.of("a");
    }

    @Override
    protected WidgetRematicEntry createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Rematic entry) {
        return new WidgetRematicEntry(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), entry, gui, listIndex);
    }
}
