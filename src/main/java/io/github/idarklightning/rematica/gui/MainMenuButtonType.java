package io.github.idarklightning.rematica.gui;

import fi.dy.masa.malilib.util.StringUtils;

public enum MainMenuButtonType {
    VIEW_REMATICS("rematica.gui.button.view_rematics");

    private final String label;

    MainMenuButtonType(String label) {
        this.label = label;
    }

    public String getTranslatedKey() {
        return StringUtils.translate(this.label);
    }
}
