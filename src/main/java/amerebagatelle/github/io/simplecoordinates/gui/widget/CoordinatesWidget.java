package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesList;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesSet;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.Tessellator;

import java.util.ArrayList;

public class CoordinatesWidget extends AlwaysSelectedEntryListWidget<CoordinatesWidget.Entry> {
    public CoordinatesList currentCoordinatesList;
    public String coordinatesListName = "";

    public CoordinatesWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.setCurrentList(new CoordinatesList().createNull(), "");
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderList(this.getRowLeft(), this.top + 4 - (int)this.getScrollAmount(), mouseX, mouseY, delta);
        this.renderDecorations(mouseX, mouseY);
        this.drawCenteredString(this.minecraft.textRenderer, "Coordinates", this.left+this.width/2, this.top-30, 16777215);
        this.drawCenteredString(this.minecraft.textRenderer, coordinatesListName, this.left+this.width/2, this.top-15, 3553279);
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

    public void setSelected(CoordinatesWidget.Entry entry) {
        super.setSelected(entry);
        this.ensureVisible(entry);
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 100;
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    public class CoordinateListEntry extends CoordinatesWidget.Entry {
        private final CoordinatesSet coordinates;

        public CoordinateListEntry(CoordinatesSet coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer renderer = CoordinatesWidget.this.minecraft.textRenderer;
            CoordinatesWidget.this.drawString(renderer, coordinates.name, x+5, y, 16777215);
            CoordinatesWidget.this.drawString(renderer, Integer.toString(coordinates.x), x+60, y, 16777215);
            CoordinatesWidget.this.drawString(renderer, Integer.toString(coordinates.y), x+90, y, 16777215);
            CoordinatesWidget.this.drawString(renderer, Integer.toString(coordinates.z), x+120, y, 16777215);
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
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinatesWidget.Entry>{
        public Entry() {
        }
    }
}
