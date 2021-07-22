package io.github.idarklightning.rematica.gui.widgets;

import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.data.SchematicHolder;
import fi.dy.masa.litematica.gui.GuiMaterialList;
import fi.dy.masa.litematica.materials.MaterialListSchematic;
import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacementManager;
import fi.dy.masa.litematica.util.FileType;
import fi.dy.masa.litematica.util.WorldUtils;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.idarklightning.rematica.Rematic;
import io.github.idarklightning.rematica.gui.GuiSearchResults;
import io.github.idarklightning.rematica.util.SearchResult;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WidgetSearchResultEntry extends WidgetListEntryBase<SearchResult> {

    private final SearchResult result;
    private final boolean isOdd;
    private final GuiSearchResults gui;

    public WidgetSearchResultEntry(final int x, int y, final int width, final int height, final SearchResult entry, final Rematic rematic, GuiSearchResults gui,
                                   final int listIndex) {
        super(x, y, width, height, entry, listIndex);
        this.result = entry;
        this.gui = gui;
        this.isOdd = (listIndex % 2 == 1);
        y += 1;

        int posX = x + width;
        ButtonListener listener;
        String label;

        label = StringUtils.translate("rematica.gui.load_litematic");
        int buttonWidth1 = this.getStringWidth(label) + 10;
        listener = new ButtonListener(ButtonListener.Action.LOAD, this.gui, rematic, this.result);
        this.addButton(new ButtonGeneric(posX - (buttonWidth1 + 2), y, buttonWidth1, 20, label), listener);

        label = StringUtils.translate("rematica.gui.materials_list");
        int buttonWidth2 = this.getStringWidth(label) + 10;
        listener = new ButtonListener(ButtonListener.Action.MATERIALS, this.gui, rematic, this.result);
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

        final String schematicName = result.getName();
        drawString(x + 20, y + 7, 0xFFFFFFFF, schematicName, matrixStack);
        drawSubWidgets(mouseX, mouseY, matrixStack);
    }

    private static class ButtonListener implements IButtonActionListener {
        ButtonListener.Action action;
        SearchResult result;
        Rematic rematic;
        GuiSearchResults gui;

        ButtonListener(final ButtonListener.Action action, final GuiSearchResults gui, final Rematic rematic, final SearchResult result) {
            this.action = action;
            this.result = result;
            this.rematic = rematic;
            this.gui = gui;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int mouseButton) {
            if (action == null) return;
            File tempFile;

            try {
                URL url = new URL(result.getSourceURL());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                tempFile = File.createTempFile("rematic", result.getName());
                tempFile.deleteOnExit();
                IOUtils.copy(conn.getInputStream(), new FileOutputStream(tempFile));
            } catch (IOException ignored) {
                return;
            }

            LitematicaSchematic schematic;
            FileType fileType = FileType.fromFile(tempFile);
            boolean warnType = false;

            if (fileType == FileType.LITEMATICA_SCHEMATIC) {
                schematic = LitematicaSchematic.createFromFile(tempFile.getParentFile(), tempFile.getName());
            } else if (fileType == FileType.SCHEMATICA_SCHEMATIC) {
                schematic = WorldUtils.convertSchematicaSchematicToLitematicaSchematic(tempFile.getParentFile(),
                        tempFile.getName(), false, gui);
                warnType = true;
            } else if (fileType == FileType.VANILLA_STRUCTURE) {
                schematic = WorldUtils.convertStructureToLitematicaSchematic(tempFile.getParentFile(),
                        tempFile.getName(), false, gui);
                warnType = true;
            } else {
                gui.addMessage(Message.MessageType.ERROR, "litematica.error.schematic_load.unsupported_type",
                        result.getName());
                return;
            }

            if (schematic != null) {
                action.dispatch(schematic, this.gui, result);

                if (warnType) InfoUtils.showGuiOrInGameMessage(Message.MessageType.WARNING, 15000,
                        "litematica.message.warn.schematic_load_non_litematica");
            }
        }

        public enum Action {
            LOAD() {
                @Override
                void dispatch(final LitematicaSchematic schematic, GuiSearchResults gui, final SearchResult result) {
                    SchematicHolder.getInstance().addSchematic(schematic, true);
                    gui.addMessage(Message.MessageType.SUCCESS, "litematica.info.schematic_load.schematic_loaded", result.getName());
                    if (!DataManager.getCreatePlacementOnLoad()) return;

                    BlockPos pos = new BlockPos(gui.mc.player.getPos());
                    String name = schematic.getMetadata().getName();
                    boolean enabled = !GuiBase.isShiftDown();
                    SchematicPlacementManager manager = DataManager.getSchematicPlacementManager();
                    SchematicPlacement placement = SchematicPlacement.createFor(schematic, pos, name, enabled, enabled);
                    manager.addSchematicPlacement(placement, true);
                    manager.setSelectedSchematicPlacement(placement);
                }
            },
            MATERIALS() {
                @Override
                void dispatch(final LitematicaSchematic schematic, GuiSearchResults gui, SearchResult result) {
//                    if (GuiBase.isShiftDown()) {
//                        GuiSchematicLoad.MaterialListCreator creator = new GuiSchematicLoad.MaterialListCreator(schematic);
//                        GuiStringListSelection listSelection = new GuiStringListSelection(schematic.getAreas().keySet(), creator);
//                        listSelection.setTitle(StringUtils.translate("litematica.gui.title.material_list.select_schematic_regions", schematic.getMetadata().getName()));
//                        listSelection.setParent(GuiUtils.getCurrentScreen());
//                        GuiBase.openGui(listSelection);
//                    } else {
                    MaterialListSchematic materialList = new MaterialListSchematic(schematic, true);
                    DataManager.setMaterialList(materialList);
                    GuiBase.openGui(new GuiMaterialList(materialList));
//                    }
                }
            };


            abstract void dispatch(LitematicaSchematic schematic, GuiSearchResults gui, SearchResult result);
        }
    }
}
