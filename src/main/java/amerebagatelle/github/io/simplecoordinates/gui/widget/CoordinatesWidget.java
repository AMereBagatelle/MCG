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
    CoordinatesList currentCoordinatesList;

    public CoordinatesWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.setCurrentList(new CoordinatesList().createNull());
    }

    public void setCurrentList(CoordinatesList list) {
        this.clearEntries();
        currentCoordinatesList = list;
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
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
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
