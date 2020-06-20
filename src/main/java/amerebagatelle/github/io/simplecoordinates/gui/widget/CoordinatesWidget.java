package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesList;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesSet;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import amerebagatelle.github.io.simplecoordinates.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

public class CoordinatesWidget extends AlwaysSelectedEntryListWidget<CoordinatesWidget.Entry> {
    public CoordinatesList currentCoordinatesList;
    public static String coordinatesListName = "";

    public CoordinatesWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, CoordinatesScreen parent) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.setCurrentList(new CoordinatesList().createNull(), "");
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        RenderUtils.drawWidgetBackground(this.left, this.top, width, height-top-(height-bottom));
        this.renderList(this.getRowLeft(), this.top + 4 - (int)this.getScrollAmount(), mouseX, mouseY, delta);
        this.renderDecorations(mouseX, mouseY);
        this.drawCenteredString(this.minecraft.textRenderer, "Coordinates", this.left+this.width/2, this.top-30, 16777215);
        this.drawString(this.minecraft.textRenderer, coordinatesListName, this.left, this.top-10, 3553279);
    }

    public void setCurrentList(CoordinatesList list, String name) {
        this.clearEntries();
        currentCoordinatesList = list;
        coordinatesListName = name;
        if(!list.isNull) {
            for (CoordinatesSet coordinatesSet : list.coordinatesSets) {
                this.addEntry(new CoordinateListEntry(coordinatesSet));
            }
        }
    }

    public void setSelected(CoordinateListEntry entry) {
        super.setSelected(entry);
        this.ensureVisible(entry);
    }

    @Override
    public CoordinateListEntry getSelected() {
        return (CoordinateListEntry) super.getSelected();
    }

    @Override
    protected int getScrollbarPosition() {
        return this.left+this.width-10;
    }

    @Override
    public int getRowWidth() {
        return this.width-30;
    }

    public class CoordinateListEntry extends CoordinatesWidget.Entry {
        private final CoordinatesSet coordinates;

        public CoordinateListEntry(CoordinatesSet coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            if(y >= CoordinatesWidget.this.top && y + height <= CoordinatesWidget.this.bottom) {
                TextRenderer renderer = CoordinatesWidget.this.minecraft.textRenderer;
                CoordinatesWidget.this.drawString(renderer, coordinates.name, x + 5, y, 16777215);
                CoordinatesWidget.this.drawString(renderer, Integer.toString(coordinates.x), x + width / 2, y, 16777215);
                CoordinatesWidget.this.drawString(renderer, Integer.toString(coordinates.y), x + width / 6 * 4, y, 16777215);
                CoordinatesWidget.this.drawString(renderer, Integer.toString(coordinates.z), x + width / 6 * 5, y, 16777215);
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            CoordinatesWidget.this.setSelected(this);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }

        public CoordinatesSet getCoordinates() {
            return coordinates;
        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinatesWidget.Entry>{
        public Entry() {
        }
    }
}
