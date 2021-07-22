package io.github.idarklightning.rematica.gui.widgets;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextInput;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.Rematica;
import io.github.idarklightning.rematica.gui.GuiLoadedRematicsList;
import io.github.idarklightning.rematica.util.SearchLitematics;
import net.minecraft.client.util.math.MatrixStack;

public class WidgetRematicEntry extends WidgetListEntryBase<Rematic> {
    private final Rematic placement;
    private final boolean isOdd;

    public WidgetRematicEntry(final int x, int y, final int width, final int height, final Rematic entry, GuiLoadedRematicsList gui,
                                                final int listIndex) {
        super(x, y, width, height, entry, listIndex);
        placement = entry;
        isOdd = (listIndex % 2 == 1);
        y += 1;

        int posX = x + width;
        ButtonListener listener;
        String label;

        label = StringUtils.translate("rematica.gui.remove_repo");
        int buttonWidth1 = this.getStringWidth(label) + 10;
        listener = new ButtonListener(ButtonListener.Action.REMOVE, gui, this.placement);
        this.addButton(new ButtonGeneric(posX - (buttonWidth1 + 2), y, buttonWidth1, 20, label), listener);

        label = StringUtils.translate("rematica.gui.search_rematics");
        int buttonWidth2 = this.getStringWidth(label) + 10;
        listener = new ButtonListener(ButtonListener.Action.SEARCH, gui, this.placement);
        this.addButton(new ButtonGeneric(posX - (buttonWidth2 + 2) - buttonWidth1, y, buttonWidth2, 20, label), listener);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final boolean selected, final MatrixStack matrixStack) {
        // Source: WidgetSchematicEntry
        RenderUtils.color(1f, 1f, 1f, 1f);

        // Draw a lighter background for the hovered and the selected entry
        if (selected || isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawRect(x, y, width, height, 0x70FFFFFF);
        } else if (isOdd) {
            RenderUtils.drawRect(x, y, width, height, 0x20FFFFFF);
        }
        // Draw a slightly lighter background for even entries
        else {
            RenderUtils.drawRect(x, y, width, height, 0x50FFFFFF);
        }

        final String schematicName = placement.getName();
        drawString(x + 20, y + 7, 0xFFFFFFFF, schematicName, matrixStack);
        drawSubWidgets(mouseX, mouseY, matrixStack);
    }

    private static class ButtonListener implements IButtonActionListener {
        Action action;
        Rematic rematic;
        GuiLoadedRematicsList gui;

        ButtonListener(final Action action, final GuiLoadedRematicsList gui, final Rematic rematic) {
            this.action = action;
            this.rematic = rematic;
            this.gui = gui;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int mouseButton) {
            if (action == null) return;
            action.dispatch(this.gui, rematic);
        }

        public enum Action {
            SEARCH() {
                @Override
                void dispatch(GuiLoadedRematicsList gui,final Rematic rematic) {
                    GuiTextInput textInput = new GuiTextInput(2048, StringUtils.translate("rematica.gui.search_repo"), "",
                            GuiUtils.getCurrentScreen(), new SearchLitematics(rematic));
                    GuiBase.openGui(textInput);
                }
            },
            REMOVE() {
                @Override
                void dispatch(GuiLoadedRematicsList gui, Rematic rematic) {
                    Rematica.REMATICS.remove(rematic);
                    Rematic.saveToFile("rematics.json");
                    GuiBase.openGui(gui);
                }
            };

            abstract void dispatch(GuiLoadedRematicsList gui, Rematic rematic);
        }
    }
}