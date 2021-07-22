package io.github.idarklightning.rematica.util;

import com.google.gson.Gson;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.interfaces.IStringConsumer;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.Rematica;
import io.github.idarklightning.rematica.gui.GuiLoadedRematicsList;

public class AddRepository implements IStringConsumer {
    private final GuiLoadedRematicsList gui;

    public AddRepository(final GuiLoadedRematicsList gui) {
        this.gui = gui;
    }

    @Override
    public void setString(String s) {
        String res = HttpUtils.getRequest(s);
        Rematic rematic = new Gson().fromJson(res != null ? res : "", Rematic.class);

        if (rematic == null || rematic.getName() == null || rematic.getSearchURL() == null) {
            this.gui.addMessage(Message.MessageType.ERROR, "rematica.gui.invalid_repo");
            return;
        }

        Rematica.REMATICS.add(rematic);
    }
}
