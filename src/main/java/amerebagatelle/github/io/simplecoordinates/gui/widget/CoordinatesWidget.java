package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.ArrayList;

public class CoordinatesWidget extends AlwaysSelectedEntryListWidget<CoordinatesWidget.Entry> {
    private final ArrayList<ArrayList<String>> coordinatesList;
    private final CoordinatesScreen gui;

    public CoordinatesWidget(CoordinatesScreen gui, MinecraftClient client, ArrayList<ArrayList<String>> coordinateList) {
        super(client, gui.width, gui.height/5*4, 40, gui.height, 20);
        this.gui = gui;
        this.coordinatesList = coordinateList;
        this.setRenderSelection(true);
        coordinatesList.forEach(coordinate -> this.addEntry(new CoordinateListEntry(gui, coordinate)));
    }

    public void setSelected(CoordinatesWidget.Entry entry) {
        super.setSelected(entry);
        this.ensureVisible(entry);
    }

    public class CoordinateListEntry extends CoordinatesWidget.Entry {
        public String name;
        public String x, y, z;
        private final CoordinatesScreen gui;
        private final ArrayList<String> coordinateList;

        public CoordinateListEntry(CoordinatesScreen gui, ArrayList<String> coordinateList) {
            this.gui = gui;
            this.name = coordinateList.get(0);
            this.x = coordinateList.get(1);
            this.y = coordinateList.get(2);
            this.z = coordinateList.get(3);
            this.coordinateList = coordinateList;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer textRenderer = CoordinatesWidget.this.minecraft.textRenderer;
            int color = 16777215;
            int drawY = y+CoordinatesWidget.this.itemHeight/2-textRenderer.fontHeight+3;
            int rowWidth = CoordinatesWidget.this.getRowWidth();
            textRenderer.draw(name, x, drawY, color);
            CoordinatesWidget.this.drawCenteredString(textRenderer, this.x, x+rowWidth/5*2, drawY, color);
            CoordinatesWidget.this.drawCenteredString(textRenderer, this.y, x+rowWidth/5*3, drawY, color);
            CoordinatesWidget.this.drawCenteredString(textRenderer, this.z, x+rowWidth/5*4, drawY, color);
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
