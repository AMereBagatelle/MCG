package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinateSet;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.ArrayList;

public class CoordinatesFolderWidget extends AlwaysSelectedEntryListWidget<CoordinatesFolderWidget.Entry> {
    private final ArrayList<CoordinateSet> coordinatesList;
    private final CoordinatesScreen gui;
    private final ArrayList<String> folders = new ArrayList<>();

    public CoordinatesFolderWidget(CoordinatesScreen gui, MinecraftClient client, ArrayList<CoordinateSet> coordinateList) {
        super(client, gui.width, gui.height, 40, gui.height / 3 - 16, 20);
        this.gui = gui;
        this.coordinatesList = coordinateList;
        for (CoordinateSet coordinateSet : coordinateList) {
            if (coordinateSet.getFolder() != null && !folders.contains(coordinateSet.getFolder())) {
                folders.add(coordinateSet.getFolder());
            }
        }
        for (String folder : folders) {
            this.addEntry(new CoordinateFolderEntry(gui, folder, client));
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 100;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 100;
    }

    @Override
    protected boolean isFocused() {
        return this.gui.getFocused() == this;
    }

    public class CoordinateFolderEntry extends CoordinatesFolderWidget.Entry {
        public String folder;
        private final CoordinatesScreen gui;
        private final MinecraftClient client;

        public CoordinateFolderEntry(CoordinatesScreen gui, String folder, MinecraftClient client) {
            this.folder = folder;
            this.gui = gui;
            this.client = client;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            CoordinatesFolderWidget.this.drawCenteredString(client.textRenderer, folder, gui.width / 2, y - height / 2, 16777215);
        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinatesFolderWidget.Entry> {
        public Entry() {
        }
    }
}
