package io.github.idarklightning.rematica.gui.widgets;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import io.github.idarklightning.rematica.Rematic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WidgetListLoadedRematics extends WidgetListBase<Rematic, WidgetRematicEntry> {
    public WidgetListLoadedRematics(int x, int y, int width, int height) {
        super(x, y, width, height, null);

        this.browserEntryHeight = 22;
    }

    @Override
    protected Collection<Rematic> getAllEntries() {
        Collection<Rematic> rematics = new ArrayList<>();

        rematics.add(new Rematic("bonka doodle"));
        return rematics;
    }

    @Override
    protected List<String> getEntryStringsForFilter(Rematic entry) {
        return ImmutableList.of("a");
    }

    @Override
    protected WidgetRematicEntry createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Rematic entry) {
        return new WidgetRematicEntry(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), entry, listIndex);
    }
}
