package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinateSet;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.ArrayList;

public class CoordinatesWidget extends AlwaysSelectedEntryListWidget<CoordinatesWidget.Entry> {
    private final ArrayList<CoordinateSet> coordinatesList;
    private final CoordinatesScreen gui;

    public CoordinatesWidget(CoordinatesScreen gui, MinecraftClient client, ArrayList<CoordinateSet> coordinateList) {
        super(client, gui.width, gui.height, gui.height / 3, gui.height - 64, 20);
        this.gui = gui;
        this.coordinatesList = coordinateList;
        coordinatesList.forEach(coordinate -> this.addEntry(new CoordinateListEntry(gui, coordinate)));
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
        return super.getRowWidth() + 100;
    }

    @Override
    protected boolean isFocused() {
        return this.gui.getFocused() == this;
    }

    public class CoordinateListEntry extends CoordinatesWidget.Entry {
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
            TextRenderer textRenderer = CoordinatesWidget.this.minecraft.textRenderer;
            int color = 16777215;
            int drawY = y+CoordinatesWidget.this.itemHeight/2-textRenderer.fontHeight+3;
            int rowWidth = CoordinatesWidget.this.getRowWidth();
            textRenderer.draw(name, x, drawY, color);
            CoordinatesWidget.this.drawCenteredString(textRenderer, Integer.toString(this.x), x+rowWidth/5*2, drawY, color);
            CoordinatesWidget.this.drawCenteredString(textRenderer, Integer.toString(this.y), x+rowWidth/5*3, drawY, color);
            CoordinatesWidget.this.drawCenteredString(textRenderer, Integer.toString(this.z), x+rowWidth/5*4, drawY, color);
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

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinatesWidget.Entry>{
        public Entry() {
        }
    }
}
