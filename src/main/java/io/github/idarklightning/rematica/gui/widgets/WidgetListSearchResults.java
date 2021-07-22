package io.github.idarklightning.rematica.gui.widgets;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.gui.GuiSearchResults;
import io.github.idarklightning.rematica.util.HttpUtils;
import io.github.idarklightning.rematica.util.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WidgetListSearchResults extends WidgetListBase<SearchResult, WidgetSearchResultEntry> {
    private final String query;
    private final Rematic rematic;
    private final GuiSearchResults gui;

    public WidgetListSearchResults(int x, int y, int width, int height, String query, Rematic rematic, GuiSearchResults gui) {
        super(x, y, width, height, null);
        this.query = query;
        this.rematic = rematic;
        this.gui = gui;
        this.browserEntryHeight = 22;
    }

    @Override
    protected Collection<SearchResult> getAllEntries() {
        return new Gson().
                fromJson(HttpUtils.getRequest("http://localhost:8000/search/" + query),
                        new TypeToken<ArrayList<SearchResult>>(){}.getType());
    }

    @Override
    protected List<String> getEntryStringsForFilter(SearchResult entry) {
        return ImmutableList.of("a");
    }

    @Override
    protected WidgetSearchResultEntry createListEntryWidget(int x, int y, int listIndex, boolean isOdd, SearchResult result) {
        return new WidgetSearchResultEntry(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(result), result, rematic, this.gui, listIndex);
    }
}
