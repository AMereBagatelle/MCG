package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinateSet;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.ArrayList;

public class CoordinatesListWidget extends AlwaysSelectedEntryListWidget<CoordinatesListWidget.Entry> {
    private final ArrayList<CoordinateSet> coordinatesList;
    private ArrayList<CoordinateSet> currentCoordinateList;
    private final CoordinatesScreen gui;
    private String currentFolder;

    public CoordinatesListWidget(CoordinatesScreen gui, MinecraftClient client, ArrayList<CoordinateSet> initialCoordinateList) {
        super(client, gui.width / 3 * 2 - 20, gui.height, 40, gui.height / 9 * 8, 20);
        this.gui = gui;
        this.coordinatesList = initialCoordinateList;
        currentFolder = "default";
        findEntriesForCurrentFolder();
        currentCoordinateList.forEach(coordinate -> this.addEntry(new CoordinateListEntry(gui, coordinate)));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
    }

    public void setCoordinatesFolder(String folder) {
        this.currentFolder = folder;
        findEntriesForCurrentFolder();
        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();
        currentCoordinateList.forEach(coordinate -> this.addEntry(new CoordinateListEntry(gui, coordinate)));
    }

    public void findEntriesForCurrentFolder() {
        currentCoordinateList = new ArrayList<>();
        coordinatesList.forEach(coordinateSet -> {
            if (coordinateSet.getFolder().equals(currentFolder)) {
                currentCoordinateList.add(coordinateSet);
            }
        });
    }

    public void setSelected(CoordinatesListWidget.Entry entry) {
        super.setSelected(entry);
        this.ensureVisible(entry);
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

    public class CoordinateListEntry extends CoordinatesListWidget.Entry {
        public String name;
        public int x, y, z;
        private final CoordinatesScreen gui;
        private final CoordinateSet coordinateList;

        public CoordinateListEntry(CoordinatesScreen gui, CoordinateSet coordinateSet) {
            this.gui = gui;
            this.name = coordinateSet.getName();
            this.x = coordinateSet.getX();
            this.y = coordinateSet.getY();
            this.z = coordinateSet.getZ();
            this.coordinateList = coordinateSet;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer textRenderer = CoordinatesListWidget.this.minecraft.textRenderer;
            int color = 16777215;
            int drawY = y + CoordinatesListWidget.this.itemHeight / 2 - textRenderer.fontHeight + 3;
            int rowWidth = CoordinatesListWidget.this.getRowWidth();
            textRenderer.draw(name, x, drawY, color);
            CoordinatesListWidget.this.drawCenteredString(textRenderer, Integer.toString(this.x), x + rowWidth / 5 * 2, drawY, color);
            CoordinatesListWidget.this.drawCenteredString(textRenderer, Integer.toString(this.y), x + rowWidth / 5 * 3, drawY, color);
            CoordinatesListWidget.this.drawCenteredString(textRenderer, Integer.toString(this.z), x + rowWidth / 5 * 4, drawY, color);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.gui.select(this, this.coordinateList);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinatesListWidget.Entry> {
        public Entry() {
        }
    }
}
