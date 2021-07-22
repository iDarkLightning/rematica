package io.github.idarklightning.rematica.util;

import fi.dy.masa.malilib.interfaces.IStringConsumer;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.gui.GuiLoadedRematicsList;

public class SearchLitematics implements IStringConsumer {
    private final Rematic rematic;

    public SearchLitematics(Rematic rematic) {
        this.rematic = rematic;
    }

    @Override
    public void setString(String query) {
        GuiLoadedRematicsList.setSearchQuery(query);
        GuiLoadedRematicsList.setCurrentRematic(this.rematic);
    }
}
